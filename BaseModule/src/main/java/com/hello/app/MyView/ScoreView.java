package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.hello.app.Base.BaseObject;
import com.hello.app.Base.BaseToolView;
import com.hello.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：自定义的奖牌评分系统
 *
 * @author dong
 * @Time: 2014/11/19
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class ScoreView extends BaseToolView {

    private Bitmap mBitmapOk = null;

    private Bitmap mBitmapOkHalf = null;

    private Bitmap mBitmapNo = null;

    private int score;

    /** 奖牌的尺寸 */
    private float w_s;

    private float h_s;

    private List<Single> mList = new ArrayList<Single>();


    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBitmapOk = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_ok);
        mBitmapNo = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_no);
        mBitmapOkHalf = BitmapFactory
                .decodeResource(mContext.getResources(), R.drawable.icon_ok_half);
        for (int i = 0; i < 5; i++) {
            Single single = new Single();
            mList.add(single);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x_L = w / 2;
        y_L = h / 2;

        init();
    }

    @Override
    public void initAttrs(AttributeSet attr) {

    }


    @Override
    public void init() {

        //尺寸初始化
        w_s = x_L * 7 / 27;
        h_s = x_L * 10 / 27;

        initScore();


    }

    private void initScore() {

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mBitmapOk = Bitmap.createScaledBitmap(mBitmapOk, (int) w_s, (int) h_s, false)
                .copy(config, true);
        mBitmapNo = Bitmap.createScaledBitmap(mBitmapNo, (int) w_s, (int) h_s, false)
                .copy(config, true);
        mBitmapOkHalf = Bitmap.createScaledBitmap(mBitmapOkHalf, (int) w_s, (int) h_s, false)
                .copy(config, true);

        mList.get(0).setX_(0);
        mList.get(0).setY_(y_L * 9 / 10);

        mList.get(1).setX_(x_L * 12 / 27);
        mList.get(1).setY_(y_L * 3 / 10);

        mList.get(2).setX_(x_L - w_s / 2);
        mList.get(2).setY_(0);

        mList.get(3).setX_(x_L + x_L * 8 / 27);
        mList.get(3).setY_(y_L * 3 / 10);

        mList.get(4).setX_(2 * x_L - w_s);
        mList.get(4).setY_(y_L * 9 / 10);

    }


    @Override
    public void drawAll(Canvas canvas) {

        for (Single single : mList) {

            single.onDrawSelf(canvas, mPaint);

        }

    }


    /** 处理分数 */
    private void parserScore() {
        float viewScore;//对应的View的得分
        if (score <= 100 && score >= 0) {
            viewScore = parserScore(score);
        } else if (score < 0) {
            viewScore = 0;
        } else {
            viewScore = 5;
        }
        Log.i("parserScore", viewScore + "分");

        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).score = 0;
            if (i + 1 <= viewScore) {
                mList.get(i).score = 1;
            }
            if (viewScore - i > 0 && viewScore < i + 1) {
                mList.get(i).score = 2;
            }


        }

        invalidate();

    }

    /** 100分以内的分数解析 */
    private float parserScore(int score) {
        int scoreInt = score / 20;
        float scoreFloat = 0f;

        int scoreTemp = score % 20;

        if (scoreTemp > 0 && scoreTemp <= 10) {
            scoreFloat = (float) (scoreInt + 0.5);
        } else if (scoreTemp > 10) {
            scoreFloat = scoreInt + 1;
        } else {
            scoreFloat = scoreInt;
        }

        return scoreFloat;

    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        parserScore();
    }


    /** 单个的奖章的内部类 */
    private class Single extends BaseObject {

        private int score = 0;

        @Override
        public void initData() {

        }

        @Override
        public void init(float x, float y, int color, float radius) {

        }

        @Override
        public void onDrawSelf(Canvas canvas, Paint paint) {

            canvas.save();

            if (score == 0) {
                bitmap = mBitmapNo;
            } else if (score == 1) {
                bitmap = mBitmapOk;
            } else if (score == 2) {
                bitmap = mBitmapOkHalf;
            }
            canvas.drawBitmap(bitmap, x_, y_, paint);

            canvas.restore();


        }

        @Override
        public void reset() {
            score = 0;
        }
    }


}
