package com.hello.app.Base;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by dong on 2014/7/29.
 */
public abstract class BaseThreadSurfaceView extends BaseToolSurfaceView {


    public BaseThreadSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRunType(RunType.THREAD);
    }


}
