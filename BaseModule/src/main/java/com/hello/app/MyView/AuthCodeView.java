package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;

import java.util.Random;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述--多种变换模式的验证码
 *
 * @author dong
 * @Time: 2015/2/11
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class AuthCodeView extends BaseToolView {


    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static Random mRandom = new Random();

    private final int scaleColor = 50;

    Line[] mLines = new Line[20];

    Point[] mPoints = new Point[50];

    private int colorBg;

    private AuthStyle mStyle;

    private int mTextSize;

    private CharCode[] mCodes = new CharCode[4];

    private String AUTH_CODE;

    public AuthCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void initAttrs(AttributeSet attr) {
        colorBg = randomColor();//随机产生背景颜色
        randomStyle();//随机产生背景颜色
    }

    @Override
    protected void init() {
        mTextSize = (int) (H / 2);

        //初始化code数组
        CharCode code1 = new CharCode();
        code1.initData(x_L - 5 * mTextSize / 3, y_L + mTextSize / 3, randomChar() + "", mTextSize);
        CharCode code2 = new CharCode();
        code2.initData(x_L - mTextSize / 2, y_L + mTextSize / 3, randomChar() + "", mTextSize);
        CharCode code3 = new CharCode();
        code3.initData(x_L + mTextSize / 3, y_L + mTextSize / 3, randomChar() + "", mTextSize);
        CharCode code4 = new CharCode();
        code4.initData(x_L + 4 * mTextSize / 3, y_L + mTextSize / 3, randomChar() + "", mTextSize);
        mCodes[0] = code1;
        mCodes[1] = code2;
        mCodes[2] = code3;
        mCodes[3] = code4;
    }


    /** 外部控制方法 */
    public void refresh() {
        colorBg = randomColor();//随机产生背景颜色
        randomStyle();//随机产生背景颜色
        AUTH_CODE = "";
        resetCode();
        invalidate();
    }

    /** 重置验证码 */
    private void resetCode() {

        for (CharCode code : mCodes) {
            code.reset();
        }

    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawLayoutBg(canvas);//绘制背景
        drawFrame(canvas);//绘制边框
        drawChar(canvas);//绘制content
        drawMask(canvas);//绘制mask


    }

    /** 绘制描边框 */

    private void drawFrame(Canvas canvas) {
        setPaintStyle(Paint.Style.STROKE, false, 5, Color.GRAY);
        canvas.save();
        canvas.drawRect(0, 0, W, H, mPaint);
        canvas.restore();

    }

    /** 绘制背景 */

    private void drawLayoutBg(Canvas canvas) {

        canvas.save();
        canvas.drawColor(colorBg);
        canvas.restore();

    }


    /** 绘制验证码 */
    private void drawChar(Canvas canvas) {
        for (CharCode charCode : mCodes) {
            charCode.onDrawSelf(canvas, mPaint);
            AUTH_CODE = AUTH_CODE + charCode.text;
        }
    }

    /** 绘制mask */
    private void drawMask(Canvas canvas) {
        switch (mStyle) {
            case point:
                drawPointMask(canvas, 5);
                break;
            case sine:
                drawLinesMask(canvas);
                break;
            case line:
                drawLinesMask(canvas);
                break;
            default:
                break;

        }

    }

    /** 绘制点状mask */

    private void drawPointMask(Canvas canvas, int size) {

        //先随机50个点
        setPaintStyle(Paint.Style.FILL, false, size, Color.GRAY);

        for (int i = 0; i < 50; i++) {
            int x = mRandom.nextInt((int) W);
            int y = mRandom.nextInt((int) H);
            Point p = new Point();
            p.set(x, y);
            mPoints[i] = p;
            canvas.drawPoint(p.x, p.y, mPaint);
        }

    }

    /** 绘制线状mask */

    private void drawLinesMask(Canvas canvas) {
        drawPointMask(canvas, 2);
        //先随机20条线
        setPaintStyle(Paint.Style.FILL, false, 3, Color.GRAY);
        PathEffect effect1 = mRandom.nextBoolean() ? new DiscretePathEffect(3.0f, 5.0f) : null;

        for (int i = 0; i < mLines.length; i++) {

//            Line line = new Line();
//            PointF start = new PointF();
//            start.set(0, mRandom.nextInt((int) H + 50) - 50);
//            PointF end = new PointF();
//            end.set(W, mRandom.nextInt((int) H + 50) - 50);
//            line.start = start;
//            line.end = end;
//            mLines[i] = line;
            mPaint.setColor(randomColor());
            mPaint.setPathEffect(effect1);
            canvas.save();
            canvas.drawLine(0, mRandom.nextInt((int) H + 50) - 50, W,
                    mRandom.nextInt((int) H + 50) - 50, mPaint);
            canvas.restore();
            mPaint.setPathEffect(null);
        }

    }

    /** 从字库里面随机一个char */
    private char randomChar() {

        return CHARS[mRandom.nextInt(28)];

    }

    /** 随机背景颜色 */
    private int randomColor() {

        int red = mRandom.nextInt(255);
        int blue = mRandom.nextInt(255);
        int green = mRandom.nextInt(255);
        return Color.argb(scaleColor, red, green, blue);


    }

    /** 随机验证码样式 */
    private void randomStyle() {

        int style = mRandom.nextInt(2);

        switch (style) {
            case 0:
                mStyle = AuthStyle.point;
                break;
            case 1:
                mStyle = AuthStyle.sine;
                break;
            case 2:
                mStyle = AuthStyle.line;
                break;
            default:
                mStyle = AuthStyle.point;
                break;
        }

    }


    /** 验证码样式枚举 */
    protected enum AuthStyle {

        point,   //点状验证码
        sine,  //带状验证码
        line //条状蒙版验证码

    }


    /** 单个字符（歪斜、颜色、渐变、大小） */
    public class CharCode extends BaseObject {

        public float oblique;

        public int color;

        public int textSize;

        public int alpha;

        public String text;

        public Paint.Style isHollow;

        public float x;

        public float y;


        @Override
        public void initData() {
            oblique = mRandom.nextBoolean() ? 1 : -1;
            oblique = oblique * mRandom.nextInt(25) / 50;
            color = randomColor();
            while (color == colorBg) {
                color = randomColor();
            }

            alpha = mRandom.nextInt(255);
            alpha = 255;
            isHollow = mRandom.nextBoolean() ? Paint.Style.STROKE : Paint.Style.FILL;
        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        public void initData(float x, float y, String text, int textSize) {
            this.x_ = this.x = x;
            this.y_ = y;
            this.text = text;
            int tempSize = mRandom.nextInt(textSize + 5);
            while (tempSize < textSize - 5) {
                tempSize = mRandom.nextInt(textSize + 5);
            }
            this.textSize = tempSize;

            int tempY = mRandom.nextInt((int) (y_ + textSize / 3));
            while (tempY < (int) y_ - textSize / 3) {
                tempY = mRandom.nextInt((int) (y_ + textSize / 3));
            }
            this.y = tempY;

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {
            paint.setColor(color);
            paint.setAlpha(alpha);
            paint.setStyle(isHollow);
            paint.setFakeBoldText(mRandom.nextBoolean());//随机粗体
            paint.setTextSkewX(oblique);
            paint.setTextSize(textSize);
            canvas.save();
            canvas.drawText(text, x, y, paint);
            canvas.restore();
        }

        @Override
        public void reset() {
            initData();
            initData(x_, y_, randomChar() + "", mTextSize);
        }
    }

    /** 线段object */
    public class Line {

        public PointF start;

        public PointF end;

        public int color;

    }


}
