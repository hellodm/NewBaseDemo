package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2015/1/9
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class CalendarDayView extends BaseToolView {

    private float w_v;//view控件显示的宽

    private float h_v; //view控件显示的高

    private float radioCircle;//背景圆圈的半径

    private int colorCircle = Color.GRAY;


    public CalendarDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawCircle(canvas);


    }

    /** 绘制圆圈 */
    private void drawCircle(Canvas canvas) {
        setPaintStyle(Paint.Style.STROKE, false, 2, colorCircle);

        canvas.drawCircle(x_L, y_L, radioCircle, mPaint);

    }

    /**绘制中心布局*/


    /** 自定义的每天日期的图例 */
    private class SingleDay extends BaseObject {

        private float w = -1;

        private float h = -1;

        private int textSize = -1;

        private String content;


        private int color_hint = Color.GRAY;

        private int color_text = Color.BLACK;


        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        public void init(float x, float y, String textContent, float width,
                float height) {
            x_ = x;
            y_ = y;
            content = textContent;
            w = width;
            h = height;
            textSize = (int) (height / 2);

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);


        }

        @Override
        public void reset() {

        }
    }


}
