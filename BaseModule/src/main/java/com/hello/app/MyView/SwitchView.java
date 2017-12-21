package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：自定义的切换开关
 *
 * @author dong
 * @Time: 2014/12/22
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class SwitchView extends BaseToolView {

    RectF rect_left;

    RectF rectF_right;

    RectF rectF_center;

    RectF rect_left_f;

    RectF rectF_right_f;

    RectF rect_center_off;

    RectF rectF_right_off;

    private int color_on = Color.argb(255, 76, 217, 100); //绿色

    private int color_frame = Color.argb(255, 229, 229, 229); //关闭背景色

    private int color_ball = Color.WHITE; //按钮白色

    private int color_off = Color.argb(255, 254, 254, 254); //边框灰色


    private float r_half;

    private float r_ball;

    private float offSet;

    private Ball mBall;

    private SwitchState mState = SwitchState.OFF;


    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {
        r_ball = W * 29 / 102;
        r_half = W * 31 / 102;
        offSet = W * 2 / 102;
        mBall = new Ball();
        mBall.init(2 * offSet + r_ball, y_L, color_ball, r_ball);

        rect_left = new RectF(0, y_L - r_half, 2 * r_half, y_L + r_half);
        rectF_right = new RectF(W - 2 * r_half, y_L - r_half, W, y_L + r_half);
        rectF_center = new RectF(r_half - 0.5f, y_L - r_half, W - r_half + 0.5f, y_L + r_half);

        rect_left_f = new RectF(offSet, y_L - r_half, 2 * r_half, y_L + r_half);
        rectF_right_f = new RectF(W - 2 * r_half, y_L - r_half, W - offSet, y_L + r_half);

        rect_center_off = new RectF(r_half - 0.5f, y_L - r_half, W - r_half + 0.5f, y_L + r_half);
        rectF_right_off = new RectF(W - 2 * r_half, y_L - r_half, W - offSet + 0.5f, y_L + r_half);

    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawBg(canvas);
//        drawBgFrame(canvas);
        mBall.onDrawSelf(canvas, mPaint);

    }

    /** 绘制背景 */
    private void drawBg(Canvas canvas) {
//绘制背景
        setPaintStyle(Paint.Style.FILL, false, 1, color_on);
        canvas.save();
        canvas.drawArc(rect_left, 90, 180, true, mPaint);
        canvas.drawRect(rectF_center, mPaint);
        canvas.drawArc(rectF_right, -90, 180, true, mPaint);
        canvas.restore();

        canvas.save();
        if (mBall.x_ < (W - offSet - r_ball)) {
            setPaintStyle(Paint.Style.FILL, true, 1, color_off);
            rect_center_off.left = mBall.x_;

            canvas.drawRect(rect_center_off, mPaint);
            canvas.drawArc(rectF_right_off, -90, 180, true, mPaint);
        }

        if (mState == SwitchState.OFF) {
            //绘制边框
            setPaintStyle(Paint.Style.STROKE, false, 2 * (int) offSet, color_frame);
            canvas.drawArc(rect_left_f, 90, 180, false, mPaint);
            canvas.drawLine(r_half - 0.5f, y_L - r_half, W - r_half + 0.5f, y_L - r_half, mPaint);
            canvas.drawLine(r_half - 0.5f, y_L + r_half, W - r_half + 0.5f, y_L + r_half, mPaint);
            canvas.drawArc(rectF_right_f, -90, 180, false, mPaint);

        }
        canvas.restore();

    }

    /**
     * 绘制背景边框
     */
    private void drawBgFrame(Canvas canvas) {
        setPaintStyle(Paint.Style.FILL, false, (int) offSet, color_frame);
        canvas.save();
        canvas.drawArc(rect_left, 90, 180, true, mPaint);
        canvas.drawRect(rectF_center, mPaint);
        canvas.drawArc(rectF_right, -90, 180, true, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x_event = event.getX();
        float y_event = event.getY();

        mBall.y_ = y_L;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x_event >= x_L) {
                    mBall.x_ = W - offSet - r_ball;
                } else if (x_event < x_L) {
                    mBall.x_ = r_ball + 2 * offSet;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                Log.i("onTouchEvent", "y=" + y_event);

                if (y_event < 0 && y_event > H) {

                    if (mBall.x_ >= x_L) {
                        mBall.x_ = W - offSet - r_ball;
                    } else if (mBall.x_ < x_L) {
                        mBall.x_ = r_ball + 2 * offSet;
                    }

                } else {
                    if (x_event < r_ball + offSet) {
                        mBall.x_ = r_ball + 2 * offSet;
                    } else if (x_event >= 2 * offSet && x_event <= W - 2 * offSet - r_ball) {
                        mBall.x_ = x_event;
                    } else {
                        mBall.x_ = W - offSet - r_ball;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:

                if (mBall.x_ >= x_L) {
                    mBall.x_ = W - offSet - r_ball;
                    mState = SwitchState.ON;
                } else if (mBall.x_ < x_L) {
                    mBall.x_ = r_ball + 2 * offSet;
                    mState = SwitchState.OFF;
                }
                break;
        }
        invalidate();
        return true;
    }

    /** 设置开关为--开 */
    private void setON() {

        mBall.x_ = W - offSet - r_ball;
        mBall.y_ = y_L;


    }

    /** 设置开关为--关 */
    private void setOFF() {

        mBall.x_ = r_ball + offSet;
        mBall.y_ = y_L;


    }

    /** 自定义的枚举状态 */
    protected enum SwitchState {
        ON, OFF
    }

    /** 自定义的圆圈按钮 */
    public class Ball extends BaseObject {

        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {
            x_ = x;
            y_ = y;
            this.radius = radius;

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {
            setPaintStyle(Paint.Style.FILL, false, 1, color_ball);
            canvas.save();
            canvas.drawCircle(x_, y_, radius, mPaint);
            setPaintStyle(Paint.Style.STROKE, false, 1, color_frame);
            canvas.drawCircle(x_, y_, radius, mPaint);
            canvas.restore();
        }

        @Override
        public void reset() {

        }


    }
}
