package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;

import com.hello.app.Base.BaseToolView;
import com.hello.app.R;

import java.text.DecimalFormat;

/**
 * Created by dong on 2014/9/10.
 */
public class SmileView extends BaseToolView {

    /** 灰色大圈 */
    private static int color_gray = Color.argb(255, 132, 132, 132);

    private static int color_text_gray = Color.argb(255, 181, 181, 183);

    private static int color_orange = Color.argb(255, 245, 157, 1);

    private static int color_yello = Color.argb(255, 254, 236, 30);

    /** 渐变颜色数组：绿-黄-白 */
    int[] colors = new int[]{color_orange, color_yello, color_orange};

    private static int color_blue_light = Color.argb(255, 70, 187, 204);

    private static int color_blue_dark = Color.argb(255, 2, 158, 149);

    /** 渐变颜色数组：淡蓝-深蓝 */
    int[] colors_blue = new int[]{color_blue_light, color_blue_dark, color_blue_light};

    private static int color_while = Color.argb(255, 255, 255, 255);

    /** 外面大圈半径 */
    private float r_big;

    /** 外面大圈的宽度 */
    private int width;

    /** 外面大圈的rect */
    private RectF mRect_big;

    /** 成功图标 */
    private float r_suc;

    /** 是否有数据 */
    private boolean isData = false;

    /** 是否盖章 */
    private boolean isSuccess = false;

    /** hour */
    private int hour = 12;

    /** min */
    private int min = 50;

    /** 字体size */
    private boolean isThree = false;

    private int textSize;

    private int textSizeData;

    private int textSizeThree;

    private int textSmallData;

    private int textOffset;

    private Bitmap bitmapSuccess;

    private DataType mDataType;

    public SmileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        bitmapSuccess = BitmapFactory
                .decodeResource(context.getResources(), R.drawable.public_avatar_default_dp_50);

    }

    @Override
    public void initAttrs(AttributeSet attr) {

    }

    @Override
    public void init() {
        Log.i(" initData()", x_L + "");
        r_big = x_L > y_L ? y_L * 4 / 5 : x_L * 4 / 5;//大圈半径
        r_suc = r_big / 2;
        width = (int) (r_big / 16);//大圈宽度
        textSize = (int) (r_big / 3);
        textSizeData = (int) (r_big * 13 / 23);
        textSizeThree = (int) (r_big * 10 / 23);
        textSmallData = textSizeData * 6 / 13;
        textOffset = textSizeData / 8;
    }

    @Override
    public void drawAll(Canvas canvas) {
        drawGrayCircle(canvas);
        drawInText(canvas);
        drawOrangeCircle(canvas);
        showSuccess(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 画外面灰色大圈 */
    private void drawGrayCircle(Canvas canvas) {
        setPaintStyle(Paint.Style.STROKE, false, width, color_gray);

        mRect_big = new RectF(x_L - r_big, y_L - r_big, x_L + r_big, y_L + r_big);
        canvas.save();
        canvas.drawArc(mRect_big, 0, 360, false, mPaint);
        canvas.restore();
    }

    /** 画橙色填充大圈 */
    private void drawOrangeCircle(Canvas canvas) {
        setPaintStyle(Paint.Style.STROKE, true, width, color_blue_light);

        //角度渐变-需要配合canvas旋转
        Shader shader_big = new SweepGradient(0, 0, colors_blue, null);

        mPaint.setShader(shader_big);

        canvas.save();
        canvas.translate(x_L, y_L);
        canvas.rotate(-90);
        mRect_big = new RectF(-r_big, -r_big, r_big, r_big);
        canvas.drawArc(mRect_big, 0, 360, false, mPaint);
        canvas.restore();

        mPaint.setShader(null);
    }

    /** 绘制中心文字 */
    private void drawInText(Canvas canvas) {

        setPaintStyle(Paint.Style.FILL, true, width, color_while);

        switch (mDataType) {

            case xx_Min:
                drawDataMin(canvas);
                break;
            case xx_H_xx_Min:
                drawData(canvas);
                break;
            case x_H_xx_Min:
                drawData_(canvas);
                break;
            case xxx_H_xx_Min:
                drawThreeData(canvas);
                break;

        }


    }

    /** 当hour为数据时候进行显示 */
    private void drawDataMin(Canvas canvas) {

        float x_off;
        float y_off = textSizeData / 3;

        canvas.save();
        mPaint.setColor(color_while);

        x_off = textSizeData * 4 / 5;
        mPaint.setTextSize(textSizeData);
        canvas.drawText(parseInt(min) + "", x_L - x_off, y_L + y_off, mPaint);

        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("Min", x_L + textSizeData / 5 + textOffset, y_L + y_off, mPaint);

        canvas.restore();


    }

    /** 当hour有2位数据时候进行显示 */
    private void drawData(Canvas canvas) {

        float x_off;
        float y_off = textSizeData / 3;

        canvas.save();
        mPaint.setColor(color_while);

        x_off = textSizeData + textSmallData * 5 / 4 + textOffset;
        mPaint.setTextSize(textSizeData);
        canvas.drawText(parseInt(hour) + "", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSizeData - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("H", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSmallData / 2 - textOffset;
        mPaint.setTextSize(textSizeData);
        mPaint.setColor(color_while);
        canvas.drawText(parseInt(min) + "", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSizeData - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("Min", x_L - x_off, y_L + y_off, mPaint);

        canvas.restore();


    }

    /** 当hour有2位数据时候进行显示 */
    private void drawData_(Canvas canvas) {

        float x_off;
        float y_off = textSizeData / 3;

        canvas.save();
        mPaint.setColor(color_while);

        x_off = textSizeData * 3 / 5 + textSmallData * 5 / 4 + textOffset;
        mPaint.setTextSize(textSizeData);
        canvas.drawText(hour + "", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSizeData / 2 - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("H", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSmallData / 2 - textOffset;
        mPaint.setTextSize(textSizeData);
        mPaint.setColor(color_while);
        canvas.drawText(parseInt(min) + "", x_L - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSizeData - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("Min", x_L - x_off, y_L + y_off, mPaint);

        canvas.restore();


    }

    /** 当hour有3位数据时候进行显示 */
    private void drawThreeData(Canvas canvas) {

        float x_off;
        float y_off = textSizeData / 3;
        float x_ = x_L;

        canvas.save();
        mPaint.setColor(color_while);

        x_off = r_big - width * 2;
        mPaint.setTextSize(textSizeThree);
        canvas.drawText(hour + "", x_ - x_off, y_L + y_off, mPaint);

        x_off = x_off - 3 * textSizeThree / 2 - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("H", x_ - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSmallData / 2 - textOffset;
        mPaint.setTextSize(textSizeThree);
        mPaint.setColor(color_while);
        canvas.drawText(parseInt(min) + "", x_ - x_off, y_L + y_off, mPaint);

        x_off = x_off - textSizeThree - textOffset;
        mPaint.setTextSize(textSmallData);
        mPaint.setColor(color_text_gray);
        canvas.drawText("Min", x_ - x_off, y_L + y_off, mPaint);

        canvas.restore();


    }

    /** 解析数据 */
    private String parseInt(int number) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("00");

        if (number >= 0 && number < 10) {
            return decimalFormat.format(number);
        } else {
            return number + "";
        }

    }

    /** 无数据时进行显示 */
    private void drawNoData(Canvas canvas) {
        mPaint.setTextSize(textSize);
        mPaint.setColor(color_text_gray);
        float x_off = 3 * textSize / 2;
        float y_off = textSize / 2;

        canvas.save();
        canvas.drawText("无数据", x_L - x_off, y_L + y_off, mPaint);
        canvas.restore();

    }

    /** 显示成功标志 */
    private void showSuccess(Canvas canvas) {

        if (isSuccess) {

            int width = (int) (2 * r_suc);
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmapCopy = Bitmap.createScaledBitmap(bitmapSuccess, width, width, false)
                    .copy(config, true);

            float x_offset = r_big - r_suc;
            float y_offset = (float) Math.sqrt(r_big * r_big - x_offset * x_offset)
                    + r_suc;

            canvas.save();
            canvas.drawBitmap(bitmapCopy, x_L - r_big, y_L - y_offset, mPaint);
            canvas.restore();


        }


    }

    /**
     * 设置数据显示
     *
     * @param isData 是否有数据
     * @param hour   小时
     * @param min    分钟
     */
    public void setData(boolean isData, int hour, int min) {

        this.isData = isData;
        this.hour = hour;
        this.min = min;

        if (isData) {
            if (hour >= 100 && hour <= 999) {
                isThree = true;
                mDataType = DataType.xxx_H_xx_Min;
            } else if (hour < 100 && hour >= 10) {
                mDataType = DataType.xx_H_xx_Min;
            } else if (hour < 10 && hour > 0) {
                mDataType = DataType.x_H_xx_Min;
            } else {
                mDataType = DataType.xx_Min;
            }

        }

        invalidate();

    }

    /********************************下面是外部调用方法**************************************/

    /**
     * 设置数据显示
     *
     * @param isSuccess 是否盖章
     */

    public void setSuccess(boolean isSuccess) {

        this.isSuccess = isSuccess;

        invalidate();

    }

    private enum DataType {
        xx_Min,
        xx_H_xx_Min,
        x_H_xx_Min,
        xxx_H_xx_Min;

    }

}
