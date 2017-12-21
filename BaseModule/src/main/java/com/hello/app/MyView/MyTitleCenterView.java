package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/4/27
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyTitleCenterView extends LinearLayout {

    private Context mContext;

    private ViewUtil.ScreenSize mScreenSize;

    private int screenWidth, screenHeight, viewWidth, viewHeight;

    private View layoutView, mLeftView, mRightView;

    @InjectView(R.id.title_center_text)
    public TextView mTextView;

    public MyTitleCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScreenSize = ViewUtil.getWindowScreen(ViewUtil.WINDOW_PX, mContext);
        screenWidth = (int) mScreenSize.width;
        screenHeight = (int) mScreenSize.height;
        addLayout();
    }

    /** 更新textView的尺寸 */
    public void updateSize(View leftView, View rightView) {
        mLeftView = leftView;
        mRightView = rightView;
    }


    /** add布局view */
    private void addLayout() {
        layoutView = LayoutInflater.from(mContext).inflate(R.layout.layout_title_center, null);
        ButterKnife.inject(this, layoutView);
        this.addView(layoutView);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
////        super.onDraw(canvas);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawText(getText().toString(), viewWidth / 2, viewHeight / 2, mPaint);
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mLeftView != null && mRightView != null) {
            int widthSize_left = MeasureSpec.getSize(mLeftView.getMeasuredWidth());
            int widthSize_right = MeasureSpec.getSize(mRightView.getMeasuredWidth());
            viewWidth = screenWidth - widthSize_left - widthSize_right;
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY);
            setPadding(widthMeasureSpec, widthSize_left, widthSize_right);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /** 根据布局尺寸设置textView的padding */
    private void setPadding(final int totalWidth, final int leftWidth, final int rightWidth) {
        final int linWidth = layoutView.getMeasuredWidth();
        final int space = (int) (((float) (totalWidth - linWidth)) / 2);
        int spaceLeft = screenWidth / 2 - leftWidth;
        int spaceRight = screenWidth / 2 - rightWidth;
        int leftPadding = spaceLeft - linWidth / 2;
        int rightPadding = spaceRight - linWidth / 2;

        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        if (leftPadding < 0) {
            leftPadding = 0;
        }
        if (rightPadding < 0) {
            leftPadding = 0;
            setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }

        this.setPadding(leftPadding, this.getPaddingTop(), this.getPaddingRight(),
                this.getPaddingBottom());
    }


    public void addText(String text) {
        mTextView.setText(mTextView.getText().toString() + text);
    }

}
