package com.hello.app.MyView.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/14
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyHeadView extends LinearLayout {


    private View mHead;

    private View mContent;

    private Context mContext;


    public MyHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MyHeadView(Context context) {
        super(context);
    }

//    void init(){
//        mMapView = new TextureMapView(getContext());
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                (int) ViewUtil.dip2px(getContext(), 682));
//        lp.topMargin = (int) ViewUtil.dip2px(getContext(), -482);
//        mMapView.setLayoutParams(lp);
//
//        mBaiduMap = mMapView.getMap();
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showMarker();
//            }
//        }, 50);
//
//
//        mRecycleView = new MyRecycleView(mContext);
//        mRecycleView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        addView(mMapView,0);
//        addView(mRecycleView,1);
//    }
//
//    public TextureMapView getMapView() {
//        return mMapView;
//    }
//    public MyRecycleView getRecyclerView() {
//        return mRecycleView;
//    }

//    void showMarker() {
//        LatLng llA = new LatLng(39.856965, 116.401394);
//        LatLng llAB = new LatLng(40.106965, 116.401394);
//        BitmapDescriptor bdA = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_car);
//        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
//                .zIndex(9).draggable(true);
//        // 掉下动画
////            ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(llAB));
//    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childCount = getChildCount();
        if (childCount <= 1) {
            throw new IllegalStateException("MyHeadView must host 3 elements");
        } else if (childCount > 2) {
            throw new IllegalStateException("MyHeadView only can host 3 elements");
        } else {
            mHead = getChildAt(0);
            mContent = getChildAt(1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        if (getOrientation() == VERTICAL) {
//            layoutVertical(l, t, r, b);
//        } else {
//            throw new IllegalStateException("MyHeadView only can not support HORIZONTAL");
//        }

    }


    void layoutVertical(int l, int t, int r, int b) {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        if (mHead != null) {
            final int left = paddingLeft;
            final int top = paddingTop;
            final int right = left + mHead.getMeasuredWidth();
            final int bottom = top + mHead.getMeasuredHeight();
            mHead.layout(left, top, right, bottom);

        }
        if (mContent != null) {
            final int left = paddingLeft;
            final int top = paddingTop + mHead.getMeasuredHeight();
            final int right = left + mContent.getMeasuredWidth();
            final int bottom = top + mContent.getMeasuredHeight();
            mContent.layout(left, top, right, bottom);
        }

    }


}