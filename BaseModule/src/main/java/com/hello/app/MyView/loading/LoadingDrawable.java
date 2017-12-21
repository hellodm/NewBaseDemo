package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;

import com.hello.app.MyView.loading.LoadingBall.BallState;
import com.hello.app.R;

public class LoadingDrawable extends Drawable implements Animatable {


    private View mParent;

    private Context mContext;

    private Animation mAnimation;

    private Bitmap mLoading;

    private static final int DURATION = LoadingBall.DURATION;


    private boolean isAnimation = false;

    //distance
    private int mTotalDragDistance;

    private int mScreenWidth;

    private int mLoadingWidth;

    private int mLoadingHeight;

    private float scale;

    private float mPercent;


    private float time = 0;


    private ShapeAnimCallback mCallback;

    private LoadingBall mBall;

    public interface ShapeAnimCallback {

        void animStop();

    }

    private Context getContext() {
        return mContext;
    }


    public LoadingDrawable(Context context, View parent, ShapeAnimCallback callback) {
        mCallback = callback;
        mParent = parent;
        mContext = context;
        init();

    }

    public void init() {
        initiateDimens();
        createBitmaps();
        setupAnimations();

        mBall = new LoadingBall();
        mBall.notifyBallState(BallState.ENUM_HAND);
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
                time = interpolatedTime * DURATION;
                if (time >= DURATION) {
                    mBall.setEnumState(BallState.EXTRA);
                } else if (time >= DURATION * 4 / 5) {
                    mBall.setEnumState(BallState.FOUR);
                } else if (time >= DURATION * 3 / 5) {
                    mBall.setEnumState(BallState.THREE);
                } else if (time >= DURATION * 2 / 5) {
                    mBall.setEnumState(BallState.TWO);
                }
                mBall.setTime(time);
                Log.i("interpolatedTime", time + "");
                refresh();

            }
        };
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                stop();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation.setRepeatCount(0);
//        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(new OvershootInterpolator());
        mAnimation.setDuration(DURATION);
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
        mBall.setEnumState(BallState.ONE);
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


    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }


}
