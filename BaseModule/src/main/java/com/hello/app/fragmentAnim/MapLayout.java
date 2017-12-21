package com.hello.app.fragmentAnim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/3
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MapLayout extends ViewGroup {

    public MapLayout(Context context) {
        super(context);
    }

    public MapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

    }
}
