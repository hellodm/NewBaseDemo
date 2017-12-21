package com.hello.app.MyView.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;

import com.hello.app.Base.BaseObject;
import com.hello.app.Util.ViewUtil;

/**
 * Created by mozzie on 16-12-19.
 */

public class LoadingBall extends BaseObject {

    SweepGradient mGradient;

    private int[] colors = new int[]{Color.TRANSPARENT, Color.argb(200, 180, 162, 59),
            Color.TRANSPARENT};

    private float[] positions = new float[]{0f, 0.25f, 0.6f};

    private float sweepAngle = 360;

    private float startAngle = 0;

    private float mAngle; //动态角度

    private RectF mRectF;

    private float speed = 8f;  //单位时间内转动的角度

    private float strokeWidth;


    @Override
    public void initData() {

    }

    @Override
    public void init(float x, float y, int color, float radius) {
        x_ = x;
        y_ = y;
        this.radius = radius;
        strokeWidth = 5;
//        mRectF = new RectF(x - radius, y - radius, x + radius, y + radius);
        mRectF = new RectF(-radius, -radius, radius, radius);
        mGradient = new SweepGradient(0, 0, colors, positions);
    }

    @Override
    public void onDrawSelf(Canvas canvas, Paint paint) {
        paint.setShader(mGradient);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.save();
        canvas.translate(x_, y_);
        canvas.rotate(mAngle);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, paint);
        canvas.restore();
    }

    @Override
    public void reset() {

    }


    //设置sweep角度
    public void setSweepTime(float time) {
        float offset = time * speed;
        mAngle = startAngle + offset;
    }
}
