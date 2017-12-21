package com.hello.app.MyView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2015/7/11
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class CheckScoreView extends ViewGroup {


    private View normalView;

    private View checkView;

    private int score = 0;

    private int progress = 0;

    private final int ALPHA_TIME = 200;

    private final int ALPHA_TIME_IN = 800;

    private AlphaAnimation normalOut;

    private AlphaAnimation normalIn;

    private AlphaAnimation checkIn;

    private AlphaAnimation checkOut;


    private Context mContext;

    private CheckState mState;

    public TextView mTextScore;

    @InjectView(R.id.check_text_progress)
    public TextView mTextProgress;

    @InjectView(R.id.image_bg)
    public ImageView mCheckBg;


    private ObjectAnimator mObjectAnimator;

    public enum CheckState {
        NORMAL, CHECKING
    }


    public CheckScoreView(Context context) {
        super(context);
        init();

    }

    public CheckScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckScoreView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        mContext = getContext();
        if (normalView == null) {
            normalView = LayoutInflater.from(mContext).inflate(R.layout.item_check_normal, null);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            mTextScore = (TextView) normalView.findViewById(R.id.check_text_score);
            normalView.setLayoutParams(lp);
            normalView.setVisibility(View.GONE);
            addView(normalView);
        }
        if (checkView == null) {
            checkView = LayoutInflater.from(mContext).inflate(R.layout.item_check_checking, null);
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            ButterKnife.inject(this, checkView);
            checkView.setLayoutParams(lp);

            addView(checkView);
//            mObjectAnimator =ObjectAnimator.ofFloat(this,"rotation",0,360).setDuration(500);
//            mObjectAnimator.start();
            RotateAnimation ratate = new RotateAnimation(0f, 360.0f, 180, 180);
            ratate.setRepeatCount(Animation.INFINITE);
            ratate.setDuration(800);
            ratate.setInterpolator(new LinearInterpolator());
            mCheckBg.setAnimation(ratate);

            //初始化渐变动画
            normalOut = initAnim(1f, 0f, ALPHA_TIME);
            normalIn = initAnim(0f, 1f, ALPHA_TIME_IN);
            checkOut = initAnim(1f, 0f, ALPHA_TIME);
            checkIn = initAnim(0f, 1f, ALPHA_TIME_IN);
        }


    }

    private AlphaAnimation initAnim(float fromAlpha, float toAlpha, int time) {

        AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(time);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.reset();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return animation;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (normalView != null) {
            normalView.measure(widthMeasureSpec, heightMeasureSpec);
        }
        if (checkView != null) {
            checkView.measure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int centerW = (r - l) / 2;
        int centerH = (b - t) / 2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        //布局的时候全部居中
        if (normalView != null) {
            int normalW = normalView.getMeasuredWidth();
            int normalH = normalView.getMeasuredHeight();
            final int left = paddingLeft + centerW - normalW / 2;
            final int top = paddingTop + centerH - normalH / 2;
            final int right = left + normalW;
            final int bottom = top + normalH;
            normalView.layout(left, top, right, bottom);

        }

        if (checkView != null) {
            int checkW = checkView.getMeasuredWidth();
            int checkH = checkView.getMeasuredHeight();
            final int left = paddingLeft + centerW - checkW / 2;
            final int top = paddingTop + centerH - checkH / 2;
            final int right = left + checkW;
            final int bottom = top + checkH;
            checkView.layout(left, top, right, bottom);
        }

    }


    /**
     * 设置检测状态
     *
     * @param state state
     */
    public void setCheckState(CheckState state) {

        mState = state;
        switch (state) {
            case NORMAL:
                normalView.setVisibility(View.VISIBLE);
                checkView.setVisibility(View.GONE);
                normalView.startAnimation(normalIn);
                checkView.startAnimation(checkOut);
                break;
            case CHECKING:
                normalView.setVisibility(View.GONE);
                checkView.setVisibility(View.VISIBLE);
                normalView.startAnimation(normalOut);
                checkView.startAnimation(checkIn);
                break;
        }

    }


    public int getScore() {
        return score;
    }

    /**
     * 设置检测分数
     *
     * @param score 检测分数
     */
    public void setScore(int score) {
        this.score = score;

    }


    public int getProgress() {
        return progress;
    }

    /**
     * 设置检测进度条
     *
     * @param progress 檢測进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }
}
