package com.hello.app.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by dong on 2014/7/29.
 */
public abstract class BaseToolView extends BaseView {


    public BaseToolView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttrs(attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawAll(canvas);
    }

    /** 初始化attrs */
    protected abstract void initAttrs(AttributeSet attr);

    /** init数据的初始化 */
    protected abstract void init();

    /** init数据的初始化 */
    protected abstract void drawAll(Canvas canvas);


    /**
     * 设置paint画笔的样式
     *
     * @param style   Paint填充样式
     * @param isRound 是否是圆头
     * @param width   画笔宽度
     * @param color   画笔颜色
     */
    protected void setPaintStyle(Paint.Style style, boolean isRound, int width, int color) {

        if (style != null) {
            mPaint.setStyle(style);
        }
        if (isRound) {
            mPaint.setStrokeCap(Paint.Cap.ROUND);
        } else {
            mPaint.setStrokeCap(Paint.Cap.BUTT);
        }

        if (width != 0) {
            mPaint.setStrokeWidth(width);
        }
        if (color != 0) {
            mPaint.setColor(color);
        }


    }

    /** 初始化画笔-黑-fill-3-方头 */
    protected void initPain() {
        setPaintStyle(Paint.Style.FILL, false, 3, Color.BLACK);
    }


}
