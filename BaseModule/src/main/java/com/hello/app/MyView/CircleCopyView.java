package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by dong on 2014/9/2.
 */
public class CircleCopyView extends ImageView {

    public CircleCopyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected void onDraw(Canvas paramCanvas) {
        Drawable localDrawable = getDrawable();

        Bitmap bitmap = getRound(drawable2Bitmap(localDrawable), getHeight() - 10);
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置无锯齿
        paint.setFilterBitmap(true);
        paint.setDither(true);

        paint.setColor(Color.RED);
        paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);

        paramCanvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);

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
            return null;
        }
    }


    public static Bitmap getRound(Bitmap bitmap, int radius) {

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int x = 0;
        int y = 0;
        int square_side = 0;  //正方形的边长

        Bitmap square_bitmap = null;

        //应为不能确定图片是否是正方形，所以先截取正方形

        if (w > h) {
            square_side = h;
            x = (w - h) / 3;
            y = 0;
            square_bitmap = Bitmap.createBitmap(bitmap, x, y, square_side, square_side);


        } else {
            square_side = w;
            x = 0;
            y = (h - w) / 3;
            square_bitmap = Bitmap.createBitmap(bitmap, x, y, square_side, square_side);

        }

        //根据传入的半径缩小图片的尺寸
        Bitmap scale = Bitmap.createScaledBitmap(square_bitmap, radius, radius, true);

        //做一个即将剪切的底片
        Bitmap output = Bitmap
                .createBitmap(scale.getWidth(), scale.getHeight(), Bitmap.Config.ARGB_8888);
        //根据底片创建canvas
        Canvas canvas = new Canvas(output);

        //创建画笔--设置画笔无锯齿
        Paint paint = new Paint();
        paint.setAntiAlias(true);// 设置无锯齿
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.RED);

//        Rect rect = new Rect(0, 0, scale.getWidth(), scale.getHeight());
//        paint.setStyle(Paint.Style.STROKE); //Paint.Style.FILL -
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(10);

//      //画布上画一个圆的背景图
        canvas.drawCircle(scale.getWidth() / 2, scale.getHeight() / 2, scale.getWidth() / 2, paint);
        //设置paint的组合个方式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

        //最终的底片和原始图片进行整合
        canvas.drawBitmap(scale, 0, 0, paint);

        releaseBitmap(bitmap);
        releaseBitmap(square_bitmap);
        releaseBitmap(scale);

        return output;
    }

    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }


}
