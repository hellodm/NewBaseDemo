package com.hello.app.MyView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments����������
 *
 * @author Caochong
 * @Time: 2015/4/28
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyTitleLinearLayout extends LinearLayout {

    private Context mContext;

    private View layoutView;

    @InjectView(R.id.title_lin_left)
    public LinearLayout viewLeft;

    @InjectView(R.id.title_lin_right)
    public LinearLayout viewRight;

    @InjectView(R.id.title_textView)
    public MyTitleCenterView myTextView;

    public MyTitleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addLayout();
    }


    /** add布局view */
    private void addLayout() {
        layoutView = LayoutInflater.from(mContext).inflate(R.layout.layout_title, null);
        ButterKnife.inject(this, layoutView);
        myTextView.updateSize(viewLeft, viewRight);
        this.addView(layoutView);
    }


    /** add返回view */
    public void addBackButton() {
        TextView backView = new TextView(this.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(120,
                ViewGroup.LayoutParams.MATCH_PARENT);
        backView.setLayoutParams(layoutParams);
        backView.setGravity(Gravity.CENTER);
        backView.setText("返回");
        backView.setTextColor(Color.WHITE);
        viewLeft.addView(backView);

    }


    /** add返回view */
    public void addMenuButton() {
        TextView backView = new TextView(this.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(120,
                ViewGroup.LayoutParams.MATCH_PARENT);
        backView.setLayoutParams(layoutParams);
        backView.setGravity(Gravity.CENTER);
        backView.setText("菜单");
        backView.setTextColor(Color.WHITE);
        viewRight.addView(backView);
        myTextView.requestLayout();

    }

    public void addText() {

        myTextView.addText("标题");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

}
