package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
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
public class GroupHeadView extends ImageView {


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
    private boolean isFrame = true;

    /**
     * 图像width
     */
    private int w;

    /**
     * 图像width
     */
    private int h;

    private int flag;

    private int number;

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        PorterDuff.Mode circleMode = PorterDuff.Mode.DST_ATOP;
        MASK_DST = new PorterDuffXfermode(localMode);
        MASK_CIRCLE = new PorterDuffXfermode(circleMode);
    }

    public GroupHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        width = (int) array.getDimension(R.styleable.CircleView_circleWidth, 10);
        color = array.getColor(R.styleable.CircleView_circleColor, Color.WHITE);
        isFrame = array.getBoolean(R.styleable.CircleView_circleIsFrame, true);
        number = array.getInt(R.styleable.GroupHeadView_GroupNumber, 2);
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
            paintSrc.setFilterBitmap(false);
            paintSrc.setAntiAlias(true);
        }

        flag = canvas.saveLayer(0.0F, 0.0F, w, h, null, 31);

        if (number == 1) {
            canvas.drawBitmap(getCircleBitmap(drawable, w, h), 0f, 0f, paintSrc);
        }

        if (number == 2) {
            canvas.drawBitmap(getCircleBitmap(drawable, w * 2 / 3, h * 2 / 3), w * 1 / 3, 0f,
                    paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w * 2 / 3, h * 2 / 3), 0f, h * 1 / 3,
                    paintSrc);

        }
        if (number == 3) {
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), w / 4, 0f, paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), w / 2, h / 2, paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), 0f, h / 2, paintSrc);
        }
        if (number == 4) {
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), 0f, 0f, paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), w / 2, h / 2, paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), w / 2, 0f, paintSrc);
            canvas.drawBitmap(getCircleBitmap(drawable, w / 2, h / 2), 0f, h / 2, paintSrc);
        }

        if (!isInEditMode()) {
            canvas.restoreToCount(flag);
        }
    }

    public void setNumber(int number) {
        this.number = number;
        invalidate();
    }

    public Bitmap getDrawables() {
        int w = getWidth();
        int h = getHeight();

        return getCircleBitmap(drawable, w / 2, h / 2);


    }

    public void setDrawables(Drawable... drawable) {
    }

    /**
     * 获得外加边框bitmap
     */
    private Bitmap getCircleBitmap(Drawable drawable, int W, int H) {

        Bitmap srcBitmap = getSrcBitMap(drawable, W, H);
        Bitmap drcBitmap = getDrcBitMap(W, H);

        Canvas canvas = new Canvas(srcBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(MASK_CIRCLE);

        canvas.drawBitmap(drcBitmap, 0f, 0f, paint);

        return srcBitmap;

    }

    /**
     * 获取Src圆圈
     */
    private Bitmap getSrcBitMap(Drawable drawable, int w, int h) {

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(w, h, srcConfig);

        Canvas srcCanvas = new Canvas(srcBitmap);
        Paint paintSrc = new Paint(1);

        RectF srcRectF = new RectF(width, width, w - width, h - width);

        srcCanvas.drawOval(srcRectF, paintSrc);

        Bitmap bitmap = getBitmap(drawable, w, h);
        paintSrc.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        srcCanvas.drawBitmap(bitmap, 0f, 0f, paintSrc);

        return srcBitmap;
    }


    /**
     * 获取外部框
     */
    private Bitmap getDrcBitMap(int w, int h) {

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

    /**
     * drawable to bitmap
     */
    private Bitmap getBitmap(Drawable drawable, int w, int h) {

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);

        return bitmap;
    }


}
