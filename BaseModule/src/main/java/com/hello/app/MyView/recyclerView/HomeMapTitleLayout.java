package com.hello.app.MyView.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hello.app.R;

import java.util.LinkedHashSet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：6.0 版本的首页title layout
 *
 * @author dong
 * @Time: 2017/3/23
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class HomeMapTitleLayout extends RelativeLayout {

    @InjectView(R.id.header_center_title_layout)
    public ViewGroup mCenterLayout;

    private boolean isBacFade = false;//背景是否渐变

    private float scale;//背景颜色百分比

    private Title mTitle;

    private OnTitleClickListener mListener;

    private LinkedHashSet<String> mSet;

    private SlidingTabLayout slidingTabLayout;

    private View mView1, mView2;

    public HomeMapTitleLayout(Context context) {
        super(context);
        init(context);

    }

    public HomeMapTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public HomeMapTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    void init(Context context) {
        inflate(context, R.layout.common_header_home_kartor, this);
        inflate(context, R.layout.common_header_home_menu_, this);
        ButterKnife.inject(this, this);
        slidingTabLayout = new SlidingTabLayout(context);
        mCenterLayout.addView(slidingTabLayout);
        mView1 = getChildAt(1);
        mView2 = getChildAt(0);

    }

    //设置是否背景需要渐变
    public void setBacFade(boolean bacFade) {
        isBacFade = bacFade;
    }

    //设置背景颜色渐变值
    public void setBackgroundScale(@FloatRange(from = 0.0, to = 1.0) float scale) {
        if (isBacFade) {
            mView1.setVisibility(VISIBLE);
//            mView2.setVisibility(GONE);
            setBackgroundColor(Color.argb((int) (255 * scale), 59, 59, 59));
            mView1.setAlpha(1 - scale * 2);
            float s;
            if (scale - 0.5f < 0) {
                s = 0;
            } else {
                s = 2 * scale - 1.0f;
            }
            mView2.setAlpha(s);
        }
    }


    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        mView1.setVisibility(GONE);
        mView2.setVisibility(VISIBLE);
        mView2.setAlpha(1);
    }

    public void setCars() {
        mSet = new LinkedHashSet<>();
        mSet.add("笨笨");
        mSet.add("奔奔");
        if (mSet == null || mSet.isEmpty()) {
            mTitle = Title.NONE;
        } else if (mSet.size() == 1) {
            mTitle = Title.ONE;
        } else if (mSet.size() == 2) {
            mTitle = Title.TWO;
        } else {
            mTitle = Title.MORE;
        }

    }

    @OnClick(R.id.go_down)
    public void goDown() {
        mListener.goDown();
    }

    @OnClick(R.id.go_up)
    public void goUp() {
        mListener.goUp();
    }


    public void updateTitle() {

        slidingTabLayout.setData(mSet.toArray(new String[]{}));

    }

    public void setOnTitleClickListener(OnTitleClickListener listener) {
        mListener = listener;
    }


    //私有方法
    private void update() {

    }

    public enum Title {

        NONE, //无车
        ONE,  //一辆车
        TWO,  //二辆车
        MORE, //多辆车
        TITLE //显示驾图

    }


    public interface OnTitleClickListener {

        void goDown();

        void goUp();

        void clickCar(String carId);

    }


}
