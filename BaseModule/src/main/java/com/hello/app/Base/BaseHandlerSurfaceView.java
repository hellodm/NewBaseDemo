package com.hello.app.Base;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by dong on 2014/7/29.
 */
public abstract class BaseHandlerSurfaceView extends BaseToolSurfaceView {


    public BaseHandlerSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setRunType(RunType.HANDLER);

    }


}
