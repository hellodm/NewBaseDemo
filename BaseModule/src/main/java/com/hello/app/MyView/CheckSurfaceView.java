package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseThreadSurfaceView;
import com.hello.app.R;
import com.hello.app.Util.BitmapUtil;

import java.util.Random;

/**
 * 自定义的车况检查的View
 * Created by dong on 2014/8/18.
 */
public class CheckSurfaceView extends BaseThreadSurfaceView {

    /**常量constant***********************************/


    /** 外部圆圈的半径radius_circle */
    private float r_c;

    /** 小球的半径radius_ball */
    private float r_b;

    /** 显示车况的圆圈半径radius_state */
    private float r_s;

    /** 白色内圈的半径radius_in */
    private float r_in;

    /** 标题的字体size */
    private float t_size;

    /** 外圈进度值 */
    private float values = 0;

    /** 外圈进度的判断识别 */
    private boolean isProcess = false;


    /** 实例******************* */

    private CheckBall ball1;

    private CheckBall ball2;

    private CheckBall ball3;

    private CheckBall ball4;

    private CheckBall ball5;

    private CheckBall ball6;

    private CheckBall[] balls;


    private CheckState[] states;

    private CheckSearch search;


    public CheckSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void startHandler(boolean isHandler) {

    }

    @Override
    public void initAttrs(AttributeSet attr) {
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.CheckSurfaceView);
        t_size = array.getDimension(R.styleable.CheckSurfaceView_check_text_size, 30);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }


    /**
     * 设置进度值
     */
    private void setValues(int values) {

        this.values = values;
    }

    /**
     * 设置小圈转动
     *
     * @param number 小圈position
     */
    private void setBallRotate(int number, boolean isRotate) {

        balls[number - 1].setRotate(isRotate);

    }


    /**
     * 设置大圈转动
     */
    private void setBigCircleRotate(boolean isRotate) {

        isProcess = isRotate;
    }

    @Override
    public void init() {

        //初始化数据
        float l = x_L >= y_L ? y_L : x_L;
        r_c = x_L >= y_L ? l - 3 * t_size : l - 5 * t_size;//设置大圈的半径
        r_b = r_c / 8;  //设置小圈半径
        r_s = r_c * 4 / 5; //设置车况半径
        r_in = r_c * 3 / 7; //设置白色内圈半径
        t_size = r_c / 7;

        float r_h = (float) (r_c * Math.sqrt(3) / 2); //水平方向的距离
        float r_v = r_c / 2;                          //垂直方向的距离

        //初始化搜索图标
        search = new CheckSearch(x_L, y_L);
        search.initData(r_c / 5, 2);

        //初始化6个小球
        ball1 = new CheckBall(x_L, y_L - r_c);
        ball2 = new CheckBall(x_L + r_h, y_L - r_v);
        ball3 = new CheckBall(x_L + r_h, y_L + r_v);
        ball4 = new CheckBall(x_L, y_L + r_c);
        ball5 = new CheckBall(x_L - r_h, y_L + r_v);
        ball6 = new CheckBall(x_L - r_h, y_L - r_v);
        balls = new CheckBall[]{ball1, ball2, ball3, ball4, ball5, ball6};

        ball1.setRadius(r_b);
        ball1.setTitle("动力系统", x_L - 2 * t_size, y_L - r_c - 2 * r_b);
        ball2.setRadius(r_b);
        ball2.setTitle("车身系统", x_L + r_h + r_b, y_L - r_v - r_b);
        ball3.setRadius(r_b);
        ball3.setTitle("信号系统", x_L + r_h + r_b, y_L + r_v + 2 * r_b);
        ball4.setRadius(r_b);
        ball4.setTitle("低盘系统", x_L - 2 * t_size, y_L + r_c + 2 * r_b + t_size);
        ball5.setRadius(r_b);
        ball5.setTitle("数据状态", x_L - r_h - r_b - 4 * t_size, y_L + r_v + 2 * r_b);
        ball6.setRadius(r_b);
        ball6.setTitle("动态行驶", x_L - r_h - r_b - 4 * t_size, y_L - r_v - r_b);

        states = new CheckState[6];
        for (int i = 0; i < states.length; i++) {
            states[i] = new CheckState(x_L, y_L);
            states[i].initData(-90 + i * 60);
            states[i].setState(Constant.STATE_NULL);
        }


    }


    /**
     * 界面点击监听
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (event.getX() > x_L - r_in
                        && event.getX() < x_L + r_in
                        && event.getY() > y_L - r_in
                        && event.getY() < y_L + r_in) {

                    reset();

                    start();

                }

                break;


        }

        return true;


    }


    /** 开始check */
    private void start() {
        search.setRotate(true);
        setBallRotate(1, true);
        setBigCircleRotate(true);

    }


    /** 界面初始化 */

    private void reset() {

        for (int i = 0; i < balls.length; i++) {

            balls[i].reset();
            states[i].reset();
        }
        setValues(0);


    }


    @Override
    public void drawSelf() {
        //绘制外部的大进度圈
        drawBgCircle();

        //绘制6个小圈
        ball1.onDrawSelf(canvas, mPaint);
        ball2.onDrawSelf(canvas, mPaint);
        ball3.onDrawSelf(canvas, mPaint);
        ball4.onDrawSelf(canvas, mPaint);
        ball5.onDrawSelf(canvas, mPaint);
        ball6.onDrawSelf(canvas, mPaint);

        //绘制刻度
        for (int i = 0; i < 6; i++) {
            states[i].onDrawSelf(canvas, mPaint);
        }

        /**绘制内部白圈*/
        drawInCircle();


    }


    /** 绘制外部大圈 */
    private void drawBgCircle() {

        if (isProcess && values < 370) {
            values++;
        }
        for (int i = 1; i <= 6; i++) {
            if (values == i * Constant.PROCESS_SCALE) {

//                setBigCircleRotate(false);
                balls[i - 1].setRotate(false);
                if (i < 6) {
                    balls[i].setRotate(true);
                }

                boolean state = new Random().nextBoolean();

                states[i - 1].setState(state ? Constant.STATE_NORMAL : Constant.STATE_EXCEPTION);

                balls[i - 1].setState(state ? Constant.BALL_NORMAL : Constant.BALL_EXCEPTION);

            }

            if (values == 360) {

                search.setRotate(false);


            }

        }

        mPaint.setStyle(Paint.Style.STROKE); //设置画笔填充
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆头
        mPaint.setStrokeWidth(r_c / 11);
        mPaint.setColor(Color.GRAY);

        canvas.save();
        canvas.drawCircle(x_L, y_L, r_c, mPaint);
        canvas.restore();

        //角度渐变
        int[] colors = new int[]{Color.argb(100, 0, 255, 0), Color.GREEN};
        Shader shader_sweep = new SweepGradient(0, 0, colors, null);
        mPaint.setShader(shader_sweep);

        canvas.save();
        canvas.translate(x_L, y_L);
        canvas.rotate(-90);
        canvas.drawArc(new RectF(-r_c, -r_c, r_c, r_c), 0,
                values, false, mPaint);
        canvas.restore();
        mPaint.setShader(null);
        mPaint.setStrokeCap(Paint.Cap.BUTT);//设置画笔为圆头

    }


    /** 绘制内部大圈 */
    private void drawInCircle() {

        //        mPaint.setAlpha(100);
        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        mPaint.setColor(Color.WHITE);

        canvas.save();
        canvas.drawCircle(x_L, y_L, r_in, mPaint);
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(t_size);
        if (!search.getRotate()) {
            canvas.drawText(new char[]{'开', '始', '检', '测'}, 0, 4, x_L - r_in + t_size, y_L,
                    mPaint);
        }
        canvas.restore();

        //绘制中心搜索
        search.onDrawSelf(canvas, mPaint);


    }


    /** 常量类 */
    public static class Constant {

        /** 刻度表盘的状态 */

        public static int STATE_NULL = Color.GRAY;  //状态空的时候

        public static int STATE_NORMAL = Color.argb(255, 0, 191, 0);  //状态正常

        public static int STATE_EXCEPTION = Color.argb(255, 255, 120, 0); //状态异常


        /** 单的个刻度的度数 */

        public static int PROCESS_SCALE = 60;


        /** 小球的状态 */
        public static int BALL_NULL = Color.WHITE;  //状态空的时候

        public static int BALL_NORMAL = 0x01;  //状态正常

        public static int BALL_EXCEPTION = 0x02; //状态异常


    }

    /** 中心搜索图标 */
    public class CheckSearch extends BaseObject {


        private float radius;  //圆圈+手柄半径

        private float radius_self; //自身半径

        private float angular;//角速度

        private float period;//周期

        private float time = 0;

        private boolean isRotate = false;

        protected CheckSearch(float x_, float y_) {
            super(x_, y_);
        }

        @Override
        public void initData() {

            bitmap = BitmapUtil.getBitmapFromID(context, R.drawable.check_search_ico);

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }


        private void initData(float radius, float period) {

            this.radius = radius;
            this.period = period;
            radius_self = radius * 1 / 2;


        }

        private void setRotate(boolean isRotate) {

            this.isRotate = isRotate;
        }

        private boolean getRotate() {

            return isRotate;
        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            if (isRotate) {

                time += 0.01;
                /**绘制搜索*/

                angular = (float) (2 * Math.PI / period);
                speed = angular * radius;

                x_ = (float) (x_L + radius * Math.cos(Math.PI / 2 - angular * time));
                y_ = (float) (y_L - radius * Math.sin(Math.PI / 2 - angular * time));

                mPaint.setColor(Color.argb(255, 178, 178, 178));
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(radius / 5);
                canvas.save();
//                canvas.drawBitmap(bitmap, x_ - radius_self, y_ - radius_self, paint);//绘制图片
                canvas.drawCircle(x_, y_, radius_self, mPaint);
                float offset = (float) (radius_self / Math.sqrt(2));
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawLine(x_ + offset, y_ + offset, x_ + 2 * offset, y_ + 2 * offset, mPaint);
                canvas.drawCircle(x_ + 2 * offset, y_ + 2 * offset, radius / 10, mPaint);
                canvas.restore();
            }
        }

        @Override
        public void reset() {

        }
    }


    /** 车况刻度state */
    public class CheckState extends BaseObject {

        private int space = 3;  //刻度间隔

        private int divider = 9; //刻度宽度

        private int startAngle = 0;  //开始绘制的角度

        private int angle = 0;        //sweep的角度

        private int color;   //刻度的颜色

        private int state;


        protected CheckState(float x_, float y_) {
            super(x_, y_);
        }

        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        /**
         * 初始化数据
         */
        public void initData(int startAngle) {
            this.startAngle = startAngle;

        }

        /**
         * 设置状态正常还是异常
         */
        public void setState(int state) {

            this.state = state;
            color = state;

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
            mPaint.setColor(color);

            RectF rectF = new RectF(x_ - r_s, y_ - r_s, x_ + r_s, y_ + r_s);

            for (int i = 0; i < 5; i++) {
                angle = startAngle + i * divider + (i + 1) * space;
                canvas.save();
                canvas.drawArc(rectF, angle, 10, true, mPaint);
                canvas.restore();
            }


        }

        @Override
        public void reset() {
            this.state = Constant.STATE_NULL;
            color = state;
        }


    }


    /** 方位小球 */
    public class CheckBall extends BaseObject {

        private Bitmap bit_ok;

        private Bitmap bit_no;

        private float radius;

        private String title;

        private float x_title;

        private float y_title;

        private boolean isRotate = false;

        private int rotate = 0;


        private int state = Constant.BALL_NULL;


        protected CheckBall(float x_, float y_) {
            super(x_, y_);
        }

        @Override
        public void initData() {

            bit_ok = BitmapUtil.getBitmapFromID(getContext(), R.drawable.check_success_bg);
            bit_no = BitmapUtil.getBitmapFromID(getContext(), R.drawable.check_warn_bg);


        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            //绘制圆圈白色填充
            drawState(state);

            //绘制标题
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(0);
            mPaint.setTextSize(t_size);

            canvas.save();
            canvas.drawText(title, x_title, y_title, mPaint);
            canvas.restore();

            //绘制转圈进度条
            if (isRotate) {
                rotate += 2;
                int[] colors = new int[]{Color.argb(10, 255, 255, 255), Color.GREEN};
                //角度渐变
                Shader shader_sweep = new SweepGradient(0, 0, colors, null);
                //线性渐变
//                Shader shader = new LinearGradient(x_ - radius, y_, x_ + radius, y_, colors, null,Shader.TileMode.CLAMP);
                //圆形渐变--圆心到外圆
//                Shader shader_circle = new RadialGradient(x_,y_,radius,colors,null, Shader.TileMode.CLAMP);

                mPaint.setShader(shader_sweep);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(r_c / 40);

                canvas.save();
                canvas.translate(x_, y_);//先平移canvas坐标在进行旋转
                canvas.rotate(rotate);
                canvas.drawCircle(0, 0, radius, mPaint);
                canvas.restore();
                mPaint.setShader(null);
            }

        }

        @Override
        public void reset() {

            setRotate(false);
            setState(Constant.BALL_NULL);

        }


        /**
         * 设置小球的状态
         */
        public void setState(int state) {
            this.state = state;
        }

        /**
         * 设置标题的位置
         */
        public void setTitle(String title, float x_title, float y_title) {

            this.title = title;
            this.x_title = x_title;
            this.y_title = y_title;

        }

        /**
         * 绘制小球的不同状态
         */
        private void drawState(int state) {

            switch (state) {

                case Color.WHITE:
                    mPaint.setStyle(Paint.Style.FILL); //绘制圆圈白色填充
                    mPaint.setColor(Color.WHITE);
                    canvas.save();
                    canvas.drawCircle(x_, y_, radius, mPaint);
                    canvas.restore();
                    //绘制外框灰色边框
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(5);
                    mPaint.setColor(Color.argb(127, 86, 86, 86));

                    canvas.save();
                    canvas.drawCircle(x_, y_, radius, mPaint);
                    canvas.restore();

                    break;

                case 0x01:
                    canvas.save();
                    canvas.drawBitmap(bit_ok, x_ - radius, y_ - radius, mPaint);
                    canvas.restore();
                    break;
                case 0x02:
                    canvas.save();
                    canvas.drawBitmap(bit_no, x_ - radius, y_ - radius, mPaint);
                    canvas.restore();
                    break;
            }

        }


        public void setRadius(float radius) {
            this.radius = radius;
        }

        public boolean isRotate() {
            return isRotate;
        }

        public void setRotate(boolean isRotate) {
            this.isRotate = isRotate;
        }
    }
}
