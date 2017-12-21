package com.hello.app.MyView.recyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/7
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyRecycleView extends RecyclerView {

    int lastY = 0;

    private View mHeadView;

    private boolean mReversed;

    private ScrollViewListener scrollViewListener = null;

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
    }

    private int mDownScroll;

    private int mCurrentScroll;

    public void addHeader(View headView) {
        mReversed = isLayoutManagerReversed(this);
        mHeadView = headView;
        attachToRecycler();

    }

    public void attachToRecycler() {
        getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int paddingTop = ((MarginLayoutParams) mHeadView
                                .getLayoutParams()).topMargin;
                        int height = mHeadView.getHeight() + paddingTop;
                        if (height > 0) {
                            if (android.os.Build.VERSION.SDK_INT
                                    >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                mHeadView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            } else {
                                mHeadView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            }

                            addItemDecoration(new HeaderItemDecoration(getLayoutManager(), height),
                                    0);
                        }
                    }
                });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof MyRecyclerAdapter) {
            ((MyRecyclerAdapter) adapter).setView(new MyRecyclerAdapter.OnViewDelete() {
                @Override
                public void deleteView(int view) {
//                    mCurrentScroll=mCurrentScroll-view;
                }
            });
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, dx, lastY + dy, dx, lastY);
            lastY += dy;
            mCurrentScroll += dy;
            if (mHeadView != null) {
                mHeadView.setTranslationY(-mCurrentScroll);
            }
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(MyRecycleView scrollView, int x, int y, int oldx, int oldy);

    }

    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }


    private boolean isLayoutManagerReversed(RecyclerView recycler) {
        boolean reversed = false;
        RecyclerView.LayoutManager manager = recycler.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            reversed = ((LinearLayoutManager) manager).getReverseLayout();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            reversed = ((StaggeredGridLayoutManager) manager).getReverseLayout();
        }
        return reversed;
    }

    private class HeaderItemDecoration extends RecyclerView.ItemDecoration {

        private int mHeaderHeight;

        private int mNumberOfChildren;

        public HeaderItemDecoration(RecyclerView.LayoutManager layoutManager, int height) {
            if (layoutManager.getClass() == LinearLayoutManager.class) {
                mNumberOfChildren = 1;
            } else if (layoutManager.getClass() == GridLayoutManager.class) {
                mNumberOfChildren = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                mNumberOfChildren = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            }
            mHeaderHeight = height;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int value = (parent.getChildLayoutPosition(view) < mNumberOfChildren) ? mHeaderHeight
                    : 0;
            if (mReversed) {
                outRect.bottom = value;
            } else {
                outRect.top = value;
            }
        }
    }


}
