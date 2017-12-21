package com.hello.app.Util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by dong on 2014/7/10.
 */
public class ViewUtil {

    /** constant常量 */

    public final static int WINDOW_DP = 1;

    public final static int WINDOW_PX = 2;


    /**
     * 获取屏幕的尺寸（W:H）
     * type=WINDOW_DP--返回dp
     * type=WINDOW_PX--返回px
     *
     * @param context activity&#x7684;context
     */
    public static String getWindow(int type, Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float w = metrics.widthPixels; // 屏幕宽度（像素）
        float h = metrics.heightPixels; // 屏幕高度（像素）
        Util.logStr("测试屏幕尺寸", "x=" + w + "y=" + h + "density=" + metrics.density + "densityDpi="
                + metrics.densityDpi);

        if (type == WINDOW_PX) {
            return w + ":" + h;
        } else {
            return px2dip(context, w) + ":" + px2dip(context, h);
        }
    }


    public static ScreenSize getWindowScreen(int type, Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float w = metrics.widthPixels; // 屏幕宽度（像素）
        float h = metrics.heightPixels; // 屏幕高度（像素）
        Util.logStr("测试屏幕尺寸", "x=" + w + "y=" + h + "density=" + metrics.density + "densityDpi="
                + metrics.densityDpi);

        ScreenSize screen;

        if (type == WINDOW_PX) {
            screen = new ScreenSize(w, h, true);
            return screen;
        } else {
            screen = new ScreenSize(px2dip(context, px2dip(context, h)), h, false);
            return screen;
        }
    }

    /**
     * 获取屏幕的尺寸（W:H）
     *
     * @param activity activity&#x7684;context
     */
    public static String getWindow(Activity activity) {

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float w = metric.widthPixels; // 屏幕宽度（像素）
        float h = metric.heightPixels; // 屏幕高度（像v素）

        return w + ":" + h;
    }


    /**
     * dp-->px
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * px-->dp
     */
    public static float px2dip(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return pxValue / scale + 0.5f;

    }


    /**
     * 获取屏幕密度dpi
     */
    public static int getDesc(WindowManager manager) {
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        return densityDpi;


    }

    public static class ScreenSize {

        public float width;

        public float height;

        public boolean isPx;

        ScreenSize(float width, float height, boolean isPx) {
            this.width = width;
            this.height = height;
            this.isPx = isPx;


        }
    }


}
