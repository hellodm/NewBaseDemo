package com.hello.app.MyView.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseView;

/**
 * Created by mozzie on 16-11-20.
 */

public class KMenuBall extends BaseView implements AnimControl.OnXYCallBack {

    private float rangeCenter;//中心角度

    private float rangeStart; //起始范围角度

    private float rangeEnd;//结束范围角度

    private PointF mainCenter;//main 中心

    private PointF center;//自己圆心

    private PointF tempCenter;//临时圆心

    private float length; //菜单长度

    private AnimControl mAnimControl;

    private float radius = 60;


    public KMenuBall(Context context) {
        super(context, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawSelf(canvas, mPaint);
    }

    public void onDrawSelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.RED);
        canvas.save();
        canvas.drawCircle(0, 0, radius, paint);
        canvas.restore();
    }

    public void reset() {
        radius = 60;
    }


    public void setRange(float rangeStart, float rangeEnd) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangeCenter = rangeStart + (rangeEnd - rangeStart) / 2;
    }

    public void setParams(float x, float y, float rangeStart, float rangeEnd, float length) {
        this.mainCenter = new PointF(x, y);
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.rangeCenter = rangeStart + (rangeEnd - rangeStart) / 2;
        this.length = length;
        calCenter();
    }

    //开启动画弹出
    public void startAnim() {
        mAnimControl.startAnim();
    }

    public void scaleRadius() {
        radius += 0.5f;
        invalidate();
    }


    /*************************************************************************************************/
    public PointF getCenter() {
        return center;
    }

    //设置自己圆心
    public void setCenter(PointF center) {
        this.center = center;
    }


    public PointF getMainCenter() {
        return mainCenter;
    }

    //设置main圆心
    public void setMainCenter(float x, float y) {
        this.mainCenter = new PointF(x, y);

    }

    public float getRangeCenter() {
        return rangeCenter;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeStart(float rangeStart) {
        this.rangeStart = rangeStart;
    }

    public float getRangeStart() {
        return rangeStart;
    }

    private void calCenter() {
        x_L = (float) (length * Math.sin(angleToPi(rangeCenter)) + mainCenter.x);
        y_L = mainCenter.x - (float) (length * Math.cos(angleToPi(rangeCenter)));
        tempCenter = center = new PointF(x_L, y_L);

    }

    private float angleToPi(float radian) {

        return (float) (Math.PI * radian / 180);
    }

    @Override
    public void callBack(float x, float y) {
        tempCenter.set(x, y);
        setTranslationX(x);
        setTranslationY(y);
    }

}
