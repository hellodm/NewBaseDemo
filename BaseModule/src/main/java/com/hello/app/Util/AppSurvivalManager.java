package com.hello.app.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：app存活时间管理
 *
 * @author dong
 * @Time: 2016/9/14
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class AppSurvivalManager {

    private static final String PERIOD = "period";

    private static HandlerThread mThread;

    private static MyHandler mHandler;

    private static int time;

    static {
        mThread = new HandlerThread("AppSurvivalManager");
        mThread.start();
        mHandler = new MyHandler(mThread.getLooper());
    }

    /**
     * 安排任务
     */
    public static void scheduleTask(long period) {
        sendMessage(period);
    }

    public static void resumeTask() {
        mThread.interrupt();
    }

    private static void sendMessage(long period) {
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putLong(PERIOD, period);
        msg.what = 1;
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private static class MyHandler extends Handler implements Runnable {

        private long period = 3000;

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && msg.getData() != null) {
                period = msg.getData().getLong(PERIOD);
                postDelayed(this, period);
            } else {
                postDelayed(this, period);
            }


        }

        @Override
        public void run() {
            sendMessage(mHandler.obtainMessage());

        }
    }


}
