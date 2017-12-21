package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

import com.hello.app.MyView.loading.SmileBall.SmileState;
import com.hello.app.R;

import javax.xml.datatype.Duration;

public class SmileDrawable extends Drawable implements Animatable {

    public final String TAG = "SmileLoading";

    private View mParent;

    private Context mContext;

    private Animation mAnimation;

    private Animation mAnimLoading;

    private Bitmap mLoading;


    private boolean isAnimation = false;

    //distance
    private int mTotalDragDistance;

    private int mScreenWidth;

    private int mLoadingWidth;

    private int mLoadingHeight;

    private float scale;

    private float mPercent;


    private float time = 0;

    private static final int DURATION = 1000000;

    private ShapeAnimCallback mCallback;

    private SmileBall mBall;

    public interface ShapeAnimCallback {

        void animStop();

    }

    private Context getContext() {
        return mContext;
    }


    public SmileDrawable(Context context, View parent, ShapeAnimCallback callback) {
        mCallback = callback;
        mParent = parent;
        mContext = context;
        init();

    }

    public void init() {
        initiateDimens();
        createBitmaps();
        setupAnimations();
        initLoadingAnim();

        mBall = new SmileBall();
        mBall.notifyBallState(SmileState.ENUM_HAND);
    }

    private void initiateDimens() {
        PtrLocalDisplay.init(mContext);
        mTotalDragDistance = PtrLocalDisplay.dp2px(120);
        mLoadingHeight = mTotalDragDistance * 2 / 3;
        mLoadingWidth = mLoadingHeight;
        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;

    }

    private void createBitmaps() {
        mLoading = BitmapFactory
                .decodeResource(getContext().getResources(), R.drawable.icon_loading);
        mLoading = Bitmap.createScaledBitmap(mLoading, mLoadingWidth, mLoadingHeight, true);
    }


    @Override
    public void draw(Canvas canvas) {
        final int saveCount = canvas.save();
        mBall.notifyScale(scale);
        mBall.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void setupAnimations() {
        mAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                time = interpolatedTime * DURATION / 5;
                Log.d(TAG, "anim 动画进行时间time=" + time);
                mBall.setTime(time);
                refresh();

            }
        };

        mAnimation.setRepeatCount(0);
//        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(DURATION);
    }

    //初始化loading启动动画
    private void initLoadingAnim() {
        mAnimLoading = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

            }
        };
        mAnimLoading.setRepeatCount(0);
//        mAnimLoading.setRepeatMode(Animation.RESTART);
        mAnimLoading.setInterpolator(new OvershootInterpolator());
        mAnimLoading.setDuration(1000);
    }


    //drawable setBounds
    public void layout(int left, int top, int right, int bottom) {
        mBall.radius = 70;
        mBall.setLayout(left, top, right, bottom);
    }

    //设置当前下拉位置
    public void setPercent(int percent) {
        mPercent = percent;
        scale = mPercent / 100;
        refresh();
    }


    private void refresh() {
        mParent.invalidate();
        invalidateSelf();
    }

    @Override
    public void start() {
        isAnimation = true;
        mBall.setEnumState(SmileState.LOADING);
        mAnimation.reset();
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        isAnimation = false;
        mParent.clearAnimation();
        mBall.reset();
        mCallback.animStop();
        refresh();

    }

    @Override
    public boolean isRunning() {
        return isAnimation;
    }


    public void resetOriginals() {
        setPercent(0);
        time = 0;
    }

    public void notifyState(SmileState state) {
        mBall.notifyBallState(state);
    }


    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


}
