package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;
import com.hello.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/12/24
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class CalendarYearView extends BaseToolView {

    public static float scale = 37f / 41f;//标准的W/H

    private float w_s; //single日期的宽

    private float h_s;//single日期的高

    private float w_v;//view控件显示的宽

    private float h_v; //view控件显示的高

    private float h_t;//title的高度

    private float h_d;//date方框的高度

    private float w_d;//date方框的宽度

    private int year;

    private int month = 12;

    private float value = 654;

    private int monthSize;

    private int valueSize;

    private int color_blue = Color.argb(255, 65, 190, 255);

    private List<SingleData> mList = new ArrayList<SingleData>();

    private List<SingleDay> mListView = new ArrayList<SingleDay>();

    private Bitmap mbitmap;

    public CalendarYearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mbitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_rili);

    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {

        if (H >= W / scale) {
            w_v = W;
            h_v = w_v / scale;
        } else {
            h_v = H;
            w_v = scale * h_v;

        }
        h_t = h_v * 8f / 41f;
        h_d = h_v - h_t;
        w_s = w_v / 7f;
        h_s = h_d / 6f;

        //初始化字体size
        monthSize = (int) (h_t * 5 / 8);
        valueSize = (int) (h_t * 3 / 8);

        mbitmap = mbitmap.createScaledBitmap(mbitmap, (int) (w_s - 3), (int) (w_s - 3), false);

        //数据初始化
        for (int i = 0; i < 31; i++) {

            SingleData data = new SingleData();
            data.date = i + 1;
            data.isHint = false;
            data.isBitmap = false;
            data.week = (i + 7) % 7;
            if (data.week == 0) {
                data.week = 7;
            }
            if (i == 10 || i == 26) {    //自己写死数据
                data.isBitmap = true;
            }
            if (i < 8) {
                data.isHint = true;
            }
            mList.add(data);
        }

        //全部dayView数据初始化
        float x;
        float y;
        int chu;
        float chuF;
        int yu;
        for (int i = 1; i <= 42; i++) {

            SingleDay day = new SingleDay();
            chuF = (float) i / 7f;
            chu = i / 7; //除数
            chu = (chuF == chu) ? (chu - 1) : chu;
            yu = i % 7 == 0 ? 7 : i % 7; //余数
            x = w_s * (yu * 2 - 1) / 2;
            y = h_s * (chu * 2 + 1) / 2 + h_t;
            day.init(x, y, DayState.none, "", w_s, h_s);
            day.mState = DayState.text;
            mListView.add(day);

        }

        calculatePosition(mList);

    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawTitle(canvas);
        for (SingleDay day : mListView) {
            day.onDrawSelf(canvas, mPaint);
        }
    }


    //绘制Title
    private void drawTitle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        setPaintStyle(Paint.Style.FILL, false, 1, Color.BLACK);
        mPaint.setTextSize(monthSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.save();
        canvas.drawText(month + "月", monthSize, (h_t + monthSize) / 2, mPaint);
        canvas.restore();

        //绘制右边价值
        mPaint.setColor(color_blue);
        mPaint.setTextSize(valueSize);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.save();
        canvas.drawText(value + "元", w_v - 10f, (h_t + valueSize) / 2, mPaint);
        canvas.restore();
    }

    /** 通过传入数据计算位置 */
    private void calculatePosition(List<SingleData> mList) {

        int start = mList.get(0).week - 1;
        for (int i = 0; i < mList.size(); i++) {
            SingleDay day = mListView.get(start + i);
            SingleData data = mList.get(i);
            day.mState = DayState.text;
            day.content = data.date + "";
            if (data.isBitmap) {
                day.mState = DayState.image;
                day.setBitmap(mbitmap);
            }
            if (data.isHint) {
                day.mState = DayState.hint;
            }
        }

    }


    /** 状态 */
    protected enum DayState {

        none(0), hint(1), text(2), image(3);

        final int nativeInt;

        private DayState(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }

    /** 自定义的每天日期的图例 */
    private class SingleDay extends BaseObject {

        private float w = -1;

        private float h = -1;

        private int textSize = -1;

        private String content;

        private DayState mState = DayState.text;

        private int color_hint = Color.GRAY;

        private int color_text = Color.BLACK;


        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        public void init(float x, float y, DayState state, String textContent, float width,
                float height) {
            x_ = x;
            y_ = y;
            mState = state;
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
            canvas.save();
//            //绘制边框*/
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setColor(Color.GRAY);
//            canvas.drawRect(x_ - w / 2, y_ - h / 2, x_ + w / 2, y_ + h / 2, paint);

            switch (mState) {
                case none:

                    break;
                case hint:
                    paint.setColor(color_hint);
                    canvas.drawText(content, x_, y_ + textSize / 2, paint);
                    break;
                case text:
                    paint.setColor(color_text);
                    canvas.drawText(content, x_, y_ + textSize / 2, paint);
                    break;
                case image:
                    canvas.drawBitmap(bitmap, x_ - w / 2, y_ - h / 2, paint);
                    break;

            }

            canvas.restore();


        }

        @Override
        public void reset() {

        }
    }


    /** 单个item的数据类 */
    public class SingleData {

        public int date;

        public boolean isHint;

        public boolean isBitmap;

        public int week;

    }

}
