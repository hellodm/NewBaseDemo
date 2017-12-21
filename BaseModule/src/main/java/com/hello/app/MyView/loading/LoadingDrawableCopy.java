package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.hello.app.R;

public class LoadingDrawableCopy extends Drawable implements Animatable {


    private View mParent;

    private Context mContext;

    private Matrix mMatrix;

    private Animation mAnimation;

    private Bitmap mLoading;

    private static final int DURATION = 500;

    private static final int ENUM_HAND = 0x1;

    private static final int ENUM_AUTO_1 = 0x2;

    private static final int ENUM_AUTO_2 = 0x3;

    private static final int ENUM_AUTO_3 = 0x4;


    private static final int ENUM_EXIT = 0x6;

    private int enumState = ENUM_HAND;

    private boolean isAnimation = false;

    //distance
    private int mTotalDragDistance;

    private int mScreenWidth;

    private int mLoadingWidth;

    private int mLoadingHeight;

    private float scale;

    private Paint mPaint;

    private Paint mPaint_S;

    private float mPercent;

    private float mRotate = 0.0f;


    private float time = 0;

    //圆圈动画的属性
    private float radius = 0f; //变化的半径

    private float r_h = 0f; //水平变化的半径

    private float r_v = 0f; //垂直变化的半径

    private float r_mid = 0f; //中间半径

    private float r_max = 0f; //最大的半径

    private float circleX;

    private float circleY;

    private float speed;

    private ShapeAnimCallback mCallback;

    public interface ShapeAnimCallback {

        public void animStop();

    }

    private Context getContext() {
        return mContext;
    }


    public LoadingDrawableCopy(Context context, View parent, ShapeAnimCallback callback) {
        mCallback = callback;
        mParent = parent;
        mContext = context;
        mMatrix = new Matrix();
        init();

    }

    public void init() {
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
        initiateDimens();
        createBitmaps();
        setupAnimations();
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
//        drawBack(canvas);
        drawCircle(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void drawBack(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();
        canvas.drawBitmap(mLoading, matrix, null);
    }

    private void drawCircle(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();
        RectF rectF;

        if (enumState == ENUM_HAND) {
            radius = 100 * scale;
            if (scale < 0.4) {
                r_mid = radius;
                canvas.drawCircle(circleX, circleY, radius, mPaint);
                canvas.drawCircle(circleX, circleY, radius, mPaint_S);
                ;
            } else {
                r_max = radius;
                rectF = new RectF(circleX - r_mid, circleY - r_max, circleX + r_mid,
                        circleY + r_max);

                canvas.drawOval(rectF, mPaint);
                canvas.drawOval(rectF, mPaint_S);
            }
        } else if (enumState == ENUM_AUTO_1 && isRunning()) {
            if (time * speed > r_max - r_mid) {
                enumState = ENUM_AUTO_2;
            }
            float d = time * speed;
            rectF = new RectF(circleX - r_mid - d, circleY - r_max + d, circleX + r_mid + d,
                    circleY + r_max - d);
            canvas.drawOval(rectF, mPaint);
            canvas.drawOval(rectF, mPaint_S);


        } else if (enumState == ENUM_AUTO_2 && isRunning()) {
            float d = time * speed - r_max + r_mid;
            if (d > r_max - r_mid) {

                enumState = ENUM_AUTO_3;
            }
            rectF = new RectF(circleX - r_max + d, circleY - r_mid - d, circleX + r_max - d,
                    circleY + r_mid + d);
            canvas.drawOval(rectF, mPaint);
            canvas.drawOval(rectF, mPaint_S);
        } else if (enumState == ENUM_AUTO_3 && isRunning()) {

            float d = time * speed - 2 * (r_max - r_mid);
            rectF = new RectF(circleX - r_mid - d, circleY - r_max, circleX + r_mid + d,
                    circleY + r_max);
            canvas.drawOval(rectF, mPaint);
            canvas.drawOval(rectF, mPaint_S);
        } else if (enumState == ENUM_EXIT) {
//            canvas.drawBitmap(mLoading, circleX - mLoadingWidth / 2, circleY - mLoadingHeight / 2,
//                    mPaint);
        }


    }

    //绘制弹跳
    private void drawBounce(Canvas canvas) {

    }

    private void setupAnimations() {
        mAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                time = interpolatedTime * DURATION;
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
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(DURATION);
    }


    //drawable setBounds
    public void layout(int centerX, int centerY) {
        circleX = centerX;
        circleY = centerY;
//        setBounds(centerX - mLoadingWidth / 2, centerY - mLoadingHeight / 2,
//                centerX + mLoadingWidth / 2, centerY + mLoadingHeight / 2);
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
        enumState = ENUM_AUTO_1;
        //计算速率
        speed = (r_max - r_mid) * 3 / DURATION;

        mAnimation.reset();
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        isAnimation = false;
        mParent.clearAnimation();
        enumState = ENUM_EXIT;
        mCallback.animStop();
        refresh();

    }

    @Override
    public boolean isRunning() {
        return isAnimation;
    }


    public void resetOriginals() {
        setPercent(0);
        enumState = ENUM_HAND;
        time = 0;
        speed = 0;
        r_max = 0;
        radius = 0;
        r_mid = 0;
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
