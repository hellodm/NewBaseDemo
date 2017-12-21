package com.hello.app.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.hello.app.R;

/**
 * Created by dong on 2014/9/2.
 */
public class CornerView extends ImageView {


    private float radiusCorner;

    public void setRadiusCorner(float radiusCorner) {
        this.radiusCorner = radiusCorner;
    }

    public CornerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CornerView);

        radiusCorner = array.getFloat(R.styleable.CornerView_corner_radius, 20);//后面自己设置默认值

        array.recycle();//释放资源


    }


    @Override
    protected void onDraw(Canvas canvas) {

        int i = getWidth();
        int j = getHeight();

        Drawable localDrawable = getDrawable();

        Bitmap bitmap = drawable2Bitmap(localDrawable);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

//        if(w>i&&h>j){
//            //根据传入的半径缩小图片的尺寸
//            bitmap = Bitmap.createScaledBitmap(bitmap, i,j , true);
//
//        }

        bitmap = Bitmap.createScaledBitmap(bitmap, i, j, true);

        bitmap = getRoundedCornerBitmap(i, j, bitmap, radiusCorner);
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置无锯齿
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);


    }


    /**
     * 获取圆角图片
     */
    public static Bitmap getRoundedCornerBitmap(int w, int h, Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {

        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

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

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmap;

        } else if (drawable instanceof NinePatchDrawable) {
            bitmap = drawableToBitmap(drawable);
            return bitmap;
        } else {
            Log.i("drawable2Bitmap", "进入误判断状态");
            return null;
        }
    }


}
