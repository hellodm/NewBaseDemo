package com.hello.app.MyView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/11/24
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MainTIpView extends RelativeLayout {

    /** 布局宽 */
    protected float W;

    /** 布局高 */
    protected float H;

    /** 布局中心X */
    protected float x_L;

    /** 布局中心Y */
    protected float y_L;

    @InjectView(R.id.icon_main_close)
    ImageView mClose;

    @InjectView(R.id.icon_main_open)
    ImageView mOpen;

    private ValueAnimator mAnimator;

    private PathMeasure mMeasure;

    public MainTIpView(Context context) {
        super(context);
        init();
    }

    public MainTIpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainTIpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.activity_main_tip, this);
        ButterKnife.inject(this, this);
//        mClose = new ImageView(this.getContext());
//        mOpen = new ImageView(this.getContext());
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        RelativeLayout.LayoutParams lp1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        mClose.setLayoutParams(lp);
//        mOpen.setLayoutParams(lp1);
//        mClose.setImageResource(R.drawable.main_close);
//        mOpen.setImageResource(R.drawable.main_open);
//        mClose.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goOpen();
//            }
//        });
//        mOpen.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goClose();
//            }
//        });

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        W = w;
        H = h;
        x_L = (int) (W / 2);
        y_L = (int) (H / 2);
    }


    @OnClick(R.id.icon_main_close)
    public void goOpen() {
        mClose.setVisibility(View.INVISIBLE);
        mOpen.setVisibility(View.VISIBLE);
        mMeasure = getPathMeasure(new PointF(mOpen.getX(), mOpen.getY()),
                new PointF(mOpen.getX(), mOpen.getY() + 50));
        setAnimator(mMeasure);
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    @OnClick(R.id.icon_main_open)
    public void goClose() {
        mOpen.setVisibility(View.INVISIBLE);
        mClose.setVisibility(View.VISIBLE);

    }

    private void setAnimator(PathMeasure measure) {
        mAnimator = ValueAnimator.ofFloat(0, measure.getLength());
        mAnimator.setDuration(500);
        mAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new BounceInterpolator());
//        mAnimator.setInterpolator(new OvershootInterpolator());
//        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
//                mOpen.setTranslationX(points[0]-915);
                mOpen.setTranslationY(-points[1]);
//                if(Math.abs(points[0]-pMain.x)<20&&Math.abs(points[1]-pMain.y)<20){
//                    pTouch.set(pMain.x,pMain.y);
//                    invalidate();
//                    isComplete=true;
//                }else if(!isComplete){
                Log.i("onAnimationUpdate", "x=" + points[0] + "y=" + points[1]);
//                }

            }
        });
    }

    private PathMeasure getPathMeasure(PointF start, PointF end) {

        Path path = new Path();
        path.reset();
        path.moveTo(start.x, start.y);
//        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);

        return new PathMeasure(path, true);

    }

}
