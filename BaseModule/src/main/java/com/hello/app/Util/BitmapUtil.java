package com.hello.app.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by dong on 2014/7/15.
 * <br\>
 * Bitmap的实用工具类
 */
public class BitmapUtil {


    /**
     * Bitmap==>Bytes
     */
    public static byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 传入资源ID获取Bitmap
     */
    public static Bitmap getBitmapFromID(Context context, int ResourceId) {
//        Resources res = context.getResources();
//        Bitmap bmp = BitmapFactory.decodeResource(res, ResourceId);

        /**据说是最深内存的读取本地资源的方法*/

        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;

        opt.inInputShareable = true;

        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(ResourceId);

        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * Bytes==>Bitmap
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * drawable==>Bitmap
     */
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

    /**
     * Drawable 转 bitmap
     */
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


    /**
     * Bitmap==>drawable
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {

        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);

        return bd;
    }


    /**
     * 缩放Biutmap
     */

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

        //可以用 Bitmap.createScaledBitmap(bitmap,width,height,true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        //获取比例
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);

        Matrix m = new Matrix();
        m.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);

    }

    /**
     * 缩放drawable
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {

        Bitmap bitmap = drawableToBitmap(drawable);

        return new BitmapDrawable(zoomBitmap(bitmap, w, h));

    }

    /**
     * 图片剪切成圆形图片的方法
     */

    public static Bitmap getRound(Drawable drawable, int radius) {

        return getRound(drawable2Bitmap(drawable), radius);
    }

    /**
     * 图片剪切成圆形图片的方法
     *
     * @param bitmap 传入图片的bitmap
     * @param radius 剪切之后圆形的半径
     */
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

//        Rect rect = new Rect(0, 0, scale.getWidth(), scale.getHeight());
//        paint.setStyle(Paint.Style.STROKE); //Paint.Style.FILL -
//        paint.setColor(Color.BLUE);
//        paint.setStrokeWidth(10);

//      //画布上画一个圆的背景图
        canvas.drawCircle(scale.getWidth() / 2, scale.getHeight() / 2, scale.getWidth() / 2, paint);
        //设置paint的组合个方式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

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


    /**
     * 获取圆角图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
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

    /**
     * 获取倒影图片
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /** 将图片切成特定的图样 */
    public static Bitmap cutGivenBitmap(Bitmap bitmap, Bitmap tempBitmap) {

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(bitmap).copy(srcConfig, true);

        PorterDuffXfermode MASK_TOP = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

        Paint paint = new Paint();
        paint.setXfermode(MASK_TOP);
        Canvas canvas = new Canvas(srcBitmap);

        canvas.drawBitmap(tempBitmap, 0f, 0f, paint);

//        carHead.recycle();
//        overLay.recycle();

        return srcBitmap;
    }

    /** 将图片切成特定的图样 */
    public static Bitmap getBacBitmap(Bitmap bitmap) {

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), srcConfig);

        PorterDuffXfermode MASK_TOP = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        paint.setXfermode(MASK_TOP);
        Canvas canvas = new Canvas(srcBitmap);

        canvas.drawBitmap(bitmap, 0f, 0f, paint);

        return srcBitmap;
    }


    public static Bitmap getMBitmap(Bitmap bitmap, Bitmap bitMapSrc) {

        int w = bitMapSrc.getWidth();
        int h = bitMapSrc.getHeight();
        boolean b = NinePatch.isNinePatchChunk(bitmap.getNinePatchChunk());
        NinePatch np;
        if (b) {
            np = new NinePatch(bitmap, bitmap.getNinePatchChunk(), null);
        } else {
            Log.i("NinePatch", "NinePatch有wrong");
            return bitMapSrc;
        }

        RectF rectf = new RectF(0, 0, w, h);

        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
        Bitmap srcBitmap = Bitmap.createBitmap(w, h, srcConfig);

        PorterDuffXfermode MASK_TOP = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        Paint paint = new Paint();
        paint.setXfermode(MASK_TOP);

        Canvas canvas = new Canvas(srcBitmap);
        np.draw(canvas, rectf);
        canvas.drawBitmap(bitMapSrc, 0f, 0f, paint);

        return srcBitmap;
    }
}
