/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hello.app.MyView.recyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.app.R;

import java.util.LinkedHashSet;


/**
 * 发现顶部滑动tab控件
 *
 * @author wangj
 * @version 4.9.0
 * @date 2016-04-26
 */
public class SlidingTabLayout extends LinearLayout {


    private OnPageChangedListener mPageChangedListener;

    private int mSelectedPosition;

    private float mSelectionOffset;

    private Paint mPaint = new Paint();

    private Bitmap mSelectedBmp;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        setOrientation(HORIZONTAL);

        mSelectedBmp = drawableToBitmap(ResourcesCompat
                .getDrawable(getResources(), R.drawable.sliding_tab_selected_bg, null));

    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDownloadSelectedBmp() {
//        mSelectedBmp = ImageUtils.drawableToBitmap(ResourcesCompat
//                .getDrawable(getResources(), R.drawable.sliding_download_selected_bg, null));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setAntiAlias(true);
        int childCount = getChildCount();
        if (childCount > 0) {
            View selectedTab = getChildAt(mSelectedPosition);
            int left = selectedTab.getLeft();
            int width = mSelectedBmp.getWidth();

            canvas.drawBitmap(mSelectedBmp, left + mSelectionOffset * width, 0, mPaint);

            for (int i = 0; i < childCount; i++) {
                TextView child = (TextView) getChildAt(i);
                if (i == mSelectedPosition) {
                    child.setTextColor(
                            getResources().getColor(R.color.white));
                } else {
                    child.setTextColor(getResources().getColor(R.color.black));
                }
            }
        }
    }

    public void setData(@NonNull String[] set) {

        final View.OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < set.length; i++) {
            TextView tabView = createDefaultTabView();
            tabView.setText(set[i]);
            tabView.setOnClickListener(tabClickListener);
            addView(tabView);
        }


    }

    public void setOnPageChangedListener(OnPageChangedListener listener) {
        mPageChangedListener = listener;
    }

    protected TextView createDefaultTabView() {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        textView.setTypeface(Typeface.DEFAULT);
        int width = mSelectedBmp.getWidth();
        int height = mSelectedBmp.getHeight();
        textView.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        return textView;
    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }


    private class TabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = 0; i < getChildCount(); i++) {
                if (v == getChildAt(i)) {

                    return;
                }
            }
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int childCount = getChildCount();
            if ((childCount == 0) || (position < 0) || (position >= childCount)) {
                return;
            }
            onViewPagerPageChanged(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                onViewPagerPageChanged(position, 0f);
            }
            if (null != mPageChangedListener) {
                mPageChangedListener.onPageChanged(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }
    }

    public interface OnPageChangedListener {

        void onPageChanged(int position);
    }

}
