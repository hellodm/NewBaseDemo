package com.hello.app.Util;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dong on 2014/7/23.
 */
public class TimeUtil {

    public final static String FORM_00 = "yyyy-MM-dd|HH:mm:ss";


    /**
     * 获取当前时间
     *
     * @return long
     */

    public static long obtainCurrentTime() {

        return System.currentTimeMillis();

    }


    /** 秒转00:00:00 */
    public static String secondTo(long time) {

        int hour = 0;
        int min = 0;
        int sec = 0;

        hour = (int) (time / 3600);

        min = (int) (time % 3600 / 60);

        sec = (int) (time % 3600 % 60);

        return numberToStr(hour) + ":" + numberToStr(min) + ":" + numberToStr(sec);
    }


    private static String numberToStr(int time) {
        String str = "00";

        if (time < 10) {
            str = "0" + time;
        }
        if (time >= 10) {

            str = time + "";
        }
        if (time > 99) {
            str = 99 + "";
        }
        return str;

    }


    /**
     * 把一个秒数转换为：00:00:00格式
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }


}
