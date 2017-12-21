package com.hello.app.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

public class CircularImage extends MaskedImage {

    public CircularImage(Context paramContext) {
        super(paramContext);
    }

    public CircularImage(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public CircularImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public Bitmap createMask() {
        try {
            int i = getWidth();
            int j = getHeight();
            Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
            Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
            Canvas localCanvas = new Canvas();
            Paint localPaint = new Paint(1);
            localPaint.setColor(-16777216);
            localPaint.setStyle(Paint.Style.FILL);

            float f1 = getWidth();
            float f2 = getHeight();

            localCanvas.setBitmap(localBitmap);
            RectF localRectF = new RectF(0.0F + 5, 0.0F + 5, f1 - 5, f2 - 5);
            localCanvas.save();
            localCanvas.drawOval(localRectF, localPaint);
            localCanvas.restore();

            localPaint.setColor(Color.RED);
            localCanvas.drawCircle(f1 / 2, f2 / 2, f1 / 2 - 20, localPaint);

            return localBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return null;
    }
}
