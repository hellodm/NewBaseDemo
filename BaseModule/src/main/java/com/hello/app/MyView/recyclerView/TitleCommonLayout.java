package com.hello.app.MyView.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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
public class TitleCommonLayout extends ViewGroup {


    private View viewLeft, viewCenter, viewRight;

    public TitleCommonLayout(Context context, AttributeSet attrs) {
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
            int childLeft = r - rightWidth - l;
            int childTop = 0;
            child.layout(childLeft, childTop, r, childTop + rightHeight);
        }
        //设置中间布局的位置
        child = getChildAt(1);
        if (child.getVisibility() != GONE) {
            centerWidth = child.getMeasuredWidth();
            centerHeight = child.getMeasuredHeight();

            int childLeft = (r - l) / 2 - centerWidth / 2;  //默认居中
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
        if (lp.width == LayoutParams.MATCH_PARENT) {

            childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getMeasuredWidth() - viewLeft.getMeasuredWidth() - viewRight.getMeasuredWidth(),
                    MeasureSpec.EXACTLY);
        } else {
            childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getMeasuredWidth() - viewLeft.getMeasuredWidth() - viewRight.getMeasuredWidth(),
                    MeasureSpec.AT_MOST);
        }

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

