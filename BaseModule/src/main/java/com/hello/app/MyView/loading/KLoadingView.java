package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2016/1/5
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class KLoadingView extends LinearLayout
        implements ILoadingLayout, LoadingDrawable.ShapeAnimCallback {

    private LoadingDrawable mDrawable;

    LoadingRecycleView mRecycleView;

    private int centerX;

    private int centerY;


    public KLoadingView(Context context) {
        super(context);
        init();
    }

    public KLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mDrawable = new LoadingDrawable(getContext(), this, this);
        mRecycleView = new LoadingRecycleView(getContext());
        addView(mRecycleView);
        mRecycleView.setVisibility(GONE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mDrawable.layout(left, top, right, bottom);
        mRecycleView.layout(centerX - 100, centerY - 100, centerX + 100, centerY + 100);

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mDrawable.draw(canvas);
    }

    //
    @Override
    public void pullProgress(int progress) {
        mDrawable.setPercent(progress);
        if (progress > 80 && !mDrawable.isRunning() && !mRecycleView.isRunning()) {
            mDrawable.start();
        }
    }

    @Override
    public void notifyState(Object state) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void invalidateDrawable(Drawable dr) {
        if (dr == mDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(dr);
        }
    }

    //LoadingDrawable 动画完成回调
    @Override
    public void animStop() {
        mRecycleView.setVisibility(VISIBLE);
        mRecycleView.start();
    }
}
