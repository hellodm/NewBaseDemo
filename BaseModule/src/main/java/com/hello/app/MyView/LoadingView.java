package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hello.app.R;

/**
 * 该类是自定义的loadingView-双圈转动登陆动画
 * Created by dong on 2014/7/10.
 */
public class LoadingView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private Paint mPaint;

    private RectF rect_in;

    private RectF rect_out;


    Context context;

    private SurfaceHolder holder;

    private boolean isRefresh = true;

    private Canvas canvas;

    protected Thread thread;


    /**
     * 下面是自定义view的属性
     */

    public int color_in = android.R.color.holo_blue_dark;

    public int color_out = android.R.color.holo_red_dark;

    /**
     * 速度
     */

    public float speed_in;

    public float speed_out;

    /**
     * 圆圈的图片
     */
    public int image_in;

    public int image_out;

    /**
     * 是否填充图片
     */
    public boolean isImage = false;

    /**
     * 圆圈半径
     */
    public float radius_in = 10;

    public float radius_out = 15;

    /**
     * 转圈角度
     */
    private float degreen_in = 0;

    private float degreen_out = 0;


    /**
     * 圆圈的坐标
     */

    public float x = 0;

    public float y = 0;


    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        holder = getHolder();
        holder.addCallback(this);
        setZOrderOnTop(true); //设置surface在其他windows的上面
        holder.setFormat(PixelFormat.TRANSLUCENT);

        mPaint = new Paint();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);

        color_in = array
                .getColor(R.styleable.LoadingView_color_in, android.R.color.holo_blue_dark);//提供默认值
        color_out = array
                .getColor(R.styleable.LoadingView_color_out, android.R.color.holo_red_dark);//提供默认值
        speed_in = array.getFloat(R.styleable.LoadingView_speed_in, 5);
        speed_out = array.getFloat(R.styleable.LoadingView_speed_out, 5);
        image_in = array.getInt(R.styleable.LoadingView_image_in, 0);
        image_out = array.getInt(R.styleable.LoadingView_image_out, 0);
        isImage = array.getBoolean(R.styleable.LoadingView_image, false);
        radius_in = array.getFloat(R.styleable.LoadingView_radius_in, 10);
        radius_out = array.getFloat(R.styleable.LoadingView_radius_out, 15);

        array.recycle();//释放资源

        thread = new Thread(this);

    }


    /**
     * 数据的初始化
     */
    public void init() {

        //获取控件尺寸并且定义中心坐标

        x = this.getWidth() / 2;
        y = this.getHeight() / 2;

        // 消除锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE); //Paint.Style.FILL --画笔填充
        mPaint.setStrokeWidth(3); //设置画笔的粗细


    }


    private void drawSelf() {

        try {

            rect_in = new RectF(x - radius_in, y - radius_in, x + radius_in, y + radius_in);
            rect_out = new RectF(x - radius_out, y - radius_out, x + radius_out, y + radius_out);

            canvas = holder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if (isImage) {

                Bitmap b_in = BitmapFactory.decodeResource(context.getResources(), image_in);
                Bitmap b_out = BitmapFactory.decodeResource(context.getResources(), image_out);

                canvas.save();
                canvas.rotate(degreen_in, x, y);
                canvas.drawBitmap(b_in, 0, 0, mPaint);
                canvas.restore();

                canvas.save();
                canvas.rotate(degreen_out, x, y);
                canvas.drawBitmap(b_out, 0, 0, mPaint);
                canvas.restore();


            } else {
                mPaint.setColor(color_in);
                canvas.save();
                canvas.rotate(degreen_in, x, y);
                canvas.drawArc(rect_in, 0, 270, false, mPaint);
                canvas.restore();

                mPaint.setColor(color_out);
                canvas.save();
                canvas.rotate(-degreen_out, x, y);
                canvas.drawArc(rect_out, 0, -270, false, mPaint);
                canvas.restore();
            }


        } catch (Exception ignored) {

        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }

        }


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRefresh = true;
        init();
        thread.setDaemon(true);
        if (!thread.isAlive()) {
            thread.start();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRefresh = false;
    }

    @Override
    public void run() {
        while (isRefresh) {
            degreen_in += speed_in;
            degreen_out += speed_out;
            drawSelf();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;

        }

        return true;


    }
}





