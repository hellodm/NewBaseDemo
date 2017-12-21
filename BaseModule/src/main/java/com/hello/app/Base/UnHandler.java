package com.hello.app.Base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hello.app.Activity.MainActivity;
import com.hello.app.Activity.MyApplication;

import java.io.File;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/11/3
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class UnHandler implements Thread.UncaughtExceptionHandler {


    private MyApplication myApp;

    private File fileErrorLog;

    public UnHandler(MyApplication app) {
        myApp = app;
        fileErrorLog = new File(MyApplication.PATH_ERROR_LOG);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        Log.i("UnHandler===================================", "捕获异常");

//        reboot(myApp);

        sendBroadcast(ex.toString());

        android.os.Process.killProcess(android.os.Process.myPid());

    }

    /** 发送广播 */
    private void sendBroadcast(String msg) {

        Intent intent = new Intent();
        intent.setAction("android.myapplication.exception");
        intent.putExtra("msg", "我崩溃啦" + msg);
        myApp.sendBroadcast(intent);

        Log.i("UnHandler===================================", "广播发送");

    }

    /** 重启 */
    private void reboot(Context context) {

        Intent LaunchIntent = context.getPackageManager()
                .getLaunchIntentForPackage("dong.myapplication");
        context.startActivity(LaunchIntent);

//        Intent intent = new Intent(myApp, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//        myApp.startActivity(intent);

    }


}
