package com.hello.app.Activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.gotye.api.Gotye;
import com.gotye.api.GotyeAPI;
import com.hello.app.Base.UnHandler;

import java.io.File;
import java.util.Properties;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/10/30
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyApplication extends Application {


    public String userName = "Mozie";

    public static final String PATH_ERROR_LOG = File.separator + "data" + File.separator + "data"
            + File.separator + "Broadcast" + File.separator + "files" + File.separator
            + "error.log";


    /** 异常处理类。 */

//    private UEHandler ueHandler;
    @Override
    public void onCreate() {

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(getApplicationContext());

        // 设置异常处理实例
//        Thread.setDefaultUncaughtExceptionHandler(new UnHandler(this));

        //初始化API，放在application中，保证第一次就启动
//        Properties properties = new Properties();
//        properties.put(Gotye.PRO_APP_KEY, "d3644d5f-c3f9-429d-ace2-6725ae2e8628");
//        Gotye.getInstance().init(this, properties);
//
//        //创建用户api，api实例是对同一个username单例，不会重复创建新的实例
//        GotyeAPI api = Gotye.getInstance().makeGotyeAPIForUser(userName);

    }


}
