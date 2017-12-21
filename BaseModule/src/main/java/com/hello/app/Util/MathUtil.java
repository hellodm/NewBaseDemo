package com.hello.app.Util;

/**
 * Created by dong on 2014/7/30.
 */
public class MathUtil {


    /**
     * 反正弦求角度°
     */
    public static double arcSin(double sin) {

        double arcSin = Math.asin(sin);

        return toAngle(arcSin);

    }

    /**
     * 反余弦求角度°
     */
    public static double arcCos(double cos) {

        double arcsCos = Math.asin(cos);

        return toAngle(arcsCos);

    }

    /**
     * 反余弦求角度°
     */
    public static double arcTan(double tan) {

        double arcsTan = Math.atan(tan);

        return toAngle(arcsTan);

    }


    /** double值转换角度° */
    public static double toAngle(double d) {

        return Math.toDegrees(d);
//        return 180 * d / Math.PI;
    }

    /** 角度转换double值(弧度) */
    public static double toDouble(double a) {
        return Math.toRadians(a);
//        return a * Math.PI / 180;
    }


}
