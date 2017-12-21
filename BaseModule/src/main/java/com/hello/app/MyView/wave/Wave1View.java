package com.hello.app.MyView.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.BounceInterpolator;

import com.hello.app.Base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：水波浪效果
 *
 * @author dong
 * @Time: 2016/11/25
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 * //y = A*sin(w*x+b)+h 正弦波方程：w影响周期，A影响振幅，h影响y位置，b为初相；
 */
public class Wave1View extends BaseView {


    //数据
    private List<PointF> mPoints;

    private List<Float> mys;  //y轴值集合

    private List<Float> mxs;  //x轴值集合

    //工具实例
    private Path mPath;

    private ValueAnimator mAnimator;

    //属性
    private int time;

    private float w = (float) Math.PI / 180; //默认角速度 1°

    private float speed = w; //角速度

    private float a = 10; //默认振幅

    private float A = a; //动态振幅a

    private float b = (float) (Math.PI / 2); // 初相

    private float B = 0; // 初相

    private float h; // y轴偏移量

    private float singleWave = (float) (Math.PI * 2 / w); //一个波的长度λ

    private float waveNumber = 2; //屏幕显示波的数量

    private float x_all; //x轴显示宽度

    private float around; //前后端拉线宽


    public Wave1View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPoints = new ArrayList<>();
        mys = new ArrayList<>();
        mxs = new ArrayList<>();
        mPath = new Path();
        mAnimator = ValueAnimator.ofFloat(0, 100);
        mAnimator.setDuration(50000);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(0);
        mAnimator.setInterpolator(new BounceInterpolator());//弹球效果
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                b -= w * 8;
                calSinPoint(b);
                calSinPoint(b + (float) (Math.PI / 2));
                createSinPath();
//                calSinPoints();
//                createSinPaths();
                invalidate();

            }
        });
    }

    //初始化默认数据
    private void initData() {
        speed = w / 2;
        A = a;
        B = 0;
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        x_all = W;
//        around = x_all / 8;
        around = 0;
        h = y_L;
        //计算ω
        singleWave = (x_all - around) / waveNumber;
        w = λToω(singleWave);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSin(canvas);
    }

    //绘制一个正弦波
    private void drawSin(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE); //设置画笔填充
        mPath.lineTo(x_all, 0);
        mPath.lineTo(0, 0);
        mPath.close();
        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        mPath.reset();
        mPath.moveTo(100, 100);
        mPath.cubicTo(200, 100, 200, 300, 300, 300);
        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    //添加前后端拉线
    public void addAroundLine(Path path) {
    }

    //开始波浪
    public void startWave() {
        if (mAnimator.isRunning()) {
            mAnimator.pause();
        } else {
            if (mAnimator.isPaused()) {
                mAnimator.cancel();
            } else {
                mAnimator.start();
            }


        }
    }

    //创建波形曲线path
    private void createSinPath() {
        mPath.reset();
        mPath.moveTo(0, y_L);
        mPath.lineTo(around / 10, y_L);
//        if(mPoints.get(0).y>y_L){
//
//        }
//        mPath.quadTo(around,y_L,mPoints.get(0).x, mPoints.get(0).y);
        mPath.cubicTo(around / 2, y_L, around / 2, mPoints.get(0).y, mPoints.get(0).x,
                mPoints.get(0).y);
        int size = mPoints.size();
        for (int i = 1; i < size - 1; i += 2) {
            mPath.quadTo(mPoints.get(i).x, mPoints.get(i).y, mPoints.get(i + 1).x,
                    mPoints.get(i + 1).y);
//            mPath.lineTo(mPoints.get(i).x,mPoints.get(i).y);
        }
//        mPath.quadTo(mPoints.get(size-1).x,mPoints.get(size-1).y,around/8,y_L-mPoints.get(size-1).y);
//        mPath.lineTo(W,y_L);
    }

    //创建波形曲线path
    private void createSinPaths() {
        mPath.reset();
        mPath.moveTo(0, y_L);
        mPath.lineTo(around / 10, y_L);
        int size = mys.size();
        for (int i = 1; i < size; i++) {
            mPath.lineTo(mxs.get(i), mys.get(i));
        }
    }

    //计算波形点坐标
    private void calSinPoint(float bb) {
        a = 10;
        mPoints.clear();
        for (float i = around; i < x_all; i += 2) {
            PointF p = new PointF();
            float y = 0;
            if (i < (x_all - around) / 2 + around) {
                y = goSin(i, w, a += 0.2f, bb, h);
            } else {
                y = goSin(i, w, a -= 0.2f, bb, h);
            }

            p.set(i, y);
            mPoints.add(p);
        }


    }

    //计算波形点坐标
    private void calSinPoints() {
        mPoints.clear();
        for (float i = around; i < x_all; i += 2) {
            PointF p = new PointF();
            float y = goSin(i, w, a + 20, b, h);
            p.set(i, y);
            mPoints.add(p);

        }

    }

    //执行一个默认正弦波
    private float goDefaultSin(float x) {
        return goSin(x, w, a, b, h);
    }

    /**
     * 生成一个波长的波
     *
     * @param w 角速度
     * @param a 振幅
     */
    private List<PointF> createPartWave(float w, float a) {
        List<PointF> mPoints = new ArrayList<>();
        for (float i = 0; i < singleWave; i += 2) {
            PointF p = new PointF();
            float y = goSin(i, w, a, b, h);
            p.set(i, y);
            mPoints.add(p);
        }

        return mPoints;

    }

    /**
     * 进行一个正弦函数的计算
     *
     * @param x 横轴
     * @param w 角速度
     * @param a 幅度
     * @param b 初相
     * @param h 上下偏移距离
     */
    private float goSin(float x, float w, float a, float b, float h) {
        return (float) (a * Math.sin(w * x + b) + h);
    }

    /**
     * 角速度转波长
     *
     * @param ω 角速度
     * @return 波长
     */
    private float ωToλ(float ω) {
        return (float) (ω / (2 * Math.PI));
    }

    /**
     * 波长转角速度
     *
     * @param λ 波长
     * @return 角速度
     */
    private float λToω(float λ) {
        return (float) (2 * Math.PI / λ);
    }

}
