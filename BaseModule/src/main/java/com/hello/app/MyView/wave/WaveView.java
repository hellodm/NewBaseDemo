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
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/25
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 * //y = A*sin(w*x+b)+h 正弦波方程：w影响周期，A影响振幅，h影响y位置，b为初相；
 */
public class WaveView extends BaseView {


    //数据
    private List<PointF> mPoints;

    private List<PointF> mDPoints;

    private List<PointF> mBPoints;

    private List<Strip> mStrips;

    private List<Float> mys;  //y轴值集合

    private List<Float> mxs;  //x轴值集合

    //工具实例
    private Path mPath;

    private Path mPathD;

    private Path mPathB;

    private ValueAnimator mAnimator;

    //属性
    private int time;

    private float w = (float) Math.PI / 180; //默认角速度 1°

    private float speed = w; //角速度

    private float a = 50; //默认振幅

    private float A = a; //动态振幅a

    private float b = (float) (Math.PI / 2); // 初相

    private float B = 0; // 初相

    private float h; // y轴偏移量

    private float singleWave = (float) (Math.PI * 2 / w); //一个波的长度λ

    private float waveNumber = 3; //屏幕显示波的数量

    private float singleStrip; //一个条的间隔

    private float stripNumber = 25; //屏幕显示条的数量

    private float x_all; //x轴显示宽度

    private float around; //前后端拉线宽


    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPoints = new ArrayList<>();
        mBPoints = new ArrayList<>();
        mDPoints = new ArrayList<>();
        mStrips = new ArrayList<>();
        mys = new ArrayList<>();
        mxs = new ArrayList<>();
        mPath = new Path();
        mPathB = new Path();
        mPathD = new Path();
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
                calSinPoint(mPoints, b);
                calSinPoint(mDPoints, b + (float) (Math.PI));
                calStripPoint(mPoints, mDPoints);
                createSinPath(mPath, mPoints);
                createSinPath(mPathD, mDPoints);
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
        singleStrip = (x_all) / stripNumber;
        w = λToω(singleWave);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSin(canvas);
    }

    Path path = new Path();


    //绘制一个正弦波
    private void drawSin(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        for (Strip s : mStrips) {
            s.onDrawSelf(canvas, mPaint);
        }

//        mPaint.setColor(Color.WHITE);
//        mPaint.setStrokeWidth(3);
//        mPaint.setStyle(Paint.Style.STROKE); //设置画笔填充
//        canvas.save();
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPath(mPathD, mPaint);
//        canvas.restore();

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
    private void createSinPath(Path path, List<PointF> points) {
        path.reset();
        path.moveTo(points.get(0).x, mDPoints.get(0).y);
        int size = points.size();
//        for (int i = 1; i < size-1; i+=2) {
//            path.quadTo(points.get(i ).x, points.get(i).y, points.get(i+1).x,
//                    points.get(i+1).y);
//        }
        for (int i = 1; i < size; i++) {
            path.lineTo(points.get(i).x, points.get(i).y);
        }
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
    private void calSinPoint(List<PointF> points, float bb) {
        a = 0;
        points.clear();
        for (float i = around; i < x_all; i += 1) {
            PointF p = new PointF();
            float y = 0;
            if (i < (x_all - around) / 2 + around) {
                y = goSin(i, w, a += 0.1f, bb, h);
            } else {
                y = goSin(i, w, a -= 0.1f, bb, h);
            }

            p.set(i, y);
            points.add(p);
        }


    }

    //计算波形点坐标
    private void calStripPoint(List<PointF> p1, List<PointF> p2) {
        int number = (int) (p1.size() / stripNumber);
        mStrips.clear();
        for (int i = 0; i < stripNumber; i++) {
            Strip strip = new Strip(singleStrip / 3);
            strip.init(0, 0, Color.GREEN, singleStrip / 6);
            strip.setLine(p1.get(i * number), p2.get(i * number));
            mStrips.add(strip);
        }


    }


    //计算波形点坐标
    private void calSinPoints() {
        mPoints.clear();
        for (float i = around; i < x_all; i += 1) {
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
