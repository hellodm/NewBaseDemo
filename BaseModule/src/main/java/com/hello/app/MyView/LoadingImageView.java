package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hello.app.R;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2015/8/24
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class LoadingImageView extends ImageView {


    private Indicator mIndicator;

    public float progress_w;//指示器的进度条宽

    public int progress_back_c;//指示器的背景色

    public int progress__c;//指示器的进度条颜色

    public float progress_r;//指示器的进度条半径


    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.LoadingImageView));
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.LoadingImageView));
    }


    private void parseAttributes(TypedArray a) {
        progress_w = a.getDimension(R.styleable.LoadingImageView_progressWidth, 0f);
        progress_r = a.getDimension(R.styleable.LoadingImageView_progressRadius, 0f);
        progress_back_c = a.getColor(R.styleable.LoadingImageView_progressBackColor,
                Color.argb(150, 0, 0, 0));
        progress__c = a
                .getColor(R.styleable.LoadingImageView_progressColor,
                        Color.argb(200, 255, 255, 255));

        a.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        final int childCount = getChildCount();
//        if (childCount <= 1) {
//            throw new IllegalStateException("MyPtrLayout must host 2 elements");
//        } else if (childCount > 2) {
//            throw new IllegalStateException("MyPtrLayout only can host 2 elements");
//        } else {
//            View child1 = getChildAt(0);
//            View child2 = getChildAt(1);
//            if (child1 instanceof PtrUIHandler) {
//                mHeaderView = (PtrHeaderView) child1;
//                mCurrentContentView = mContentView = child2;
//            } else if (child2 instanceof PtrUIHandler) {
//                mHeaderView = (PtrHeaderView) child2;
//                mCurrentContentView = mContentView = child1;
//            }
//
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        progress_w = progress_w == 0 ? w / 20 : progress_w;
        progress_r = progress_r == 0 ? w / 6 : progress_r;
        mIndicator = new Indicator(w / 2, h / 2, progress_r, progress_w, progress_back_c,
                progress__c);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mIndicator.onDraw(canvas);
    }


    /** 设置中间进度条的开启关闭 */
    public void setLoading(boolean isShown) {
        mIndicator.setLoading(isShown);
        postInvalidate();
    }

    public boolean getLoadingState() {
        return mIndicator.getLoadingState();
    }

    /**
     * 设置进度值
     *
     * @param percent 百分比 0-100
     */
    public void setProgress(int percent) {
        mIndicator.setProgress(percent);
        postInvalidate();
    }


    private class Indicator {

        private Paint paintA;

        private Paint paintB;

        private Paint paintC;

        public float x;

        public float y;

        public float r;

        public float progress_w;

        public int progress_back_c;//指示器的背景色

        public int progress_c;//指示器的进度条颜色

        private int progress = 0; //指示器的进度值/百分比

        private float val = 0; //指示器的进度值

        private boolean isShown = false;


        public Indicator(float x, float y, float r, float progress_w, int color_b, int color_p) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.progress_w = progress_w;
            this.progress_back_c = color_b;
            this.progress_c = color_p;
            init();
        }

        private void init() {

            paintA = new Paint();
            paintA.setStyle(Paint.Style.STROKE); //设置画笔填充
            paintA.setAntiAlias(true);    //设置画笔抗锯齿
            paintA.setSubpixelText(true);
            paintA.setDither(true);
            paintB = new Paint();
            paintB.setStyle(Paint.Style.STROKE); //设置画笔填充
            paintB.setAntiAlias(true);    //设置画笔抗锯齿
            paintB.setSubpixelText(true);
            paintB.setDither(true);
            paintB.setStrokeCap(Paint.Cap.ROUND);
            paintC = new Paint();
            paintC.setStyle(Paint.Style.FILL); //设置画笔填充
            paintC.setAntiAlias(true);    //设置画笔抗锯齿
            paintC.setSubpixelText(true);
            paintC.setDither(true);

            mRectF = new RectF(x - r, y - r, x + r, y + r);

            paintA.setColor(progress_back_c);
            paintA.setStrokeWidth(progress_w);
            paintB.setColor(progress_c);
            paintB.setStrokeWidth(progress_w);
            paintC.setColor(progress_back_c);
        }

        public void setLoading(boolean isShown) {
            this.isShown = isShown;
        }

        public boolean getLoadingState() {
            return isShown;
        }

        public void reset() {
            val = 0;
        }


        public int getProgress() {
            return progress;
        }

        public void setProgress(int percent) {
            isShown = isShown == true ? isShown : true;
            this.val = (float) percent * 3.6f;
            val = val > 360 ? 360 : val;
        }

        RectF mRectF;

        public void onDraw(Canvas canvas) {
            if (isShown) {
                //绘制背景色
                canvas.save();
                canvas.drawCircle(x, y, r + r * 4 / 9, paintC);
                canvas.restore();
                //绘制背景环
//                canvas.save();
//                canvas.drawCircle(x, y, r, paintA);
//                canvas.restore();
                //绘制进度条
                canvas.save();
                canvas.drawArc(mRectF, 270, val, false, paintB);
                canvas.restore();

            }


        }

    }


}
