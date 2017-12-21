package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.hello.app.Base.BaseView;
import com.hello.app.R;
import com.hello.app.Util.BitmapUtil;
import com.hello.app.Util.MathUtil;
import com.hello.app.Util.TimeUtil;

import java.sql.Time;

/**
 * 自定义的时间选择空间
 * Created by dong on 2014/9/29.
 */
public class TimePicker extends BaseView {


    private Context mContext;

    private Bitmap bitmapBg;

    private Bitmap bitmapInBg;

    private Bitmap bitmapIcon;

    private Paint mPaint;

    private Paint paint;  //进度条画笔

    private Canvas mCanvas;

    private int scale;

    private float r_o;  //外表盘刻度的半径

    private float r_in;  //里表盘刻度的半径

    /** picker的进度头 */
    private float x_d = 62;

    private float y_d = 200;

    private float r_d;  //进度条中心半径

    private float w_d = 40; //进度条的宽度

    private float r_p = 33; //进度头的半径

    /** 进度条角度 */
    private float angle = 270;


    /** text的尺寸 */
    private float textSize;

    private String textContent = "00:00:00";

    private int textSecond;


    private OnTimePickListener mPickListener;


    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);

//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
//        int textColor = array.getColor(R.styleable.MyView_textColor, 0XFF00FF00); //提供默认值，放置未指定
//        float textSize = array.getDimension(R.styleable.MyView_textSize, 36);
//        mPaint.setColor(textColor);
//        mPaint.setTextSize(textSize);
//
//        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响

        initBitMap();

    }


    /** 初始化bitMap */
    private void initBitMap() {

        mPaint = new Paint();
        mPaint.setDither(true);//是否抖动
        mPaint.setAntiAlias(true);//是否抗锯齿

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(200, 156, 212, 1));
        paint.setStrokeWidth(w_d);
        paint.setStrokeCap(Paint.Cap.ROUND);

        bitmapBg = BitmapUtil.getBitmapFromID(getContext(), R.drawable.bg_time_pick);

        bitmapInBg = BitmapUtil.getBitmapFromID(getContext(), R.drawable.bg_time_pick_in);
        bitmapIcon = BitmapUtil.getBitmapFromID(getContext(), R.drawable.icon_time_pick);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCanvas = canvas;

        x_L = getWidth() / 2;
        y_L = getHeight() / 2;

        r_d = x_L - 62; //设置进度条中心半径

        drawFace(canvas);

        drawProgress(canvas);

        drawPoint(x_d, y_d, canvas);

        drawText(canvas);


    }


    /** 绘制表盘 */
    private void drawFace(Canvas canvas) {
        canvas.save();
        bitmapBg = Bitmap.createScaledBitmap(bitmapBg, 2 * (int) x_L, 2 * (int) y_L, false);
        canvas.drawBitmap(bitmapBg, 0f, 0f, mPaint);

        bitmapInBg = Bitmap.createScaledBitmap(bitmapInBg, 2 * (int) x_L, 2 * (int) x_L, false);
        canvas.drawBitmap(bitmapInBg, 0f, 0f, mPaint);
        canvas.restore();
    }

    /** 绘制进度条 */
    private void drawProgress(Canvas canvas) {

        RectF rectF = new RectF(x_L - r_d, y_L - r_d, x_L + r_d, x_L + r_d);
        canvas.save();
        canvas.drawArc(rectF, -90, angle, false, paint);
        canvas.restore();

    }


    /** 绘制进度头 */
    private void drawPoint(float x, float y, Canvas canvas) {

        canvas.save();
        canvas.drawBitmap(bitmapIcon, x - r_p, y - r_p, mPaint);
        canvas.restore();
    }

    /** 绘制和总监表盘刻度 */
    private void drawText(Canvas canvas) {

        mPaint.setTextSize(70);

        canvas.save();
        canvas.drawText(textContent, x_L - 140, y_L, mPaint);
        canvas.restore();

    }

    /** 触摸监听 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean isProgress = isProgress(event.getX(), event.getY());
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:  //0

                if (isProgress) {

                    setNewPoint(event.getX(), event.getY());

                    Log.i(TimePicker.class.getSimpleName(), event.getX() + "=" + event.getY());

                }

                break;
            case MotionEvent.ACTION_MOVE:

                setNewPoint(event.getX(), event.getY());
                angle = getAngle(x_d, y_d);

                break;
            case MotionEvent.ACTION_UP:

                angle = getAngle(x_d, y_d);

                break;

        }

        callRefresh(angle);

        return true;
    }


    /** 通知监听 */
    private void callRefresh(float angle) {

        textSecond = (int) (angle * 20);

        textContent = TimeUtil.secToTime(textSecond);

        mPickListener.onTimePick(angle);
        invalidate();

    }


    /** 计算进度头圆圈的坐标 */
    private void setNewPoint(float x, float y) {

        boolean xIsPlus = (x - x_L) > 0 ? true : false;
        boolean yIsPlus = (y - y_L) > 0 ? true : false;

        //求绝对值
        y = Math.abs(y - y_L);
        x = Math.abs(x - x_L);

        float r = (float) Math.sqrt(x * x + y * y);

        //按比例计算
        x_d = x * r_d / r;
        y_d = y * r_d / r;

        //还原正常尺寸
        x_d = xIsPlus ? x_d + x_L : x_L - x_d;
        y_d = yIsPlus ? y_d + y_L : y_L - y_d;


    }


    /** 计算坐标角度 */
    private float getAngle(float x, float y) {

        float angle = 0;

        boolean xIsPlus = (x - x_L) > 0 ? true : false;
        boolean yIsPlus = (y - y_L) > 0 ? true : false;

        x = Math.abs(x - x_L);
        y = Math.abs(y - y_L);

        if (xIsPlus && !yIsPlus) {
            angle = (float) MathUtil.arcTan(x / y);
        }

        if (xIsPlus && yIsPlus) {

            angle = (float) MathUtil.arcTan(y / x) + 90;
        }

        if (!xIsPlus && yIsPlus) {
            angle = (float) MathUtil.arcTan(x / y) + 180;
        }

        if (!xIsPlus && !yIsPlus) {
            angle = (float) MathUtil.arcTan(y / x) + 270;
        }

        return angle;

    }

    /** 进度条坐标判断 和内圈外圈半径判断 */

    private boolean isProgress(float x, float y) {

        float r_insider = r_d - w_d / 2;
        float r_outsider = r_d + w_d / 2;

        //计算坐标点和中心的距离*/

        float r_touch = (float) Math.sqrt((x - x_L) * (x - x_L) + (y - y_L) * (y - y_L));

        if (r_touch >= r_insider && r_touch <= r_outsider) {

            return true;

        } else {
            return false;
        }


    }


    /**
     * 设置时间选择监听
     *
     * @param onTimePickListener 时间选择监听
     */
    public void setOnTimePickListener(OnTimePickListener onTimePickListener) {

        mPickListener = onTimePickListener;


    }

    /** 时间选择监听 */

    public interface OnTimePickListener {


        public void onTimePick(float min);

    }


}
