package com.hello.app.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/4/28
 *
 * 自定义的标题居中、省略的布局
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class TitleLayout extends ViewGroup {


    private View viewLeft, viewCenter, viewRight;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int totalWidth = r - l;
        int leftWidth = 0, leftHeight = 0;
        int rightWidth = 0, rightHeight = 0;
        int centerWidth = 0, centerHeight = 0;

        //设置左边布局的位置
        View child = getChildAt(0);
        if (child.getVisibility() != GONE) {
            leftWidth = child.getMeasuredWidth();
            leftHeight = child.getMeasuredHeight();
            int childLeft = 0;
            int childTop = 0;
            child.layout(childLeft, childTop, childLeft + leftWidth, childTop + leftHeight);
        }
        //设置右边布局的位置
        child = getChildAt(2);
        if (child.getVisibility() != GONE) {

            rightWidth = child.getMeasuredWidth();
            rightHeight = child.getMeasuredHeight();
            int childLeft = r - rightWidth;
            int childTop = 0;
            child.layout(childLeft, childTop, r, childTop + rightHeight);
        }
        //设置中间布局的位置
        child = getChildAt(1);
        if (child.getVisibility() != GONE) {
            centerWidth = child.getMeasuredWidth();
            centerHeight = child.getMeasuredHeight();

            int childLeft = r / 2 - centerWidth / 2;  //默认居中
            int childRight = childLeft + centerWidth;
            int childTop = 0;

            int spaceLeft = totalWidth / 2 - leftWidth;
            int spaceRight = totalWidth / 2 - rightWidth;
            int leftPadding = spaceLeft - centerWidth / 2;
            int rightPadding = spaceRight - centerWidth / 2;

            if (leftPadding < 0) {//左靠
                childLeft = leftWidth;
                childRight = childLeft + centerWidth;
            }
            if (rightPadding < 0) {//右靠
                childRight = r - rightWidth;
                childLeft = childRight - centerWidth;
            }

            child.layout(childLeft, childTop, childRight, childTop + centerHeight);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childWidthMeasureSpec;
        int childHeightMeasureSpec;

        viewLeft = getChildAt(0);
        viewCenter = getChildAt(1);
        viewRight = getChildAt(2);

        LayoutParams lp = viewLeft.getLayoutParams();

        if (lp.width == LayoutParams.MATCH_PARENT) {
            childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
                    MeasureSpec.EXACTLY);
        } else {
            childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0,
                    lp.width);
        }

        if (lp.height == LayoutParams.MATCH_PARENT) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                    MeasureSpec.EXACTLY);
        } else {
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0,
                    lp.height);
        }

        viewLeft.measure(childWidthMeasureSpec, childHeightMeasureSpec);

        lp = viewRight.getLayoutParams();

        if (lp.width == LayoutParams.MATCH_PARENT) {
            childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
                    MeasureSpec.EXACTLY);
        } else {
            childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0,
                    lp.width);
        }

        if (lp.height == LayoutParams.MATCH_PARENT) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                    MeasureSpec.EXACTLY);
        } else {
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0,
                    lp.height);
        }

        viewRight.measure(childWidthMeasureSpec, childHeightMeasureSpec);

        lp = viewCenter.getLayoutParams();

        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - viewLeft.getMeasuredWidth() - viewRight.getMeasuredWidth(),
                MeasureSpec.AT_MOST);

        if (lp.height == LayoutParams.MATCH_PARENT) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                    MeasureSpec.EXACTLY);
        } else {
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0,
                    lp.height);
        }

        viewCenter.measure(childWidthMeasureSpec, childHeightMeasureSpec);


    }


}

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//
//        Log.i("onLayout","onLayout");
//
//            if (getChildCount() != 3) {
//                return;
//            }
//            int w = r - l;
//            viewLeft = (LinearLayout) getChildAt(0);
//            viewCenter = (LinearLayout) getChildAt(1);
//            viewRight = (LinearLayout) getChildAt(2);
//            //获取左右布局尺寸
//            int widthLeft = viewLeft.getMeasuredWidth();
//            int widthRight = viewRight.getMeasuredWidth();
//            //设置左右布局
//            LayoutParams paramL = (LayoutParams) viewLeft.getLayoutParams();
//            paramL.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            LayoutParams paramR = (LayoutParams) viewRight.getLayoutParams();
//            paramR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            //设置center布局的具体位置
//            LayoutParams params = (LayoutParams) viewCenter.getLayoutParams();
//            params.width = LayoutParams.MATCH_PARENT;
//            params.setMargins(widthLeft, viewCenter.getTop(), widthRight,
//                    viewCenter.getBottom());
//            //根据内容重新设置中心位置
//            setPadding(w, widthLeft, widthRight);
//    }
//
//    /** 根据布局尺寸设置textView的padding */
//    private void setPadding(final int totalWidth, final int leftWidth, final int rightWidth) {
//        int linWidth = 0;
//        for (int i = 0; i < viewCenter.getChildCount(); i++) {
//            linWidth = linWidth + viewCenter.getChildAt(i).getMeasuredWidth();
//        }
//        int spaceLeft = totalWidth / 2 - leftWidth;
//        int spaceRight = totalWidth / 2 - rightWidth;
//        int leftPadding = spaceLeft - linWidth / 2;
//        int rightPadding = spaceRight - linWidth / 2;
//
//        viewCenter.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//
//
//        if (leftPadding < 0) {
//            leftPadding = 0;
//        }
//        if (rightPadding < 0) {
//            leftPadding = 0;
//            viewCenter.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
//        }
//
//        viewCenter.setPadding(leftPadding, viewCenter.getPaddingTop(), viewCenter.getPaddingRight(),
//                viewCenter.getPaddingBottom());
//    }
