package com.hello.app.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dong on 2014/9/28.
 * 自定义的无SurFace的View
 */
public abstract class BaseView extends View {


    protected Context mContext;

    protected Paint mPaint;

    /** 布局宽 */
    protected float W;

    /** 布局高 */
    protected float H;

    /** 布局中心X */
    protected float x_L;

    /** 布局中心Y */
    protected float y_L;

    /** surface的刷新状态 */
    private boolean isRefresh = true;

    /** 线程控制 */
    private Thread thread;


    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        mPaint.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint.setSubpixelText(true);
        mPaint.setDither(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        W = w;
        H = h;
        x_L = (int) (W / 2);
        y_L = (int) (H / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
