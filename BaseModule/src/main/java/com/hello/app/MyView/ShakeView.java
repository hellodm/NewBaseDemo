package com.hello.app.MyView;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hello.app.R;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2015/2/26
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class ShakeView extends ImageView implements View.OnLongClickListener {


    private static final float DEGREE = -3f; //左右偏移量

    private static final int ANIMATION_DURATION = 80;//摆动一次时间

    private static final int SHAKE_DURATION = 100;//手机震动时间

    private float x; //图标的x坐标

    private float y;//图标的y坐标

    private boolean isShake = false;

    private RotateAnimation rotateA;

    private RotateAnimation rotateB;

    private float icon_width;

    private Context mContext;

    private Drawable mDrawable = null;

    private Bitmap mBitmap = null;

    private boolean isFirst = true;

    private OnShakeListener mOnShakeListener;

    private int position;

    private float x_temp;

    private float y_temp;


    public ShakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        this.setOnLongClickListener(this);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.about_logo);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        icon_width = MeasureSpec.getSize(widthMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        icon_width = w;
        initAnimation(icon_width / 2, icon_width / 2);
    }

    public void setOnShakeListener(OnShakeListener onShakeListener, int position) {
        mOnShakeListener = onShakeListener;
        this.position = position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * 长按监听
     */
    @Override
    public boolean onLongClick(View v) {
        if (!isShake) {
            startShake();
        }

        return false;
    }

    /** view的触摸监听 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isShake) {

            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    Log.i("ACTION", "ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("ACTION", "ACTION_MOVE");
//                    x = eventX - getMeasuredWidth() / 2;
//                    y = eventY - getMeasuredHeight() / 2;

//                    goView(eventX, eventY, this);
//                    translationView(eventX, eventY, this);

                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("ACTION", "ACTION_UP");
//                    startShake();
                    break;

            }

            return false;
        } else {
            return super.onTouchEvent(event);
        }

    }

    /**
     *
     * @param x
     * @param y
     * @param view
     */
    public void goView(float x, float y, View view) {

        if (isFirst) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            x_temp = location[0];
            y_temp = location[1];
            isFirst = false;
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins((int) (x - x_temp - view.getWidth() / 2 + 0.5),
                (int) (y - y_temp - view.getHeight() / 2 + 0.5), 0, 0);
        view.setLayoutParams(layoutParams);

    }

    public void translationView(float transX, float transY, View view) {

        if (isFirst) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            x_temp = location[0];
            y_temp = location[1];
            isFirst = false;
        }

        x = transX;
        y = transY;

        view.setTranslationX(transX - x_temp - view.getMeasuredWidth() / 2);
        view.setTranslationY(transY - y_temp - view.getMeasuredHeight() / 2);

    }


    /** 初始化动画实例 */
    private void initAnimation(float pivotX, float pivotY) {

        rotateA = new RotateAnimation(DEGREE, -DEGREE, pivotX, pivotY);
        rotateB = new RotateAnimation(-DEGREE, DEGREE, pivotX, pivotY);

        rotateA.setDuration(ANIMATION_DURATION);
        rotateB.setDuration(ANIMATION_DURATION);

        rotateA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShake()) {
                    startAnimation(rotateB);
                } else {
                    clearAnimation();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rotateB.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShake()) {
                    startAnimation(rotateA);
                } else {
                    clearAnimation();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /**
     * 启动图标晃动
     */
    public void startShake() {
        isShake = true;
        //启动手机震动
        shakePhone((Activity) mContext, SHAKE_DURATION);
//        initAnimation(x - x_temp, y - y_temp);
        //启动动画qsqg
        startAnimation(rotateA);
        //通知监听
        if (mOnShakeListener != null) {
            mOnShakeListener.notifyShake(position);
        }


    }

    /**
     * 关闭图标晃动
     */
    public void stopShake() {
        isShake = false;
//        clearAnimation();
//        invalidate();
    }

    /** 获取图标是否晃动 */
    public boolean isShake() {

        return isShake;
    }

    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    private void shakePhone(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }


    /** ShakeView监听 */
    public interface OnShakeListener {

        public void notifyShake(int position);

    }


}
