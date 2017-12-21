package com.hello.app.MyView.wave;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.Log;

import com.hello.app.Base.BaseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mozzie on 16-12-19.
 */

public class Wave extends BaseObject {

    //空闲振幅
    private static final float IDLE_AMPLITUDE = 0.05f;

    public int colorS1 = Color.argb(255, 119, 75, 62);

    public int colorE1 = Color.argb(255, 91, 84, 46);

    public int colorS2 = Color.argb(255, 212, 119, 91);

    public int colorE2 = Color.argb(255, 180, 162, 59);

    public int colorContent = Color.argb(15, 226, 169, 113);


    //数据
    private List<PointF> mPoints;

    private List<PointF> mDPoints;

    //工具实例
    private Path mPath;

    private Path mPathD;

    private Path path;

    private Shader mShader1;

    private Shader mShader2;

    //属性
    private float width; //显示宽度

    private float height; //显示高度

    private float y_L; // h中间位

    private int latVolume = 0; //音量

    private int time;

    private float w = (float) Math.PI / 180; //默认角速度 1°

    private float w1; // 波形1的角速度

    private float w2; //波形2的角速度

    private float a = 50; //默认振幅

    private float mAmplitude = 0.5f; //动态振幅

    private float b = (float) (Math.PI / 2); // 初相

    private float h; // y轴偏移量

    private float singleWave = (float) (Math.PI * 2 / w); //一个波的长度λ

    private float waveNumber = 2; //屏幕显示波的数量

    private float strokeWidth = 5;

    @Override
    public void initData() {
        mPoints = new ArrayList<>();
        mDPoints = new ArrayList<>();
        mPath = new Path();
        mPathD = new Path();
        path = new Path();
    }

    @Override
    public void init(float x, float y, int color, float radius) {

    }

    public void init(float width, float height) {
        this.width = width;
        this.height = height;
        h = y_L = height / 2;
        //计算ω
        singleWave = (width) / waveNumber;
        w = λToω(singleWave);
        w1 = w;
        w2 = w * 9 / 10;
        mShader1 = new LinearGradient(0, y_L, width, y_L, new int[]{colorS1, colorE1}, null,
                Shader.TileMode.REPEAT);
        mShader2 = new LinearGradient(0, y_L, width, y_L, new int[]{colorS2, colorE2}, null,
                Shader.TileMode.REPEAT);
    }

    @Override
    public void onDrawSelf(Canvas canvas, Paint paint) {
        //绘制一个正弦波
        paint.setStyle(Paint.Style.FILL); //设置画笔填充
        paint.setShader(null);
        paint.setColor(colorContent);
        canvas.save();
        canvas.drawPath(path, paint);
        canvas.restore();

        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE); //设置画笔填充
        paint.setColor(Color.WHITE);
        paint.setShader(mShader1);
        canvas.save();
        canvas.drawPath(mPathD, paint);
        canvas.restore();
        paint.setShader(mShader2);
        canvas.save();
        canvas.drawPath(mPath, paint);
        canvas.restore();

    }

    @Override
    public void reset() {

    }


    /**
     * 相位变化计算
     */
    public void calPath(int time) {
        b -= w * 15;
        calSinPoint(mPoints, b, w1);
        calSinPoint(mDPoints, b + (float) Math.PI, w2);
        createSinPath(mPath, mPoints);
        createSinPath(mPathD, mDPoints);
        creteAllPath(mPoints, mDPoints);
    }

    /**
     * 设置振幅
     */
    public void setAmplitude(float value) {
        if (value > 1) {
            value = 1f;
        }
        mAmplitude = Math.max(value, IDLE_AMPLITUDE);
    }


    //计算波形点坐标
    private void calSinPoint(List<PointF> points, float bb, float w) {
        a = 0;
        points.clear();
        for (float i = 0; i < width; i += 10) {
            PointF p = new PointF();
            float y = 0;
            if (i < width / 2) {
                y = goSin(i, w, a += mAmplitude * 2, bb, h);
            } else {

                y = goSin(i, w, a -= mAmplitude * 2, bb, h);
            }

            p.set(i, y);
            points.add(p);
        }


    }


    //计算波形点坐标
    private void calSinPoint(List<PointF> p1s, List<PointF> p2s, float b1, float b2, float w1,
            float w2) {
        a = 0;
        p1s.clear();
        p2s.clear();
        for (float i = 0; i < width; i += 10) {
            PointF p1 = new PointF();
            PointF p2 = new PointF();
            float y1 = 0;
            float y2 = 0;
            if (i < width / 2) {
                y1 = goSin(i, w1, a += mAmplitude * 2, b1, h);
                y2 = goSin(i, w2, a += mAmplitude * 2, b2, h);
            } else {
                y1 = goSin(i, w1, a -= mAmplitude * 2, b1, h);
                y2 = goSin(i, w2, a -= mAmplitude * 2, b2, h);
            }
            p1.set(i, y1);
            p2.set(i, y2);
            p1s.add(p1);
            p2s.add(p2);
        }


    }


    //创建波形曲线path
    private void createSinPath(Path path, List<PointF> points) {
        path.reset();
        path.moveTo(points.get(0).x, mDPoints.get(0).y);
        int size = points.size();
        for (int i = 1; i < size - 1; i += 2) {
            path.quadTo(points.get(i).x, points.get(i).y, points.get(i + 1).x,
                    points.get(i + 1).y);
        }

    }

    private void creteAllPath(List<PointF> points1, List<PointF> points2) {
        path.reset();
        path.moveTo(points1.get(0).x, points1.get(0).y);
        for (int i = 1; i < points1.size(); i += 1) {

            PointF p1 = points1.get(i);
            PointF p2 = points2.get(i);

            if (p1.y <= p2.y) {
                path.lineTo(p1.x, p1.y);
            } else {
                path.lineTo(p2.x, p2.y);
            }
        }

        for (int i = points1.size() - 1; i >= 0; i -= 1) {

            PointF p1 = points1.get(i);
            PointF p2 = points2.get(i);

            if (p1.y >= p2.y) {
                path.lineTo(p1.x, p1.y);
            } else {
                path.lineTo(p2.x, p2.y);
            }

        }

    }


    /**
     * 进行一个正弦函数的计算
     *
     * @param x 横轴
     * @param w 角速度
     * @param a 幅度
     * @param b 初相
     * @param h 上下偏移距离
     */
    private float goSin(float x, float w, float a, float b, float h) {
        return (float) (a * Math.sin(w * x + b) + h);
    }

    /**
     * 角速度转波长
     *
     * @param ω 角速度
     * @return 波长
     */
    private float ωToλ(float ω) {
        return (float) (ω / (2 * Math.PI));
    }

    /**
     * 波长转角速度
     *
     * @param λ 波长
     * @return 角速度
     */
    private float λToω(float λ) {
        return (float) (2 * Math.PI / λ);
    }

}
