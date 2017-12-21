package com.hello.app.Base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by dong on 2014/7/26.
 */
public abstract class BaseObject {


    /** X，y坐标 */
    public float x_;

    public float y_;

    /** 速度 */
    public float speed;

    /** 颜色 */
    public int color;

    public Bitmap bitmap;

    /** 半径 */
    public float radius;


    public Paint paint;

    protected BaseObject() {
        initData();
    }

    protected BaseObject(float x_, float y_) {
        this.x_ = x_;
        this.y_ = y_;
        initData();
    }

    public abstract void initData();

    public abstract void init(float x, float y, int color, float radius);


    public abstract void onDrawSelf(Canvas canvas, Paint paint);


    public void release() {

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }


    }

    public abstract void reset();


    public float getX_() {
        return x_;
    }

    public void setX_(float x_) {
        this.x_ = x_;
    }

    public float getY_() {
        return y_;
    }

    public void setY_(float y_) {
        this.y_ = y_;
    }

    public float getV() {
        return speed;
    }

    public void setV(float v) {
        this.speed = v;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
