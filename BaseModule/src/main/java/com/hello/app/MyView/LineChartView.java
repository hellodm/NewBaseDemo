package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.hello.app.Base.BaseToolView;

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
public class LineChartView extends BaseToolView {


    private float time = 0;

    private List<Point> mPointList = new ArrayList<Point>();


    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {
        W = W - 10;
        H = H - 10;
        Point p0 = new Point(20, H);
        Point p1 = new Point(60, (H * 2 / 3));
        Point p2 = new Point(80, (H / 2));
        Point p3 = new Point(120, (H * 1 / 3));
        Point p4 = new Point(W / 2 - 60, (H / 2));
        Point p5 = new Point(W / 2 + 20, (H * 1 / 3));
        Point p6 = new Point(W * 2 / 3, (H / 2));
        Point p7 = new Point(W * 3 / 4, H / 2);
        Point p8 = new Point(W * 4 / 5, (H * 1 / 3));
        Point p00 = new Point(W * 4 / 5, H);
        mPointList.add(p0);
        mPointList.add(p1);
        mPointList.add(p2);
        mPointList.add(p3);
        mPointList.add(p4);
        mPointList.add(p5);
        mPointList.add(p6);
        mPointList.add(p7);
        mPointList.add(p8);
        mPointList.add(p00);
    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawLineShadow(canvas);
        drawLinePath(canvas);
        drawLineCircle(canvas);
        drawLineCircle_(canvas);

        if (time != mPointList.size() - 1) {
            time += 0.1f;
            invalidate();
        }
    }

    /** 绘制折线阴影 */
    private void drawLineShadow(Canvas canvas) {

        int[] colors = new int[]{Color.argb(255, 161, 226, 85), Color.argb(255, 195, 233, 151),
                Color.WHITE};
        Shader shader = new LinearGradient(W / 2, 0, W / 2, H, colors, null,
                Shader.TileMode.CLAMP);

        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        for (int i = 0; i < time; i++) {
            Point point = mPointList.get(i);
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }

            if (i <= time && i + 1 >= time) {
                path.lineTo(point.x, H);
            }
        }

        path.close();
        canvas.drawPath(path, mPaint);
        mPaint.setShader(null);

    }

    /** 绘制折现 */
    private void drawLinePath(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.argb(255, 85, 147, 8));

        for (int i = 0; i < time; i++) {
            Point point = mPointList.get(i);
            if (i != mPointList.size() - 1) {
                Point point1 = mPointList.get(i + 1);
                canvas.drawLine(point.x, point.y, point1.x, point1.y, mPaint);
            }

        }


    }

    /** 绘制折现点 */
    private void drawLineCircle(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.argb(255, 85, 147, 8));
        for (int i = 0; i < time; i++) {
            Point point = mPointList.get(i);
            canvas.drawCircle(point.x, point.y, 5, mPaint);

        }
    }

    /** 绘制折现点 */
    private void drawLineCircle_(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        for (int i = 0; i < time; i++) {
            Point point = mPointList.get(i);
            canvas.drawCircle(point.x, point.y, 5, mPaint);

        }

    }

    class Point {

        float x;

        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

    }


}
