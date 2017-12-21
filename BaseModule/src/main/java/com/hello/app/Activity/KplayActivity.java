package com.hello.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hello.app.Fragment.Fragment1;
import com.hello.app.Fragment.Fragment2;
import com.hello.app.MyView.DirectionalViewPager;
import com.hello.app.MyView.ViewPager.LayoutAdapter;
import com.hello.app.MyView.ViewPager.RecyclerViewPager;
import com.hello.app.R;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class KplayActivity extends FragmentActivity {

    //    @InjectView(R.id.pager)
//    DirectionalViewPager mViewPager;
    @InjectView(R.id.pager)
    RecyclerViewPager mRecyclerView;

    @InjectView(R.id.lin_viewPage)
    LinearLayout mLin;

    private DirectionalPagerAdapter adapter;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.play_activity);
//        ButterKnife.inject(this);
//        mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
//        mLin.setClipChildren(false);
//        mViewPager.setClipChildren(false);
//        mViewPager.setOffscreenPageLimit(3);
//        adapter = new DirectionalPagerAdapter(this.getSupportFragmentManager());
//        mViewPager.setAdapter(adapter);
//
//        mLin.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return mViewPager.dispatchTouchEvent(event);
//            }
//        });
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        ButterKnife.inject(this);
        initViewPager();

    }


    protected void initViewPager() {
        mRecyclerView = (RecyclerViewPager) findViewById(R.id.pager);

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setTriggerOffset(0.15f);
        mRecyclerView.setFlingFactor(0.25f);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.setAdapter(new LayoutAdapter(this, mRecyclerView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
//                mPositionText.setText("First: " + mRecyclerViewPager.getFirstVisiblePosition());
                int childCount = mRecyclerView.getChildCount();
                int width = mRecyclerView.getChildAt(0).getWidth();
                int padding = (mRecyclerView.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v
                                    .getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                    int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mRecyclerView.getChildCount() < 3) {
                    if (mRecyclerView.getChildAt(1) != null) {
                        View v1 = mRecyclerView.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (mRecyclerView.getChildAt(0) != null) {
                        View v0 = mRecyclerView.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (mRecyclerView.getChildAt(2) != null) {
                        View v2 = mRecyclerView.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });
    }


    private class DirectionalPagerAdapter extends FragmentPagerAdapter {

        private HashMap<Integer, Fragment> mObjectMap = new HashMap<Integer, Fragment>();

        public DirectionalPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mObjectMap.get(position);
            if (fragment == null) {
                if (position == 0) {
                    fragment = new Fragment1();
                }
                if (position == 1) {
                    fragment = new Fragment2();
                }
                if (position == 2) {
                    fragment = new Fragment1();
                }
                if (position == 3) {
                    fragment = new Fragment2();
                }
                if (position == 4) {
                    fragment = new Fragment1();
                }
                if (position == 5) {
                    fragment = new Fragment2();
                }

                mObjectMap.put(position, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }
    }


}
