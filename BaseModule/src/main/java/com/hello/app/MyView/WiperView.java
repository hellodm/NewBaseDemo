package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;
import com.hello.app.R;


/**
 * Created by dong on 2014/9/27.
 */
public class WiperView extends BaseToolView {

    private static Bitmap mBitmap;

    int color_gray = Color.argb(255, 204, 204, 204);

    private Wiper mWiper;

    private int time;

    private boolean isCW = true;//顺时针

    public WiperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loading_wiper);
        mWiper = new Wiper();
    }

    @Override
    protected void initAttrs(AttributeSet attr) {

    }

    @Override
    protected void init() {
        mWiper.init(x_L, y_L, 0, 0);
        mWiper.setBitmap(mBitmap);
        mWiper.sweep = time;

    }

    @Override
    protected void drawAll(Canvas canvas) {
        if (time == 0) {
            isCW = true;
        }
        if (time == 180) {
            isCW = false;
        }
        if (isCW) {
            time += 4;

        } else {
            time -= 4;
        }

        drawArc(canvas);
        drawArcGray(canvas);
        mWiper.sweep = time;
        mWiper.onDrawSelf(canvas, mPaint);

        invalidate();

    }

    /** 绘制扇形 */
    private void drawArc(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        RectF rectF = new RectF(x_L - mBitmap.getWidth() / 2, y_L - mBitmap.getWidth() / 2,
                x_L + mBitmap.getWidth() / 2, y_L + mBitmap.getWidth() / 2);
        canvas.save();
        canvas.drawArc(rectF, 180, time, true, mPaint);
        canvas.restore();
    }

    /** 绘制扇形 -灰色 */
    private void drawArcGray(Canvas canvas) {
        mPaint.setColor(color_gray);
        RectF rectF = new RectF(x_L - mBitmap.getWidth() / 9, y_L - mBitmap.getWidth() / 9,
                x_L + mBitmap.getWidth() / 9, y_L + mBitmap.getWidth() / 9);
        canvas.save();
        canvas.drawArc(rectF, 180, time, true, mPaint);
        canvas.restore();

    }


    /** 单个的奖章的内部类 */
    private class Wiper extends BaseObject {

        public int sweep;


        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {
            this.x_ = x;
            this.y_ = y;
            this.radius = radius;
        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            canvas.save();
            canvas.translate(x_, y_);
            canvas.rotate(sweep);
            canvas.drawBitmap(bitmap, 0 - bitmap.getWidth() / 2, 0 - bitmap.getHeight() / 2,
                    paint);
            canvas.restore();


        }

        @Override
        public void reset() {

            this.x_ = 0;
            this.y_ = 0;
            this.radius = 0;
            if (this.bitmap != null) {
                this.bitmap.recycle();
                this.bitmap = null;
            }

        }
    }

}
