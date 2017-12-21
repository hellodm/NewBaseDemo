package com.hello.app.Util;

import android.content.Context;
import android.os.PowerManager;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：控制系统方法
 *
 * @author dong
 * @Time: 2015/3/9
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class SystemUtil {

    public static final String POWER_SERVICE = Context.POWER_SERVICE;


    /** 唤醒屏幕 */
    public static void wakeScreen(Context context) {

        PowerManager powerManager = getService(context, POWER_SERVICE);
        PowerManager.WakeLock mWeakLock = powerManager
                .newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "SystemUtil");

        if (!powerManager.isScreenOn()) {
            mWeakLock.acquire();
            mWeakLock.release();
        }
    }


    /**
     * 获取相对应的服务
     *
     * @param context     context
     * @param serviceName 对应服务的名字
     */
    private static <T extends Object> T getService(Context context, String serviceName) {

        return (T) context.getSystemService(serviceName);

    }

}
