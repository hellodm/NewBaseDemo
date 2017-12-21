package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义的群头像变化view
 * Created by dong on 2014/10/29.
 */
public class GroupImageView extends ImageView {


    private Paint paintSrc;

    private static final Xfermode MASK_DST;

    private static final Xfermode MASK_CIRCLE;

    /** 圈子头像个数 */
    private int number;

    /**
     * 图像width
     */
    private int w;

    /**
     * 图像width
     */
    private int h;

    /**
     * 边框宽度
     */
    private int width;

    /**
     * 边框颜色
     */
    private int color;

    private int flag;

    private Drawable[] mDrawables;


    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        PorterDuff.Mode circleMode = PorterDuff.Mode.DST_ATOP;
        MASK_DST = new PorterDuffXfermode(localMode);
        MASK_CIRCLE = new PorterDuffXfermode(circleMode);
    }


    public GroupImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

        w = getWidth();
        h = getHeight();

        if (paintSrc == null) {
            paintSrc = new Paint();
            paintSrc.setColor(color);
            paintSrc.setFilterBitmap(false);
            paintSrc.setAntiAlias(true);
            paintSrc.setXfermode(MASK_DST);
        }

        flag = canvas.saveLayer(0.0F, 0.0F, w, h, null, 31);

        if (mDrawables == null || mDrawables.length == 0) {

            return;

        }

        getSingleBitMap(paintSrc, canvas, getDrawables()[0]);

    }

    private Bitmap srcBitmap;

    private Bitmap circleBitmap;

    /** 获取单个剪切带边框图片 */
    private void getSingleBitMap(Paint paint, Canvas canvas, Drawable drawable) {

        drawable.setBounds(width, width, w - width, h - width);
        drawable.draw(canvas);

        if ((this.srcBitmap == null) || (this.srcBitmap.isRecycled())) {
            srcBitmap = getSrcBitMap();
        }
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        paint.setXfermode(MASK_CIRCLE);

        if ((this.circleBitmap == null) || (this.circleBitmap.isRecycled())) {
            circleBitmap = getDstBitMap(w, h);
        }

        canvas.drawBitmap(circleBitmap, 0f, 0f, paint);

        if (!isInEditMode()) {
            canvas.restoreToCount(flag);
        }


    }


    /** 设置drawable */
    public void setDrawables(Drawable... drawables) {

        mDrawables = drawables;


    }


    /** 获取drawable */
    public Drawable[] getDrawables() {

        return mDrawables;


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

        RectF srcRectF = new RectF(width, width, w, h);
        srcCanvas.drawOval(srcRectF, paintSrc);

        return srcBitmap;
    }

    /**
     * 获取外部框
     */
    private Bitmap getDstBitMap(int w, int h) {

        Bitmap.Config DstConfig = Bitmap.Config.ARGB_8888;
        Bitmap dstBitmap = Bitmap.createBitmap(w, h, DstConfig);

        Canvas dstCanvas = new Canvas(dstBitmap);
        Paint paintDst = new Paint(1);

        paintDst.setColor(color);

        RectF rectF = new RectF(0, 0, w, h);
        dstCanvas.drawOval(rectF, paintDst);

        return dstBitmap;
    }


}
