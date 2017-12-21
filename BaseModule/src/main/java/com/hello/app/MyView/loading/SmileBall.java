package com.hello.app.MyView.loading;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.hello.app.Base.BaseObject;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2016/1/11
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class SmileBall extends BaseObject implements IBallCall<SmileBall.SmileState> {

    //圆球属性

    public static final int DURATION = 1200;

    /** 变化坐标 x */
    public float x_d;

    /** 变化坐标 y */
    public float y_d;

    /** 最大半径 */
    public float r_;

    /** 水平速度 */
    public float speed_H;

    /** 垂直速度 */
    public float speed_V;

    /** 圆心速度 */
    public float speed_C;

    /** 动画时间 */
    public float time;

    public Circle mCircle;

    public Eyes mEyes;

    float mSw_hand; //drag阶段的角度

    private Paint mPaint; //实体画笔

    private float scale;

    private RectF rectF;

    private float top;

    private float bottom;

    private float padding = 20;

    private SmileState enumState = SmileState.ENUM_HAND;

    @Override
    public void initData() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE); //设置画笔填充
        mPaint.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint.setSubpixelText(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.argb(255, 252, 232, 86));
        mPaint.setStrokeWidth(30);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //实例化圆圈和眼睛
        mCircle = new Circle();
        mEyes = new Eyes();

    }

    @Override
    public void init(float x, float y, int color, float radius) {
        x_ = x;
        y_ = y;
        x_d = x_;
        y_d = y_;
        this.radius = radius;

    }

    public void setLayout(int left, int top, int right, int bottom) {
        this.top = padding;
        this.bottom = bottom - top - padding;
        r_ = (this.bottom - this.top) / 2;
        x_ = (right - left) / 2;
        y_ = (bottom - top) / 2;
        x_d = x_;
        y_d = y_;
        mCircle.setXY(x_, y_);
        mEyes.setXY(x_, y_);
    }

    public void setTime(float time) {
        this.time = time;
        //计算速率
        switch (enumState) {
            case ENUM_HAND:
                break;
            case LOADING:
                break;

            case EXTRA:
                this.time = 10000;
                break;

        }

    }

    public void draw(Canvas canvas) {
        onDrawSelf(canvas, null);
    }

    @Override
    public void onDrawSelf(Canvas canvas, Paint paint) {
        switch (enumState) {
            case ENUM_HAND:
                mSw_hand = 360 * scale;
                canvas.save();
                rectF = new RectF(x_ - r_, y_ - r_, x_ + r_, y_ + r_);
                canvas.drawArc(rectF, 180, mSw_hand, false, mPaint);
                canvas.restore();

                break;
            case LOADING:
                canvas.save();
                rectF = new RectF(x_ - r_, y_ - r_, x_ + r_, y_ + r_);
                canvas.drawArc(rectF, 180 + time, mSw_hand, false, mPaint);
                canvas.restore();
                break;
            case EXTRA:
                mCircle.onDrawSelf(canvas, mPaint);
                mEyes.onDrawSelf(canvas, mPaint);
                break;
            case EXIT:
                break;

        }
    }

    @Override
    public void reset() {
        enumState = SmileState.EXIT;
        x_ = 0;
        y_ = 0;
        radius = 0;
        r_ = 0;
        mSw_hand = 0;
    }

    @Override
    public void notifyBallState(SmileState state) {
        enumState = state;

    }

    public void notifyScale(float scale) {
        this.scale = scale;
    }

    public void setSpeed(float speed_H, float speed_V) {
        this.speed_V = speed_V;
        this.speed_H = speed_H;
    }

    public void setR_(float r_) {
        this.r_ = r_;
    }

    public void setEnumState(SmileState enumState) {
        this.enumState = enumState;
    }

    public enum SmileState {
        ENUM_HAND, LOADING, EXTRA, EXIT;
    }

    /** 外围圆圈 */
    private class Circle extends BaseObject {

        public float sweep = 200;

        public float start;

        public Circle() {

        }

        public Circle(float x_, float y_) {
            super(x_, y_);
        }


        public void setXY(float x, float y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {
            canvas.save();
            rectF = new RectF(this.x_ - r_, this.y_ - r_, this.x_ + r_, this.y_ + r_);
            canvas.drawArc(rectF, start, sweep, false, mPaint);
            canvas.restore();
        }

        @Override
        public void reset() {

        }
    }

    /** 一双眼睛 */
    private class Eyes extends BaseObject {

        public Eyes() {

        }

        public Eyes(float x_, float y_) {
            super(x_, y_);
        }

        public void setXY(float x, float y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

        }

        @Override
        public void reset() {

        }
    }
}
