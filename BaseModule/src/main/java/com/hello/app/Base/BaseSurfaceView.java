package com.hello.app.Base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by dong on 2014/7/29.
 */
public abstract class BaseSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback, Runnable {


    public Context context;

    public SurfaceHolder holder;

    public Paint mPaint;

    public Canvas canvas;

    /** surface的刷新状态 */
    private boolean isRefresh = true;

    /** 线程控制 */
    private Thread thread;

    private boolean isThread = false;

    /** handler控制 */
    public Handler handler;

    private boolean isHandler = false;


    /** 布局的中心坐标 */
    public float x_L;

    public float y_L;


    /** 设置handler和thread的枚举 */
    public enum RunType {

        HANDLER(0),

        THREAD(1);

        RunType(int value) {
            this.runType = value;
        }

        int runType;

    }

    /** 设置run运行方式 */
    public void setRunType(RunType runType) {

        switch (runType) {

            case HANDLER:
                isHandler = true;
                break;
            case THREAD:
                isThread = true;
                break;
        }

    }


    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        holder = this.getHolder();
        holder.addCallback(this);
        setZOrderOnTop(true); //设置surface在其他windows的上面
        holder.setFormat(PixelFormat.TRANSPARENT);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL); //设置画笔填充
        mPaint.setAntiAlias(true);    //设置画笔抗锯齿
        mPaint.setSubpixelText(true);

        initAttrs(attrs);

    }


    /** 启动线程 */
    public void startThread() {

        if (thread == null) {
            thread = new Thread(this);
            thread.setDaemon(true);
            thread.start();

        }
        if (!thread.isAlive()) {
            thread.start();
        }


    }

    /** 启动handler */
    public void startHandler(boolean isHandler) {
        if (!isHandler) {
            return;
        }

        if (handler == null) {
            handler = new Handler();
        }

        handler.post(this);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //获取布局的尺寸中心
        x_L = this.getWidth() / 2;
        y_L = this.getHeight() / 2;
        init();
        setisRefresh(true);
        if (isThread) {

            startThread();
        }
        if (isHandler) {
            startHandler(isHandler);
        }

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        release();

    }


    /** 初始化attrs */
    public abstract void initAttrs(AttributeSet attr);

    /** init数据的初始化 */
    public abstract void init();


    /** 开始绘制 */
    public abstract void drawSelf();


    public void onDraw() {

        try {

            canvas = holder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            drawSelf();


        } catch (Exception e) {

        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }

        }


    }

    @Override
    public void run() {

        while (isRefresh) {
//            Log.i("run", "====开始刷新");
            onDraw();

        }
    }

    /** 设置surface的刷新 */
    public void setisRefresh(boolean isRefresh) {
        Log.i("setisRefresh", "====true");
        this.isRefresh = isRefresh;

    }

    /** 设置surface的刷新 */
    public boolean getRefresh() {

        return isRefresh;

    }

    /** 释放资源 */
    public void release() {
        Log.i("release", "====false");
        if (thread.isAlive() && isThread) {
            thread = null;
        }
        if (isHandler && handler != null) {
            handler.post(null);

        }

        isRefresh = false;
//        holder.removeCallback(this);

    }


}
