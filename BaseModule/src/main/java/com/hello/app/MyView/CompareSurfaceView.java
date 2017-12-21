package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseThreadSurfaceView;
import com.hello.app.Util.MathUtil;

/**
 * 比一比view
 * Created by dong on 2014/9/5.
 */
public class CompareSurfaceView extends BaseThreadSurfaceView {

    /** 外面大圈半径 */
    private float r_big;

    /** 外面大圈的宽度 */
    private int width;

    /** 外面大圈的rect */
    private RectF mRect_big;

    /** 初始化41个刻度 */
    private Line mLine;

    private double angular;  //角度

    private float radius_line; //刻度半径

    private int width_line; //刻度线宽度

    private int number = 40;//刻度条数

    /** 绘制内部内容 -------------------------------------*/

    /** 刻度值 */
    private int value;


    /** 刻度flag */
    private float flag_v;

    /** 总刻度值 */
    private int value_all = 300;

    /** 内部显示总值 */
    int value_int = 110;

    /** 内部显示变动值 */
    int flag_int;

    /** 数字字体尺寸 */
    int textSize;

    /** 单位尺寸 */
    int unitSize;

    /** 汉字尺寸 */
    int ChineseSize;

    /** 单个int字体偏移量 */
    int textOffset;
    /** 绘制内部内容 -------------------------------------*/


    /** 灰色大圈 */
    int color_gray = Color.argb(255, 132, 132, 132);

    /** 渐变颜色数组：绿-黄-白 */
    int[] colors = new int[]{Color.argb(250, 95, 191, 0), Color.argb(250, 255, 255, 0),
            Color.argb(250, 255, 255, 255)};

    /** 渐变颜色数组间隔 */
    float[] position = new float[]{0.0f, 0.3f, 1.0f};


    public CompareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void initAttrs(AttributeSet attr) {
    }

    /** 初始化页面刻度 */
    public void resetView() {

        flag_v = 0;
        flag_int = 0;

    }

    /**
     * 设置速度值
     *
     * @param value_int 速度值-单位KM/H
     */
    public void setValue(int value_int) {

        this.value_int = value_int;
        value = value_int * number / value_all;


    }

    /** 按比例初始化布局参数 */
    @Override
    public void init() {

        r_big = x_L > y_L ? y_L * 3 / 4 : x_L * 3 / 4;//大圈半径
        width = (int) (r_big / 3);//大圈宽度
        radius_line = r_big + width / 2;//刻度线半径
        width_line = (int) (r_big / 27);//刻度线宽度]

        textSize = (int) (r_big * 5 / 9);//设置内部数字字体
        unitSize = textSize / 3; //设置内部单位字体的大小
        ChineseSize = textSize / 4;//汉字大小
        textOffset = textSize / 2;//数字字体偏移

        //设置图标外圈参数
        x_icon = x_L - 2 * ChineseSize;
        y_icon = y_L + r_big - width / 2;
        r_icon = textSize / 5;

        //设置内圈半径
        r_in = r_icon / 4;

        //设置图标指针的参数
        r_offset = 5;
        r_pointer = r_icon + r_offset;
        x_pointer = (float) (x_icon - r_pointer * Math.cos(MathUtil.toDouble(60)));
        y_pointer = (float) (y_icon - r_pointer * Math.sin(MathUtil.toDouble(60)));

        //计算指针扫过的角度
        degree = (float) MathUtil.arcCos(r_in / r_pointer);

        mLine = new Line(0, 0);
    }

    @Override
    public void drawSelf() {

//        drawGrayCircle();
        drawLines(); //绘制刻度
        drawInCircle();//绘制内部灰圈
        drawInText(); //绘制内部速度值
        drawInUnit(); //绘制内部单位
        drawSpeed();//绘制平均速度
        drawIcon();//绘制图标
    }


    /** 画外面灰色大圈 */
    private void drawGrayCircle() {
        setPaintStyle(Paint.Style.STROKE, false, width, color_gray);

        mRect_big = new RectF(r_big / 3, y_L - r_big, x_L + r_big, y_L + r_big);
        canvas.save();
        canvas.drawArc(mRect_big, 135, 270, false, mPaint);
        canvas.restore();
    }


    int colorBg = Color.argb(255, 53, 57, 60);


    /** 另一种绘制刻度的方法 */
    private void drawLines() {
        angular = 91;//初始化角度
        setPaintStyle(Paint.Style.STROKE, false, width_line, color_gray);

        //角度渐变-需要配合canvas旋转
        Shader shader_big = new SweepGradient(0, 0, colors, position);

        canvas.save();
        canvas.translate(x_L, y_L);
        canvas.rotate(135);

        //绘制灰色刻度
        for (int i = 0; i < 41; i++) {

            mLine.setCoordinate(angular, radius_line);
            mLine.onDrawSelf(canvas, mPaint);
            angular = angular + 6.75;
        }

        //绘制渐变刻度
        angular = 91;//初始化角度
        mPaint.setShader(shader_big);
        for (int i = 0; i < flag_v; i++) {

            mLine.setCoordinate(angular, radius_line);
            mLine.onDrawSelf(canvas, mPaint);
            angular = angular + 6.75;


        }

        if (flag_v < value) {
            flag_v += 0.25;
        }

        canvas.restore();
        mPaint.setShader(null);

    }


    /** TODO 画内部灰色覆盖圈 */
    private void drawInCircle() {
        setPaintStyle(Paint.Style.FILL, false, width, colorBg);

        canvas.save();
        canvas.drawCircle(x_L, y_L, r_big - width / 2, mPaint);
        canvas.restore();
    }


    /** TODO 绘制速度值 */
    private void drawInText() {

        if (flag_int < value_int) {
            flag_int += 2;
        }

        if (flag_int < 10) {
            textOffset = textSize / 4;
        }
        if (flag_int < 100 && flag_int >= 10) {
            textOffset = textSize / 2;
        }
        if (flag_int >= 100) {
            textOffset = textSize * 3 / 4;
        }
        CharSequence cs = flag_int + "";

        setPaintStyle(Paint.Style.FILL, false, 2, Color.WHITE);
        mPaint.setTextSize(textSize);
        canvas.save();
        canvas.drawText(cs, 0, cs.length(), x_L - textOffset, y_L, mPaint);
        canvas.restore();

        //中轴线
//        canvas.save();
//        canvas.drawLine(x_L, y_L - r_big, x_L, y_L + r_big, mPaint);
//        canvas.restore();
//        canvas.save();
//        canvas.drawLine(x_L - r_big, y_L, x_L + r_big, y_L, mPaint);
//        canvas.restore();

    }


    /** TODO 绘制单位km/h */

    private void drawInUnit() {

        CharSequence cs = "KM/H";

        setPaintStyle(Paint.Style.FILL, false, 2, Color.WHITE);
        mPaint.setTextSize(unitSize);
        canvas.save();
        canvas.drawText(cs, 0, cs.length(), x_L - unitSize, y_L + 2 * unitSize, mPaint);
        canvas.restore();

    }


    /** TODO 绘制平均速度 */
    private void drawSpeed() {

        CharSequence cs = "平均速度";

        setPaintStyle(Paint.Style.FILL, false, 2, Color.WHITE);
        mPaint.setTextSize(ChineseSize);
        canvas.save();
        canvas.drawText(cs, 0, cs.length(), x_L - ChineseSize, y_L + r_big - width / 2, mPaint);
        canvas.restore();


    }

    /** 速度图标外圈的参数 */
    private float x_icon;

    private float y_icon;

    private float r_icon;

    /** 速度图标指针的参数 */

    private float x_pointer;

    private float y_pointer;

    private float r_pointer;

    private float r_offset;

    /** 指针扫过的角度一半 */
    private float degree;

    /** 内圈半径 */
    private float r_in;

    RectF rect_icon;


    /** TODO 绘制平均里程图标 */
    private void drawIcon() {

        setPaintStyle(Paint.Style.STROKE, true, 3, Color.WHITE);

        //绘制半圆
        rect_icon = new RectF(x_icon - r_icon, y_icon - r_icon, x_icon + r_icon, y_icon + r_icon);
        canvas.save();
        canvas.drawArc(rect_icon, 180, 45, false, mPaint);
        canvas.restore();
        canvas.save();
        canvas.drawArc(rect_icon, 255, 105, false, mPaint);
        canvas.restore();

        //绘制指针
        setPaintStyle(Paint.Style.FILL, true, 3, Color.WHITE);
        rect_icon = new RectF(x_pointer - r_pointer, y_pointer - r_pointer, x_pointer + r_pointer,
                y_pointer + r_pointer);

        canvas.save();
        canvas.drawArc(rect_icon, 60 - degree, 2 * degree, true, mPaint);
        canvas.restore();

        //绘制内圈
        rect_icon = new RectF(x_icon - r_in, y_icon - r_in, x_icon + r_in, y_icon + r_in);
        canvas.save();
        canvas.drawArc(rect_icon, 0, 360, false, mPaint);
        canvas.restore();


    }


    /** 刻度类 */
    public class Line extends BaseObject {


        private double angular;  //角度

        private float x_end; //刻度线的终点位置

        private float y_end;


        protected Line(float x_, float y_) {
            super(x_, y_);
        }


        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        public void setCoordinate(double angular, float radius_line) {
            this.angular = angular;
            this.radius = radius_line;
        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            x_end = (float) (0 + radius_line * Math.cos(Math.PI / 2 - MathUtil.toDouble(angular)));
            y_end = (float) (0 - radius_line * Math.sin(Math.PI / 2 - MathUtil.toDouble(angular)));

            canvas.drawLine(x_, y_, x_end, y_end, mPaint);
        }


        @Override
        public void reset() {

        }
    }

}
