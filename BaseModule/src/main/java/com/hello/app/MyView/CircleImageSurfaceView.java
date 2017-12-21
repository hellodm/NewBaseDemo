package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseThreadSurfaceView;
import com.hello.app.R;
import com.hello.app.Util.Util;

/**
 * 该类是自定义的View
 * Created by dong on 2014/7/10.
 */
public class CircleImageSurfaceView extends BaseThreadSurfaceView {


    /**
     * 下面是自定义view的属性
     */

    public int color_in = android.R.color.holo_blue_dark;

    public int color_out = android.R.color.holo_red_dark;

    public float time = 0;  //时间t

    /** 周期--单位s */
    public float period_in = 60;

    public float period_out = 60;

    /**
     * 圆圈的图片
     */

    public Bitmap bitmap_in;

    public Bitmap bitmap_out;

    /**
     * 是否填充图片
     */
    public boolean isImage = false;

    /**
     * 圆圈半径
     */
    public float radius_in = 10;

    public float radius_out = 15;

    private boolean isRefresh = true;

    private Circle cirIn = null;

    private Circle cirOut = null;


    public CircleImageSurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initAttrs(attrs);
    }

    @Override
    public void startHandler(boolean isHandler) {

    }

    @Override
    public void initAttrs(AttributeSet attr) {

        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.CircleImageSurfaceView);

        color_in = array.getColor(R.styleable.CircleImageSurfaceView_color_i,
                android.R.color.holo_blue_dark);//提供默认值
        color_out = array.getColor(R.styleable.CircleImageSurfaceView_color_o,
                android.R.color.holo_red_dark);//提供默认值
        period_in = array.getFloat(R.styleable.CircleImageSurfaceView_period_i, 60);
        period_out = array.getFloat(R.styleable.CircleImageSurfaceView_period_o, 60);
        isImage = array.getBoolean(R.styleable.CircleImageSurfaceView_image_circle, false);
        radius_in = array.getFloat(R.styleable.CircleImageSurfaceView_radius_i, 180);
        radius_out = array.getFloat(R.styleable.CircleImageSurfaceView_radius_o, 250);

        array.recycle();//释放资源
    }


    @Override
    public void init() {
        isRefresh = true;

        //获取布局的尺寸
        Util.logStr("控件尺寸", x_L + "=== " + y_L);

        //初始化2个圈
        cirIn = new Circle(x_L, y_L - radius_in);
        cirOut = new Circle(x_L - radius_out, y_L);
        cirIn.setRadius((int) radius_in);
        cirIn.setPeriod(period_in);
        cirIn.setBitmap(bitmap_in);
        cirOut.setRadius((int) radius_out);
        cirOut.setPeriod(period_out);
        cirOut.setBitmap(bitmap_out);

        // 消除锯齿
        mPaint.setColor(color_in);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL); //Paint.Style.STROKE--画笔描边
        mPaint.setStrokeWidth(2);

    }

    @Override
    public void drawSelf() {

        drawTrack(canvas, mPaint);//绘制轨道

        cirIn.onDrawSelf(canvas, mPaint);
        cirOut.onDrawSelf(canvas, mPaint);


    }


    /** 绘制轨道 */
    private void drawTrack(Canvas canvas, Paint paint) {
        Log.i("开始画轨迹", "开始画轨迹");
        //绘制内圈
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.save();
        canvas.drawCircle(x_L, y_L, radius_in, paint);
        canvas.restore();
        //绘制外圈
        canvas.save();
        canvas.drawCircle(x_L, y_L, radius_out, paint);
        canvas.restore();

        Log.i("开始画轨迹", "轨迹绘画结束");

    }

    @Override
    public void run() {

        while (isRefresh) {

            time += 0.01;

            onDraw();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouch(cirIn, x, y)) {
                    Toast.makeText(this.getContext(), "里面的圆圈", Toast.LENGTH_SHORT).show();
                }
                if (isTouch(cirOut, x, y)) {
                    Toast.makeText(this.getContext(), "外面的圆圈", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onTouchEvent(event);
    }


    /**
     * 判断是否触碰某个勋章
     *
     * @param circle 需要判断的medal
     * @param x      手指点击的x坐标
     * @param y      手指点击的y坐标
     * @return 布尔值
     */
    private boolean isTouch(Circle circle, float x, float y) {
        boolean isX = false;
        boolean isY = false;
        if (x >= circle.x_ - circle.radius_self && x <= circle.x_ + circle.radius_self) {
            isX = true;
        }
        if (y >= circle.y_ - circle.radius_self && y <= circle.y_ + circle.radius_self) {
            isY = true;
        }
        if (isX && isY) {
            return true;
        } else {
            return false;
        }

    }


    public Bitmap getBitmap_in() {
        return bitmap_in;
    }

    public void setBitmap_in(Bitmap bitmap_in) {
        this.bitmap_in = bitmap_in;
    }

    public Bitmap getBitmap_out() {
        return bitmap_out;
    }

    public void setBitmap_out(Bitmap bitmap_out) {
        this.bitmap_out = bitmap_out;
    }

    /** 自定义的圆圈 */

    private class Circle extends BaseObject {


        private int radius;  //圆周半径

        private int radius_self = 50; //自身半径

        private float angular;//角速度

        private float period;//周期


        protected Circle(float x_, float y_) {
            super(x_, y_);
        }


        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            //老的方法--转动画布
//            angular = (float) (2 * Math.PI / period);
//            canvas.save();
//            canvas.rotate(angular * time*30, x, y);
//            canvas.drawBitmap(bitmap, x_ - radius_self, y_ - radius_self, paint);
//            canvas.restore();

            //新的方法自己转动--坐标圆周运动

            Log.i("开始画小球", "time====" + time);

            angular = (float) (2 * Math.PI / period);
            speed = angular * radius;

            x_ = (float) (x_L + radius * Math.cos(Math.PI / 2 - angular * time));
            y_ = (float) (y_L - radius * Math.sin(Math.PI / 2 - angular * time));

            canvas.save();
            paint.setColor(Color.YELLOW);
//            paint.setStyle(Paint.Style.FILL);
//            canvas.drawCircle(x_, y_, radius_self, paint);  //无图片画圆

            canvas.drawBitmap(bitmap, x_ - radius_self, y_ - radius_self, paint);

            canvas.restore();


        }

        @Override
        public void reset() {

        }


        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getRadius_self() {
            return radius_self;
        }

        public void setRadius_self(int radius_self) {
            this.radius_self = radius_self;
        }

        public float getAngular() {
            return angular;
        }

        public void setAngular(float angular) {
            this.angular = angular;
        }

        public float getPeriod() {
            return period;
        }

        public void setPeriod(float period) {
            this.period = period;
        }
    }
}
