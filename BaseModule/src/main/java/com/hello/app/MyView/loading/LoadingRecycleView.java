package com.hello.app.MyView.loading;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static com.hello.app.R.drawable.anim_loading_fly;
import static com.hello.app.R.drawable.anim_loading_grow;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2016/1/7
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class LoadingRecycleView extends View implements Animatable {

    private AnimationDrawable mGrowDrawable;

    private AnimationDrawable mFlyDrawable;

    public LoadingRecycleView(Context context) {
        super(context);
        init();
    }

    public LoadingRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LoadingRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mGrowDrawable = (AnimationDrawable) getResources().getDrawable(anim_loading_grow);
        mFlyDrawable = (AnimationDrawable) getResources().getDrawable(anim_loading_fly);
        setBackground(mGrowDrawable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            widthMeasureSpec = MeasureSpec
                    .makeMeasureSpec(mGrowDrawable.getIntrinsicWidth(), MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.AT_MOST);
        }
        if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            heightMeasureSpec = MeasureSpec
                    .makeMeasureSpec(mGrowDrawable.getIntrinsicHeight(), MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }


    Handler handler = new Handler() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //此处调用第二个动画播放方法
                setBackground(mFlyDrawable);
                mFlyDrawable.start();
            }

        }
    };


    @Override
    public void start() {
        mGrowDrawable.start();
        handler.postDelayed(new Runnable() {
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 640);

    }

    @Override
    public void stop() {
        mGrowDrawable.stop();
    }

    @Override
    public boolean isRunning() {
        return mGrowDrawable.isRunning();
    }
}
