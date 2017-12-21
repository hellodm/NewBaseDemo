package com.hello.app.Util;

import android.util.Log;

/**
 * 基本的工具类
 * Created by dong on 2014/7/11.
 */
public class Util {


    /**
     * Log的开关
     */
    public static boolean isLog = true;


    /**
     * log 全部自由选择
     */
    public static void logStr(String type, String Tag, String str) {

        if (isLog) {

        }


    }

    /**
     * log i
     */
    public static void logStr(String Tag, String str) {

        if (isLog) {

            Log.i(Tag, str);
        }


    }


}
