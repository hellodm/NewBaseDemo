package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hello.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

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
public class KMenuView extends LinearLayout implements OnClickListener, OnLongClickListener {


    Button mImageButton;

    private int number = 4;

    private float allRange = 180;

    private float dump = 0.55f; //滑动阻尼0-1, 越大阻力越大

    private int centerX;

    private int centerY;

    private List<KMenuBall> mMenus;

    private List<AnimControl> mAnimControls;

    private Paint mPaint;

    private boolean isShowMenu = false;

    private PointF firstP = new PointF(); //第一个触摸的点

    private PointF lastP = new PointF(); //触摸上一个点

    private PointF nowP = new PointF(); //当前触摸的点


    public KMenuView(Context context) {
        super(context);
        init();
    }


    public KMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ButterKnife.inject(this);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        mPaint.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint.setSubpixelText(true);
        mPaint.setDither(true);
        mImageButton = new Button(getContext());
        mImageButton.setOnLongClickListener(this);
        addView(mImageButton);
        mImageButton.setBackgroundResource(R.drawable.icon_time_pick);
        setClipChildren(false);
    }

    /**
     * 初始化菜单
     */
    private void initMenu() {
        mAnimControls = new ArrayList<>();
        mMenus = new ArrayList<>();
        float angle = allRange / number;
        float start = 0;
        float end = angle;
        for (int i = 0; i < number; i++) {
            KMenuBall mBall = new KMenuBall(this.getContext());
            AnimControl<KMenuBall> control = new AnimControl<>(mBall);
            mBall.setParams(centerX, centerY, start, end, 300);
            start += angle;
            end += angle;
            control.createPathAnim(mBall.getMainCenter(), mBall.getCenter());
            mAnimControls.add(control);
            mBall.setVisibility(GONE);
            mMenus.add(mBall);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(120, 120);
            mBall.setLayoutParams(lp);
            mBall.setBackgroundResource(R.color.red);
            addView(mBall);
        }


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
        initMenu();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        mImageButton.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nowP.set(event.getX(), event.getY());
                lastP.set(event.getX(), event.getY());
                firstP.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                nowP.set(event.getX(), event.getY());
                float angle = getAngle(firstP, nowP);
                Log.d("move", "angle=" + angle);
                int poi = getNearBall(angle);
                Log.d("move", "poi=" + poi);
                touchBall(poi, lastP, nowP);
                lastP.set(nowP.x, nowP.y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                nowP.set(0, 0);
                lastP.set(0, 0);
                switchMenu();
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void switchMenu() {
        showMenu(!isShowMenu);
    }


    public void showMenu(boolean isShow) {
        for (KMenuBall b : mMenus) {
            b.setVisibility(isShow ? VISIBLE : GONE);
        }
        if (isShow) {
            startAnim();
        } else {
            cancelAnim();
        }
        isShowMenu = isShow;

    }


    public void startAnim() {

        for (AnimControl b : mAnimControls) {
            b.startAnim();
        }
    }

    public void cancelAnim() {

        for (AnimControl b : mAnimControls) {
            b.stopAnim();
        }
        for (KMenuBall b : mMenus) {
            b.reset();
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        if (v == mImageButton) {
            switchMenu();
            return true;
        }
        return false;
    }

    //2个坐标的偏移角
    public float getAngle(PointF start, PointF end) {
        float angle = 0;
        //偏移向量
        float offsetX = end.x - start.x;
        float offsetY = end.y - start.y;
        float pi = (float) Math.atan(Math.abs(offsetX / offsetY));
        if (offsetX > 0 && offsetY < 0) {
            angle = piToAngle(pi);
        } else if (offsetX > 0 && offsetY > 0) {
            angle = 180 - piToAngle(pi);
        } else if (offsetX > 0 && offsetY == 0) {
            angle = 90;
        }

        return angle;

    }


    //弧度转角度
    private float piToAngle(float radian) {

        return (float) (180 * radian / Math.PI);
    }

    //角度转弧度
    private float angleToPi(float radian) {

        return (float) (Math.PI * radian / 180);
    }

    //获取最相近角度的Ball位置
    private int getNearBall(float angle) {
        int position = -1;
        for (int i = 0; i < mMenus.size(); i++) {
            if (angle <= mMenus.get(i).getRangeEnd() && angle >= mMenus.get(i).getRangeStart()) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void touchBall(int poi, PointF start, PointF end) {
        //xy偏移向量
        KMenuBall ball = mMenus.get(poi);
        float offsetX = Math.abs(end.x - start.x);
        float offsetY = Math.abs(end.y - start.y);
        //s方向偏移值
        float offsetS = (float) Math.sqrt(Math.pow(offsetX, 2) + Math.pow(offsetY, 2)) * (1 - dump);
        float addX = (float) (offsetS * Math.sin(angleToPi(ball.getRangeCenter())));
        float addY = (float) (offsetS * Math.cos(angleToPi(ball.getRangeCenter())));
        ball.setTranslationX(ball.getX() + addX);
        ball.setTranslationY(ball.getY() - addY);
        ball.scaleRadius();
        invalidate();
    }


}
