package com.hello.app.MyView.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.hello.app.Base.BaseObject;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/12/9
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class Strip extends BaseObject {

    public float stroke; //线宽

    public float length;  //线长

    public PointF start;

    public PointF end;


    Strip(float stroke) {
        this.stroke = stroke;
    }

    @Override
    public void initData() {

    }

    @Override
    public void init(float x, float y, int color, float radius) {

    }

    public void setLine(PointF start, PointF end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void onDrawSelf(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(stroke);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.YELLOW);
        canvas.save();
        canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        canvas.restore();


    }

    @Override
    public void reset() {

    }
}
