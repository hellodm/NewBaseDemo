package com.hello.app.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/4/28
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class TitleRelativeLayout extends RelativeLayout {


    private LinearLayout viewLeft, viewCenter, viewRight;

    public TitleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getChildCount() != 3) {
            return;
        }
        viewLeft = (LinearLayout) getChildAt(0);
        viewCenter = (LinearLayout) getChildAt(1);
        viewRight = (LinearLayout) getChildAt(2);
        //获取左右布局尺寸
        int widthLeft = viewLeft.getMeasuredWidth();
        int widthRight = viewRight.getMeasuredWidth();
        //设置左右布局
        LayoutParams paramL = (LayoutParams) viewLeft.getLayoutParams();
        paramL.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        LayoutParams paramR = (LayoutParams) viewRight.getLayoutParams();
        paramR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //设置center布局的具体位置
        LayoutParams params = (LayoutParams) viewCenter.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.setMargins(widthLeft, viewCenter.getTop(), widthRight, viewCenter.getBottom());
        //根据内容重新设置中心位置
        setPadding(w, widthLeft, widthRight);


    }


    /** 根据布局尺寸设置textView的padding */
    private void setPadding(final int totalWidth, final int leftWidth, final int rightWidth) {
        int linWidth = 0;
        for (int i = 0; i < viewCenter.getChildCount(); i++) {
            linWidth = linWidth + viewCenter.getChildAt(i).getMeasuredWidth();
        }
        int spaceLeft = totalWidth / 2 - leftWidth;
        int spaceRight = totalWidth / 2 - rightWidth;
        int leftPadding = spaceLeft - linWidth / 2;
        int rightPadding = spaceRight - linWidth / 2;

        viewCenter.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        if (leftPadding < 0) {
            leftPadding = 0;
        }
        if (rightPadding < 0) {
            leftPadding = 0;
            viewCenter.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }

        viewCenter.setPadding(leftPadding, viewCenter.getPaddingTop(), viewCenter.getPaddingRight(),
                viewCenter.getPaddingBottom());
    }


}
