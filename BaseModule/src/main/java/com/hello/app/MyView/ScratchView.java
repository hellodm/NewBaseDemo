package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hello.app.Base.BaseToolView;
import com.hello.app.R;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述--中奖系统的刮奖view
 *
 * @author dong
 * @Time: 2015/2/6
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class ScratchView extends BaseToolView {

    private static final Xfermode MASK_DST;

    private static int grayBg = Color.argb(255, 190, 191, 190);

    private final float TOUCH_TOLERANCE = 5f; // 填充距离，使线条更自然，柔和,值越小，越柔和。

    Canvas mCanvas;

    private boolean isCanvas = false;

    private Bitmap bitmapBg;

    private Bitmap bitmapTest;

    private Bitmap bitmapTest1;

    private Path mPath; //擦除路径

    /** 记录最后的点 */
    private float mLastX;

    private float mLastY;

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_OUT;
        MASK_DST = new PorterDuffXfermode(localMode);
    }

    public ScratchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        bitmapTest = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        bitmapTest1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.test1);

    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {
        bitmapBg = Bitmap.createBitmap((int) W, (int) H, Bitmap.Config.ARGB_8888);
        bitmapTest = Bitmap.createScaledBitmap(bitmapTest, (int) W * 2 / 3, (int) H * 2 / 3, false);
        bitmapTest1 = Bitmap
                .createScaledBitmap(bitmapTest1, (int) W * 2 / 3, (int) H * 2 / 3, false);

    }

    @Override
    protected void drawAll(Canvas canvas) {
        drawText(canvas);

        drawMask();
        drawTrack();
        canvas.drawBitmap(bitmapBg, 0, 0, null);
    }

    /** 绘制底层文字 */
    private void drawText(Canvas canvas) {
        setPaintStyle(Paint.Style.FILL, true, 3, Color.RED);
        PathEffect effect1 = new CornerPathEffect(10);
        PathEffect effect2 = new DiscretePathEffect(3.0f, 5.0f);
        PathEffect effect = new ComposePathEffect(effect1, effect2);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(100);
        mPaint.setXfermode(null);
        mPaint.setPathEffect(effect);
        canvas.save();
        canvas.drawColor(Color.WHITE);
//        canvas.drawText("再来一瓶", x_L, y_L, mPaint);
        canvas.drawTextOnPath("3597", mPath, 5, 5, mPaint);
        canvas.restore();

        mPaint.setPathEffect(null);
    }

    /** 绘制覆盖图层 */
    private void drawMask() {
        if (mCanvas == null) {
            mCanvas = new Canvas(bitmapBg);
            mCanvas.drawColor(grayBg);

        }


    }


    /** 绘制擦除轨迹 */
    private void drawTrack() {
        setPaintStyle(Paint.Style.STROKE, true, 60, Color.YELLOW);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setXfermode(MASK_DST);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startPath(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                addPath(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                addPath(x, y);
                closePath(x, y);
                invalidate();
                break;

            default:

                break;
        }

        return true;
    }

    private void startPath(float x, float y) {
        if (!mPath.isEmpty()) {
//            mPath.reset();
        }

        mPath.moveTo(x, y);
        mLastX = x;
        mLastY = y;


    }

    /** 叠加路径-手指滑动 */
    private void addPath(float x, float y) {
        float off_x = Math.abs(x - mLastX);
        float off_y = Math.abs(y - mLastY);

        if (mPath.isEmpty()) {
            startPath(x, y);
        } else {

            if (off_x >= TOUCH_TOLERANCE || off_y >= TOUCH_TOLERANCE) {
                mPath.quadTo(mLastX, mLastY, (x + mLastX) / 2, (y + mLastY) / 2);
                mLastX = x;
                mLastY = y;
            }

        }


    }

    /** 关闭路径 */
    private void closePath(float x, float y) {
        mPath.lineTo(x, y);
    }


}
