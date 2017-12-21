package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hello.app.R;


/**
 * Created by dong on 2014/9/27.
 */
public class CircleView extends ImageView {


    private static final Xfermode MASK_DST;

    private static final Xfermode MASK_CIRCLE;

    private Bitmap dstBitmap;

    private Bitmap circleBitmap;

    private Drawable drawable;

    private Paint paintSrc;

    private Paint paintDst;

    private Paint paintCircle;

    /**
     * 边框宽度
     */
    private int width;

    /**
     * 边框颜色
     */
    private int color;

    /**
     * 是否有边框
     */
    private boolean isFrame;

    /**
     * 图像width
     */
    private int w;

    /**
     * 图像width
     */
    private int h;

    private int flag;

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        PorterDuff.Mode circleMode = PorterDuff.Mode.DST_ATOP;
        MASK_DST = new PorterDuffXfermode(localMode);
        MASK_CIRCLE = new PorterDuffXfermode(circleMode);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        width = (int) array.getDimension(R.styleable.CircleView_circleWidth, 0);
        color = array.getColor(R.styleable.CircleView_circleColor, Color.WHITE);
        isFrame = array.getBoolean(R.styleable.CircleView_circleIsFrame, false);
        array.recycle();

    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    public void recycle() {
    }

    @Override
    protected void onDraw(Canvas canvas) {

        w = getWidth();
        h = getHeight();

        drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (paintSrc == null) {
            paintSrc = new Paint();
            paintSrc.setColor(color);
            paintSrc.setFilterBitmap(false);
            paintSrc.setAntiAlias(true);
            paintSrc.setXfermode(MASK_DST);
        }

        flag = canvas.saveLayer(0.0F, 0.0F, w, h, null, 31);

        drawable.setBounds(width, width, w - width, h - width);
        drawable.draw(canvas);

        if ((this.dstBitmap == null) || this.dstBitmap.isRecycled()) {
            dstBitmap = getSrcBitMap();
        }
        canvas.drawBitmap(dstBitmap, 0, 0, paintSrc);

        if (isFrame) { // 假如有边框

            paintSrc.setXfermode(MASK_CIRCLE);
            if ((this.circleBitmap == null) || (this.circleBitmap.isRecycled())) {
                circleBitmap = getBitMap(w, h);
            }
            canvas.drawBitmap(circleBitmap, 0f, 0f, paintSrc);
        }

        if (dstBitmap != null) {
            dstBitmap.recycle();
            dstBitmap = null;
        }
        if (circleBitmap != null) {
            circleBitmap.recycle();
            circleBitmap = null;
        }

        if (!isInEditMode()) {
            canvas.restoreToCount(flag);
        }
    }


    /**
     * 获取Src圆圈
     */
    private Bitmap getSrcBitMap() {

        int w = getWidth() - width;
        int h = getHeight() - width;

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(w, h, srcConfig);
        Canvas srcCanvas = new Canvas(srcBitmap);
        Paint paintSrc = new Paint(1);
        paintSrc.setColor(color);

        RectF srcRectF = new RectF(width, width, w, h);
        srcCanvas.drawOval(srcRectF, paintSrc);

        return srcBitmap;
    }

    /**
     * 获取外部框
     */
    private Bitmap getBitMap(int w, int h) {

        Bitmap.Config DstConfig = Bitmap.Config.ARGB_8888;
        Bitmap dstBitmap = Bitmap.createBitmap(w, h, DstConfig);

        Canvas dstCanvas = new Canvas(dstBitmap);
        Paint paintDst = new Paint(1);

        if (isFrame) {
            paintDst.setColor(color);
        }

        RectF rectF = new RectF(0, 0, w, h);
        dstCanvas.drawOval(rectF, paintDst);

        return dstBitmap;
    }


}
