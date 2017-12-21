package com.hello.app.MyView.wave;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.hello.app.Base.BaseView;

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
public class WaveMiView extends BaseView implements WaveCallback {

    private LoadingBall mBall; //loading

    private Wave mWave; //波形

    private ValueAnimator mAnimator; //动画控制器

    private int latVolume; //音量

    private int mTime; //动画时间

    public WaveMiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBall = new LoadingBall();
        mWave = new Wave();

        mAnimator = ValueAnimator.ofFloat(0, 1000);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mTime++;
                mWave.calPath(mTime);
                mBall.setSweepTime(mTime);
                invalidate();
            }
        });

    }


    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        mWave.init(W, H);
        mBall.init(x_L, y_L, 0, 70);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mWave.onDrawSelf(canvas, mPaint); //绘制波形
        drawLoading(canvas); //绘制 loading圈
    }

    private void drawLoading(Canvas canvas) {
        mBall.onDrawSelf(canvas, mPaint);
    }

    //开始波浪
    @Override
    public void startWave() {
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        } else {
            if (mAnimator.isStarted()) {
                mAnimator.cancel();
                mAnimator.start();
            } else {
                mAnimator.start();
            }


        }
    }

    @Override
    public void onVolume(int volume, byte[] data) {
        if (Math.abs(volume - latVolume) > 1) {
            latVolume = volume = latVolume + (volume - latVolume) / 5;
        }
        mWave.setAmplitude(volume * 0.03F);

//        short[] shorts = new short[audio.length / 2];
//        ByteBuffer.wrap(audio).order(ByteOrder.nativeOrder())
//                .asShortBuffer()
//                .get(shorts);
//        updateAudioData(volume,shorts);
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void completeWave() {

    }


    //跟新声音数据
    public void updateAudioData(int volume, short[] buffer) {
        if (buffer == null || buffer.length < 1) {
            return;
        }

        float sum = 0;
        for (short rawSample : buffer) {
            sum += rawSample * rawSample * 0.0000305 * 0.0000305;// 0.0000305 = 1/32768
        }
        final float rms = (float) Math.sqrt(Math.sqrt(Math.sqrt(sum / buffer.length)));
        //改变振幅
        mWave.setAmplitude(rms);
        Log.d("WaveMiView", "rms=" + rms + "volume=" + volume);
    }


}
