package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.hello.app.Base.BaseHandlerSurfaceView;
import com.hello.app.R;
import com.hello.app.Util.MathUtil;

/**
 * 自定义的VoiceView用handler
 * Created by dong on 2014/7/29.
 */
public class VoiceSurfaceView extends BaseHandlerSurfaceView {


    private float voice_height = 0;

    private float volume = 0;

    private float x_mic;

    private float y_mic;


    public VoiceSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    @Override
    public void startHandler(boolean isHandler) {

        if (!isHandler) {
            return;
        }

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                handler.post(VoiceSurfaceView.this);
            }
        };

        handler.post(this);

    }

    @Override
    public void initAttrs(AttributeSet attr) {
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.VoiceSurfaceView);
        x_mic = array.getInteger(R.styleable.VoiceSurfaceView_mic_size, 100) / 2;
        y_mic = 3 * x_mic;


    }


    @Override
    public void init() {

    }

    @Override
    public void drawSelf() {

        drawMicrophone();


    }


    /** 绘制话筒 */
    public void drawMicrophone() {

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);

        RectF rect_back = new RectF(x_L - x_mic, y_L - y_mic, x_L + x_mic,
                y_L + y_mic);

        canvas.save();
        canvas.drawRoundRect(rect_back, x_mic, x_mic, mPaint);
        canvas.restore();

        //当height<=50的时候绘制
        drawBottom(voice_height);

        //**当50<=height<=250的时候绘制*/
        if (voice_height > x_mic && voice_height <= 5 * x_mic) {

            drawCenter(voice_height);

        }

        //**当height>=250时候绘制*/
        if (voice_height > 5 * x_mic && voice_height <= 6 * x_mic) {
            drawCenter(5 * x_mic);
            drawTop(voice_height);

        }
        if (voice_height > 6 * x_mic) {
            drawCenter(5 * x_mic);
            drawTop(6 * x_mic - 1);

        }


    }

    /**
     * 当height<=50的时候绘制
     */
    private void drawBottom(float voice_height) {

        RectF rect_bottom = new RectF(x_L - x_mic, y_L + x_mic, x_L + x_mic,
                y_L + y_mic);
        mPaint.setStyle(Paint.Style.FILL);

        double sin = (x_mic - voice_height) / x_mic;
        int angle = (int) MathUtil.arcSin(sin);

        canvas.save();
        canvas.drawArc(rect_bottom, angle, 180 - 2 * angle, false, mPaint);
        canvas.restore();


    }

    /**
     * *当50<=height<=250的时候绘制
     */
    private void drawCenter(float voice_height) {

        RectF rect_center = new RectF(x_L - x_mic, y_L + (y_mic - voice_height),
                x_L + x_mic, y_L + 2 * x_mic);

        canvas.save();
        canvas.drawRoundRect(rect_center, 0, 0, mPaint);
        canvas.restore();
    }

    /**
     * 当height>250时候绘制
     */
    private void drawTop(float voice_height) {
        RectF rect_top = new RectF(x_L - x_mic, y_L - y_mic, x_L + x_mic,
                y_L - x_mic);
        double sin_top = (voice_height - 5 * x_mic) / x_mic;
        int angle_top = (int) MathUtil.arcSin(sin_top);

        canvas.save();
        canvas.drawArc(rect_top, 360 - angle_top, 180 + 2 * angle_top, false, mPaint);
        canvas.restore();
    }


    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {

        if (volume != this.volume) {
            this.volume = volume;
            this.voice_height = 2 * y_mic * volume / 33;
            handler.sendEmptyMessage(0);
        }
    }
}
