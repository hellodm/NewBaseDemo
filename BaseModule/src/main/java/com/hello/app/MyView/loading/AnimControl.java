package com.hello.app.MyView.loading;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/21
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class AnimControl<V extends View> {

    private final int DURATION = 500;

    public PointF mStart, mEnd;

    private PathMeasure mMeasure;

    private ValueAnimator mAnimator;

    private OnXYCallBack mCallBack;

    private V mView;

    AnimControl(V view) {
        mView = view;
    }

//    public AnimControl(PointF start, PointF end, OnXYCallBack callBack) {
//        mStart = start;
//        mEnd = end;
//        mCallBack = callBack;
//    }

    /**
     * 构造动画路径
     *
     * @param start 起始点
     * @param end   终点
     */
    public void createPathAnim(PointF start, PointF end) {
        mStart = start;
        mEnd = end;
        mAnimator = new ValueAnimator();
        mMeasure = getPathMeasure(start, end);
        setAnimator(mMeasure);
    }

    public void startAnim() {
        mAnimator.start();
    }

    public void stopAnim() {
        mAnimator.cancel();
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
        mAnimator.setDuration(DURATION);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
//        mAnimator.setInterpolator(new BounceInterpolator());
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
                mView.setTranslationX(points[0]);
                mView.setTranslationY(points[1]);
                Log.i("onAnimationUpdate", "x=" + points[0] + "y=" + points[1]);

            }
        });
    }

    public interface OnXYCallBack {

        void callBack(float x, float y);
    }

}
