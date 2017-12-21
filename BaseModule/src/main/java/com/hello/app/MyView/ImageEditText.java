package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by dong on 2014/7/31.
 */
public class ImageEditText extends EditText {


    public ImageEditText(Context context) {
        super(context);
    }

    public ImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
