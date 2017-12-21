package com.hello.app.Base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


/**
 * Created by dong on 2014/7/29.
 */
public abstract class BaseToolSurfaceView extends BaseSurfaceView {


    public BaseToolSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }


    /**
     * 设置paint画笔的样式
     *
     * @param style   Paint填充样式
     * @param isRound 是否是圆头
     * @param width   画笔宽度
     * @param color   画笔颜色
     */
    public void setPaintStyle(Paint.Style style, boolean isRound, int width, int color) {

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
    public void initPain() {
        setPaintStyle(Paint.Style.FILL, false, 3, Color.BLACK);
    }


}
