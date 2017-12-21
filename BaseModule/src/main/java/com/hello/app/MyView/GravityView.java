package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hello.app.R;


/**
 * 该类是自定义的-重力小球动画
 * Created by dong on 2014/7/10.
 */
public class GravityView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private Paint mPaint;

    private Ball ball;


    private Context context;

    private SurfaceHolder holder;

    private boolean isRefresh = true;

    private Canvas canvas;

    protected Thread thread;


    /**
     * 屏幕的尺寸
     */
    public float w;

    public float h;


    public GravityView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        mPaint = new Paint();

        holder = getHolder();
        holder.addCallback(this);
        setZOrderOnTop(true); //设置surface在其他windows的上面
        holder.setFormat(PixelFormat.TRANSLUCENT);
        ball = new Ball(this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GravityView);

//        ball.color = array  .getColor(R.styleable.GravityView_color, android.R.color.holo_blue_dark);//提供默认值
        ball.g = array.getFloat(R.styleable.GravityView_radius, 20);
        ball.h = array.getFloat(R.styleable.GravityView_radius, 20);
        ball.radius = array.getFloat(R.styleable.GravityView_radius, 20);

        array.recycle();//释放资源

        thread = new Thread(this);


    }


    /**
     * 数据的初始化
     */
    private void init() {

        //获取控件尺寸并且定义中心坐标

        ball.ball_x = this.getWidth() / 2;
        ball.ball_y = this.getHeight() / 2;
        w = this.getWidth();
        h = this.getHeight();
        ball.W = w;
        ball.H = h;
        ball.ground_y = h * 4 / 5;

        // 消除锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE); //Paint.Style.FILL --画笔填充
        mPaint.setStrokeWidth(3); //设置画笔的粗细


    }

    /**
     * 画一条地线
     */


    private void drawGround() {

        mPaint.setColor(Color.BLACK);

        canvas.save();
        canvas.drawLine(0, h * 4 / 5, 2 * w, h * 4 / 5, mPaint);
        canvas.restore();


    }


    private void drawSelf() {

        while (isRefresh) {

            ball.t = ball.t + 0.1f;
            canvas = holder.lockCanvas();
            long start = System.currentTimeMillis();

            if (canvas == null) {
                return;
            }

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            try {
                drawGround();
                mPaint.setColor(ball.color);
                ball.drawSelf(canvas, mPaint);

                long end = System.currentTimeMillis();

                if (end - start < 10) {  //实现每秒60帧-相当于60ms
                    Thread.sleep(10 - (end - start));
                }


            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }


        }


    }


    private void release() {
        isRefresh = false;
        holder.removeCallback(this);
        holder.getSurface().release();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRefresh = true;
        init();
        thread.setDaemon(true);
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        release();
    }

    @Override
    public void run() {

        drawSelf();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:

                if (ball.isTouchBall(event.getX(), event.getY()) || ball.isPress) {
                    ball.ball_x = event.getX();
                    ball.ball_y = event.getY();
                    ball.isPress = true;
                    ball.isRaise = false;
                    ball.isFall = false;
                    ball.h = 0;
                    ball.t = 0;
                    ball.h_up = 0;
                }

                break;

            case MotionEvent.ACTION_UP:

                if (ball.isPress) {
                    ball.isPress = false;
                }

                break;

        }

        return true;


    }


}





