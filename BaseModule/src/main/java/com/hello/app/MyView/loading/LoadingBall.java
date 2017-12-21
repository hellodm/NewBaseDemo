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
public class LoadingBall extends BaseObject implements IBallCall<LoadingBall.BallState> {

    //圆球属性

    /** 变化坐标 x */
    public float x_d;

    /** 变化坐标 y */
    public float y_d;

    /** 最大半径 */
    public float r_max;

    /** 最小半径 */
    public float r_min;

    /** 过度半径 */
    public float r_tran;

    /** 当前半径 */
    public float r_curr;

    /** 水平速度 */
    public float speed_H;

    /** 垂直速度 */
    public float speed_V;

    /** 圆心速度 */
    public float speed_C;

    /** 动画时间 */
    public float time;

    private Paint mPaint; //实体画笔

    private Paint mPaint_S;//边框画笔

    private float scale;

    private RectF rectF;

    private float top;

    private float bottom;

    private float padding = 20;


    //
    private BallState enumState = BallState.ENUM_HAND;

    public static final int DURATION = 1200;

    @Override
    public void notifyBallState(BallState state) {
        enumState = state;

    }


    public enum BallState {
        ENUM_HAND, ONE, TWO, THREE, FOUR, EXTRA, EXIT;
    }

    @Override
    public void initData() {
        mPaint = new Paint();
        mPaint_S = new Paint();
        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        mPaint.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint.setSubpixelText(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.argb(255, 252, 232, 86));
        mPaint_S.setStyle(Paint.Style.STROKE); //设置画笔填充
        mPaint_S.setStrokeWidth(2);
        mPaint_S.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint_S.setSubpixelText(true);
        mPaint_S.setDither(true);
        mPaint_S.setColor(Color.argb(255, 96, 56, 19));
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
        r_max = (this.bottom - this.top) / 2;
        r_min = padding;
        x_ = (right - left) / 2;
        y_ = (bottom - top) / 2;
        x_d = x_;
        y_d = y_;
    }

    public void setTime(float time) {
        this.time = time;
        //计算速率
        switch (enumState) {
            case ONE:
                speed_V = (bottom - top - 2 * r_min) * 5 / (DURATION * 2);
                speed_H = (r_tran - r_min) * 5 / (DURATION * 2);
                break;
            case TWO:
                this.time = time - DURATION * 2 / 5;
                speed_V = (radius - r_min) * 5 / DURATION;
                speed_C = (bottom - r_min - y_) * 5 / DURATION;
                break;
            case THREE:
                this.time = time - DURATION * 3 / 5;
                speed_V = (radius - r_tran) * 5 / DURATION;
                speed_H = (r_max - padding - radius) * 5 / DURATION;
                break;
            case FOUR:
                this.time = time - DURATION * 4 / 5;
                speed_V = (radius - r_tran) * 5 / DURATION;
                speed_H = (r_max - padding - radius) * 5 / DURATION;
                break;
            case EXTRA:
                this.time = time - DURATION * 4 / 5;
                speed_V = (radius - r_tran) * 5 / DURATION;
                speed_H = (r_max - padding - radius) * 5 / DURATION;
                break;

        }

    }


    public void draw(Canvas canvas) {
        onDrawSelf(canvas, null);
    }

    float d_v;

    float d_h;

    @Override
    public void onDrawSelf(Canvas canvas, Paint paint) {
        switch (enumState) {
            case ENUM_HAND:
                r_curr = r_max * scale;
                y_d = r_curr + padding;
                if (scale < 0.4) {
                    canvas.drawCircle(x_, y_d, r_curr, mPaint);
                    canvas.drawCircle(x_, y_d, r_curr, mPaint_S);
                    rectF = new RectF(x_ - r_curr, y_d - r_curr, x_ + r_curr, y_d + r_curr);
                    r_tran = r_curr;//设置过度半径
                } else {
                    rectF.bottom = 2 * r_curr + 20;
                    canvas.drawOval(rectF, mPaint);
                    canvas.drawOval(rectF, mPaint_S);
                }

                break;
            case ONE:
                d_v = top + time * speed_V;
                d_h = r_tran - time * speed_H;
                rectF = new RectF(x_ - d_h, d_v, x_ + d_h, bottom);
                canvas.drawOval(rectF, mPaint);
                canvas.drawOval(rectF, mPaint_S);
                break;
            case TWO:
                r_curr = r_min + speed_V * time;
                y_d = bottom - r_min - speed_C * time;
                canvas.drawCircle(x_, y_d, r_curr, mPaint);
                canvas.drawCircle(x_, y_d, r_curr, mPaint_S);

                break;
            case THREE:
                d_v = radius - time * speed_V;
                d_h = radius + time * speed_H;
                rectF = new RectF(x_ - d_h, y_ - d_v, x_ + d_h, y_ + d_v);
                canvas.drawOval(rectF, mPaint);
                canvas.drawOval(rectF, mPaint_S);
                break;
            case FOUR:
                d_v = r_tran + time * speed_V;
                d_h = r_max - padding - time * speed_H;
                rectF = new RectF(x_ - d_h, y_ - d_v, x_ + d_h, y_ + d_v);
                canvas.drawOval(rectF, mPaint);
                canvas.drawOval(rectF, mPaint_S);
                break;
            case EXTRA:
                d_v = r_tran + time * speed_V;
                d_h = r_max - padding - time * speed_H;
                rectF = new RectF(x_ - d_h, y_ - d_v, x_ + d_h, y_ + d_v);
                canvas.drawOval(rectF, mPaint);
                canvas.drawOval(rectF, mPaint_S);
                break;
            case EXIT:
                break;

        }
    }

    @Override
    public void reset() {
        enumState = BallState.EXIT;
        x_ = 0;
        y_ = 0;
        radius = 0;
        r_max = 0;
        r_min = 0;
        r_curr = 0;
        r_tran = 0;
    }


    public void notifyScale(float scale) {
        this.scale = scale;
    }


    public void setSpeed(float speed_H, float speed_V) {
        this.speed_V = speed_V;
        this.speed_H = speed_H;
    }


    public void setR_max(float r_max) {
        this.r_max = r_max;
    }

    public void setR_min(float r_min) {
        this.r_min = r_min;
    }

    public void setR_tran(float r_tran) {
        this.r_tran = r_tran;
    }

    public void setEnumState(BallState enumState) {
        this.enumState = enumState;
    }
}
