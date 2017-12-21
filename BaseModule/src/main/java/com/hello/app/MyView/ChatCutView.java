package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/11/21
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class ChatCutView extends ImageView {

    public ChatCutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }


    public static Bitmap getMBitmap(Bitmap bitmap, Bitmap bitMapSrc) {

        int w = bitMapSrc.getWidth();
        int h = bitMapSrc.getHeight();

        NinePatch np = new NinePatch(bitmap, bitmap.getNinePatchChunk(), null);
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
