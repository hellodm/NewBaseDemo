package com.hello.app.MyView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;

import com.hello.app.Base.BaseView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/4
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class QQMessageView extends BaseView {

    private float r_scale = 4 / 5;

    boolean isComplete = false;

    private Path mPath;

    private RectF mRectF;

    private PointF pMain, p1, p2, pCenter, pC1, pC2, p3, p4, pTouch;

    private float mRadius;

    private float mTempR;

    private ValueAnimator mAnimator;

    private PathMeasure mMeasure;

    private double angle = 0;//触摸点和原始点的倾斜角度


    public QQMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        mAnimator = new ValueAnimator();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                pTouch.set(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                pTouch.set(event.getX(), event.getY());
                releaseTouch();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void releaseTouch() {
        mMeasure = getPathMeasure(pTouch, pMain);
        setAnimator(mMeasure);
        startAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = mTempR = h / 2;
        pMain = new PointF(x_L, y_L);
        p1 = new PointF();
        p2 = new PointF();
        p3 = new PointF();
        p4 = new PointF();
        pC1 = new PointF();
        pC2 = new PointF();
        pTouch = new PointF(x_L, y_L);
        pCenter = new PointF();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        drawOval(canvas);
        drawPath(canvas);
        drawCircle(canvas, pMain, mTempR * 4 / 5);
        drawCircle(canvas, pTouch, mTempR);
        drawNumber(canvas);
//        drawHalfCircle(canvas,pMain, piToAngle((float) angle)+110);
//        drawHalfCircle(canvas,pTouch, piToAngle((float) angle)-70);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 绘制椭圆
     *
     * @param canvas 画布
     */
    private void drawOval(Canvas canvas) {
        mRectF = new RectF(0, 0, W, H);
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.drawOval(mRectF, mPaint);
        canvas.restore();
    }

    private void drawNumber(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mRadius * 3 / 2);
        canvas.save();
        canvas.drawText("8", pTouch.x, pTouch.y + mRadius * 3 / 6, mPaint);
        canvas.restore();
    }

    /** 绘制圆圈 */
    private void drawCircle(Canvas canvas, PointF point, float radius) {
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.drawCircle(point.x, point.y, radius, mPaint);
        canvas.restore();
    }

    /**
     * 绘制半圆
     *
     * @param point 圆心
     * @param angle 开始角度
     */
    private void drawHalfCircle(Canvas canvas, PointF point, float angle) {
        RectF rectF = new RectF(point.x - mTempR, point.y - mTempR, point.x + mTempR,
                point.y + mTempR);
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.drawArc(rectF, angle, 200, true, mPaint);
        canvas.restore();
    }

    /**
     * 绘制path
     *
     * @param canvas 画布
     */
    private void drawPath(Canvas canvas) {
        setPoint();
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }


    //设置path点的数据
    private void setPoint() {
        double len = Math.sqrt(Math.pow(pTouch.x - pMain.x, 2) + Math.pow(pTouch.y - pMain.y, 2));
        mTempR = (float) (mRadius - len / 15);
        angle = Math.atan((double) (pTouch.x - pMain.x) / (double) (pTouch.y - pMain.y));
        pCenter.set((pTouch.x - pMain.x) / 2 + pMain.x, (pTouch.y - pMain.y) / 2 + pMain.y);
        float offsetX = (float) (mTempR * Math.cos(angle));
        float offsetY = (float) (mTempR * Math.sin(angle));
        float offsetX_ = (float) (mTempR * 4 / 5 * Math.cos(angle));
        float offsetY_ = (float) (mTempR * 4 / 5 * Math.sin(angle));
        p1.set(pMain.x + offsetX_, pMain.y - offsetY_);
        p2.set(pMain.x - offsetX_, pMain.y + offsetY_);
        p3.set(pTouch.x + offsetX, pTouch.y - offsetY);
        p4.set(pTouch.x - offsetX, pTouch.y + offsetY);
//        pC1.set(pCenter.x + offsetX / 4, pCenter.y - offsetY / 4);
//        pC2.set(pCenter.x - offsetX / 4, pCenter.y + offsetY / 4);
        pC1.set(pCenter.x, pCenter.y);
        pC2.set(pCenter.x, pCenter.y);
        mPath.reset();
        mPath.moveTo(p1.x, p1.y);
        mPath.lineTo(p2.x, p2.y);
        mPath.quadTo(pC2.x, pC2.y, p4.x, p4.y);
        mPath.lineTo(p3.x, p3.y);
        mPath.quadTo(pC1.x, pC1.y, p1.x, p1.y);
    }

    private float piToAngle(float radian) {

        return (float) (180 * radian / Math.PI);
    }

    private PathMeasure getPathMeasure(PointF start, PointF end) {

        Path path = new Path();
        path.reset();
        path.moveTo(start.x, start.y);
//        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);

        return new PathMeasure(path, true);

    }

    private void setAnimator(PathMeasure measure) {
        mAnimator = ValueAnimator.ofFloat(0, measure.getLength() / 2);
        mAnimator.setDuration(300);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
//        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setInterpolator(new OvershootInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
//                if(Math.abs(points[0]-pMain.x)<20&&Math.abs(points[1]-pMain.y)<20){
//                    pTouch.set(pMain.x,pMain.y);
//                    invalidate();
//                    isComplete=true;
//                }else if(!isComplete){
                Log.i("onAnimationUpdate", "x=" + points[0] + "y=" + points[1]);
                pTouch.set(points[0], points[1]);
                invalidate();
//                }

            }
        });
    }

    private void startAnim() {
        mAnimator.start();
    }


}
