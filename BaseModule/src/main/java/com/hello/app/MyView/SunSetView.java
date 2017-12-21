package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.hello.app.Base.BaseToolView;
import com.hello.app.Util.MathUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/12/10
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class SunSetView extends BaseToolView {


    /**
     * 水平线的起始点和结束点
     */
    private Point p_s;

    private Point p_e;

    private Point p_r; //日落圆弧的圆心

    private float r_s;//日落圆弧的半径

    private float angle = 0;//动态的扫过角度

    private float angleALL = 30; //初始化

    private RectF mRectF;

    private float oval_a;

    private float oval_b;

    private Timer mTimer;

    private int color_gray = Color.argb(255, 149, 173, 180);

    private int color_bac = Color.argb(255, 109, 140, 150);


    public SunSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }


    /**
     * 初始化各个数据
     */
    @Override
    protected void init() {
        p_s = new Point();
        Log.i("initData", "初始化各个数据");
        p_e = new Point();
        p_s.x = 0;
        p_s.y = (int) H;
        p_e.x = (int) W;
        p_e.y = (int) H;

        //初始化椭圆的a，b
        oval_a = p_e.x - x_L;
        oval_b = p_e.y - H / 2;

        //初始化圆的圆心-扫过弧度angleAll
        p_r = new Point();
        r_s = (float) (x_L / Math.sin(MathUtil.toDouble(angleALL / 2)));
        p_r.x = (int) x_L;
        p_r.y = p_e.y + (int) Math.sqrt(r_s * r_s - x_L * x_L);
        mRectF = new RectF(x_L - r_s, p_r.y - r_s, x_L + r_s, p_r.y + r_s);


    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawCircle(canvas);
        drawTriangle(canvas);
        drawBack(canvas);
        drawCircleAll(canvas);
        if (angleALL <= 120) {
            angle += 1;
            if (angle <= 100) {
                angleALL += 1;
            }
            reInit(angleALL);
            invalidate();
        }

    }

    /** 重新计算角度半径 */
    private void reInit(float angleALL) {

        //初始化圆的圆心-扫过弧度angleAll
        r_s = (float) (x_L / Math.sin(MathUtil.toDouble(angleALL / 2)));
        p_r.x = (int) x_L;
        p_r.y = p_e.y + (int) Math.sqrt(r_s * r_s - x_L * x_L);
        mRectF = new RectF(x_L - r_s, p_r.y - r_s, x_L + r_s, p_r.y + r_s);

    }

    /** 计时器 */
    private void goTime() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

            }
        };

        mTimer.schedule(task, 200, 120);

    }

    /** 绘制基本线 */
    private void drawBack(Canvas canvas) {
        Log.i("drawBack", "绘制基本线");
        setPaintStyle(Paint.Style.FILL, false, 3, Color.WHITE);

        canvas.save();
        canvas.drawLine(p_s.x, p_s.y, p_e.x, p_e.y, mPaint);
        canvas.restore();

    }

    /** 绘制圆弧日落 */

    private void drawCircle(Canvas canvas) {
        Log.i("drawArc", "绘制日落弧线");
        setPaintStyle(Paint.Style.FILL, false, 3, color_gray);

        canvas.save();
        canvas.drawArc(mRectF, 270 - angleALL / 2, angle, true, mPaint);
        canvas.restore();

    }

    /** 绘制填补的三角形 */
    private void drawTriangle(Canvas canvas) {
        Log.i("drawTriangle", "绘制补齐的三角形");
        setPaintStyle(Paint.Style.FILL, false, 0, color_bac);
        if (angle >= angleALL / 2) {
            mPaint.setColor(color_gray);
        } else {
            mPaint.setColor(color_bac);
        }
        float y_off = (float) (r_s * Math.cos(MathUtil.toDouble(angle - angleALL / 2)));
        float x_off = (float) (r_s * Math.sin(MathUtil.toDouble(angle - angleALL / 2)));
        // 绘制多边形
        Path path1 = new Path();
        path1.moveTo(p_r.x, p_r.y);
        path1.lineTo(p_r.x + x_off, p_r.y - y_off);
        path1.lineTo(p_r.x + x_off, p_r.y);
        // 使这些点构成封闭的多边形
        path1.close();
        canvas.save();
        canvas.drawPath(path1, mPaint);
        canvas.restore();

        drawSun(canvas, p_r.x + x_off, p_r.y - y_off);
        drawSunCircle(canvas, p_r.x + x_off, p_r.y - y_off);
    }

    /** 绘制日落弧度 */
    private void drawCircleAll(Canvas canvas) {

        setPaintStyle(Paint.Style.STROKE, false, 1, Color.WHITE);

        canvas.save();
        canvas.drawArc(mRectF, 270 - angleALL / 2, angleALL, true, mPaint);
        canvas.restore();


    }

    /** 绘制太阳 */
    private void drawSun(Canvas canvas, float s_x, float s_y) {

        setPaintStyle(Paint.Style.FILL, false, 0, Color.WHITE);

        canvas.save();
        canvas.drawCircle(s_x, s_y, 7, mPaint);
        canvas.restore();


    }

    /** 绘制太阳外圈 */
    private void drawSunCircle(Canvas canvas, float s_x, float s_y) {

        setPaintStyle(Paint.Style.STROKE, false, 1, Color.WHITE);

        canvas.save();
        canvas.drawCircle(s_x, s_y, 20, mPaint);
        canvas.restore();


    }


    /** resetView */
    public void reset() {
        angle = 0;
        angleALL = 30;
        invalidate();

    }

//    /** 绘制日落弧线--椭圆 */
//    private void drawArc(Canvas canvas) {
//        Log.i("drawArc", "绘制日落弧线");
//        setPaintStyle(Paint.Style.FILL, false, 3, Color.GREEN);
//
//        RectF rectF = new RectF(p_s.x, H / 2, p_e.x, H * 5 / 6);
////        canvas.save();
////        canvas.drawOval(rectF, mPaint);
////        canvas.restore();
//
//        canvas.save();
//        canvas.drawArc(rectF, 180, angle, true, mPaint);
//        canvas.restore();
//
//    }
//
//
//    /** 绘制补齐的三角形-椭圆 */
//    private void drawTriangle(Canvas canvas) {
//        Log.i("drawTriangle", "绘制补齐的三角形");
//        double sq_a = oval_a * oval_a;
//        double sq_b = oval_b * oval_b;
//        double tan = Math.tan(MathUtil.toDouble(180 - angle));
//        double sq_t = tan * tan;
//        double sq_x = sq_a * sq_b / (sq_b + sq_a * sq_t);
//        float x_off = (float) Math.sqrt(sq_x);
//        float y_off = (float) (x_off * tan);
//
//        // 绘制多边形
//        Path path1 = new Path();
//        path1.moveTo(x_L, p_e.y);
//        path1.lineTo(x_L + x_off, p_e.y - y_off);
//        path1.lineTo(x_L + x_off, p_e.y);
//        // 使这些点构成封闭的多边形
//        path1.close();
//        mPaint.setColor(Color.GRAY);
//        canvas.drawPath(path1, mPaint);
//
//    }


}
