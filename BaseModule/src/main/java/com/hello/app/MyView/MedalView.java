package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：自定义的奖牌系统
 *
 * @author dong
 * @Time: 2014/11/19
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MedalView extends BaseToolView {

    private static Bitmap[] mBitmaps = new Bitmap[]{};

    private static Single[] mSingles = new Single[4];


    /** 显示布局的尺寸 */
    private float w_s;

    private float h_s;


    /** 小勋章-little的尺寸 */
    private float w_l;

    private float h_l;

    /** 大勋章-big的尺寸 */
    private float w_b;

    private float h_b;

    private float offset_x;

    private float offset_y;

    private MedalState mState;


    public MedalView(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        W = w;
        H = h;
        x_L = (int) (W / 2);
        y_L = (int) (H / 2);

        w_b = w * 141 / 300;
        h_b = w * 143 / 300;

        w_l = w * 70 / 300;
        h_l = w * 71 / 300;

        offset_x = w * 97 / 300;
        offset_y = w * 108 / 300;

        init();
    }

    @Override
    public void initAttrs(AttributeSet attr) {

    }


    @Override
    public void init() {

        //尺寸初始化
        if (W >= H) {
            w_s = H;
            h_s = H;
        } else {
            w_s = W;
            h_s = W;
        }


    }


    @Override
    protected void drawAll(Canvas canvas) {

        for (Single single : mSingles) {
            if (single != null) {
                single.onDrawSelf(canvas, mPaint);
            }


        }

    }

    /** 设置图像数据 */
    public void setData(Bitmap... bitmaps) {
        reset();
        mBitmaps = bitmaps;

        switch (mBitmaps.length) {

            case 0:
                break;
            case 1:
                mState = MedalState.one;
                Single single = new Single();
                single.x_ = x_L;
                single.y_ = y_L;
                single.radius = w_b / 2;
                single.bitmap = getBitmap(mBitmaps[0], w_b, h_b);
                mSingles[0] = single;
                break;
            case 2:
                mState = MedalState.two;
                Single single1 = new Single();
                Single single2 = new Single();

                single1.init(offset_x, y_L, 0, w_l / 2);
                single2.init(W - offset_x, y_L, 0, w_l / 2);

                single1.bitmap = getBitmap(mBitmaps[0], w_l, h_l);
                single2.bitmap = getBitmap(mBitmaps[1], w_l, h_l);

                mSingles[0] = single1;
                mSingles[1] = single2;

                break;
            case 3:
                mState = MedalState.three;
                Single single_1 = new Single();
                Single single_2 = new Single();
                Single single_3 = new Single();

                single_1.init(x_L, offset_y, 0, w_l / 2);
                single_2.init(offset_x, H - offset_y, 0, w_l / 2);
                single_3.init(W - offset_x, H - offset_y, 0, w_l / 2);

                single_1.bitmap = getBitmap(mBitmaps[0], w_l, h_l);
                single_2.bitmap = getBitmap(mBitmaps[1], w_l, h_l);
                single_3.bitmap = getBitmap(mBitmaps[2], w_l, h_l);

                mSingles[0] = single_1;
                mSingles[1] = single_2;
                mSingles[2] = single_3;
                break;
            case 4:
                mState = MedalState.four;
                Single s1 = new Single();
                Single s2 = new Single();
                Single s3 = new Single();
                Single s4 = new Single();

                s1.init(offset_x, offset_y, 0, w_l / 2);
                s2.init(W - offset_x, offset_y, 0, w_l / 2);
                s3.init(offset_x, H - offset_y, 0, w_l / 2);
                s4.init(W - offset_x, H - offset_y, 0, w_l / 2);

                s1.bitmap = getBitmap(mBitmaps[0], w_l, h_l);
                s2.bitmap = getBitmap(mBitmaps[1], w_l, h_l);
                s3.bitmap = getBitmap(mBitmaps[2], w_l, h_l);
                s4.bitmap = getBitmap(mBitmaps[3], w_l, h_l);

                mSingles[0] = s1;
                mSingles[1] = s2;
                mSingles[2] = s3;
                mSingles[3] = s4;
                break;


        }

        invalidate();
    }


    private void reset() {

        for (int i = 0; i < mSingles.length; i++) {
            if (mSingles[i] != null) {
                mSingles[i].reset();
                mSingles[i] = null;
            }

        }

    }

    /** 缩放bitmap */
    private Bitmap getBitmap(Bitmap srcBitmap, float w, float h) {

        Bitmap bitmap = Bitmap.createScaledBitmap(srcBitmap, (int) w, (int) h, false);

        return bitmap;


    }

    /** 状态 */
    protected enum MedalState {

        one(1), two(2), three(3), four(4);

        final int nativeInt;

        private MedalState(int nativeInt) {
            this.nativeInt = nativeInt;
        }
    }


    /** 单个的奖章的内部类 */
    private class Single extends BaseObject {


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
            canvas.drawBitmap(bitmap, x_ - radius, y_ - radius, paint);
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
