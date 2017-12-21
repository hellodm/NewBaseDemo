package com.hello.app.MyView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.hello.app.Util.Util;

/**
 * Created by dong on 2014/7/12.
 */
public class Ball {


    private View view;

    public float W;

    public float H;

    /**
     * 小球的颜色
     */
    public int color;

    /**
     * 小球的速度
     */
    public float v;

    /**
     * 圆圈半径
     */
    public float radius = 15;

    /**
     * 小球的质量
     */
    public float m = 10;

    /**
     * 重力加速度g
     */
    public float g = 9.8f;

    /**
     * 小球下落的时间
     */
    public float t = 0;

    /**
     * 小球下落的高度
     */
    public float h = g * t * t / 2;

    /**
     * 小球上抛高度
     */
    public float h_up;

    /**
     * 小球势能
     */
    public float Ep = m * g * h;   //mgh=m*v^2/2---gh=v^2/2    h=v^2/2g

    /**
     * 小球的X，y坐标
     */
    public float ball_x;

    public float ball_y;

    /**
     * 画笔对象
     */
    public Paint paint;

    /**
     * 判断小球书否被按下
     */
    public boolean isPress = false;

    /**
     * 小球下落判断
     */
    public boolean isFall = false;

    /**
     * 小球上升判断
     */
    public boolean isRaise = false;


    /**
     * 地平线位置
     */
    public float ground_y;


    public Ball(View view) {
        this.view = view;
        init();
    }

    public void init() {

    }

    public void drawSelf(Canvas canvas, Paint paint) {

        if (ball_y < ground_y - radius && !isPress) {       //离地的节奏

            if (!isRaise) {                                //下落的节奏

                isFall = true;

                ball_y += g * t * t / 2 - h;

                h = g * t * t / 2;

                if (ball_y > ground_y - radius) {
                    ball_y = ground_y - radius;
                }

                Util.logStr("小球上抛", ground_y - radius + "y==" + ball_y + "   下落h=" + h);

            } else {                                       //上升的节奏

                ball_y -= v * t - g * t * t / 2 - h;
                h = v * t - g * t * t / 2;
                Util.logStr("小球上抛", h + "");

                if (h == h_up) {
                    h_up = 0;
                    h = 0;
                    t = 0;
                    isFall = true;
                    isRaise = false;

                }


            }


        }
//        else if (ball_y == ground_y - radius && !isPress) {
//
//
//
//            if(isFall){
//                isFall=false;
//                isRaise=true;
//                Ep = m * g * h;
//                h_up = h;
//                v = (float) Math.sqrt(2 * Ep / m);  //势能转换成速度
//            }
//
//            t = 0;
//            h=0;
//
//        }
        else {

            if (isFall) {
                isFall = false;
                isRaise = true;

                Ep = m * g * h;
                h_up = h;
                v = (float) Math.sqrt(2 * Ep / m);  //势能转换成速度
                h = 0;
                t = 0;
            }

            if (ball_y == ground_y - radius && !isPress && isRaise) {

                ball_y -= v * t - g * t * t / 2 - h;
                h = v * t - g * t * t / 2;
                Util.logStr("小球上抛", h + "");
            }

        }

        canvas.save();
        canvas.drawCircle(ball_x, ball_y, radius, paint);
        canvas.restore();

    }

    /** 判断是都触摸到球 */
    public boolean isTouchBall(float x, float y) {

        if (x >= ball_x - radius && x <= ball_x + radius && y >= ball_y - radius
                && y <= ball_y + radius) {

            return true;
        } else {
            return false;
        }

    }


}
