package com.hello.app.MyView.myRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hello.app.MyView.refresh.internal.LoadingLayout;
import com.hello.app.R;

/**
 * Created by dong on 2014/7/31.
 */
public abstract class RefreshBase<T extends View> extends LinearLayout implements IPullToRefresh {

    /** Constant常量字段 */

    static final float FRICTION = 2.0f;  //摩擦力


    /** Field字段 */
    private int mTouchSlop;

    private float mLastMotionX, mLastMotionY;

    private float mInitialMotionX, mInitialMotionY;

    private LoadingLayout mHeaderLayout;

    private LoadingLayout mFooterLayout;

    private State state = State.RESET;

    private Mode mode = Mode.getDefault();

    private Mode mCurrentMode;

    private boolean mIsBeingDragged = false;

    private boolean mShowViewWhileRefreshing = true;

    private boolean mScrollingWhileRefreshingEnabled = false;

    private boolean mFilterTouchEvents = true;

    private boolean mOverScrollEnabled = true;

    private boolean mLayoutVisibilityChangesEnabled = true;

    public T mRefreshableView;

    private FrameLayout mRefreshableViewWrapper;

    private OnRefreshListener<T> mOnRefreshListener;

    private OnPullEventListener<T> mOnPullEventListener;


    /** 构造方法 */
    public RefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    /** 初始化数据 */
    private void init(Context context, AttributeSet attrs) {

        /**设置布局垂直居中*/
        this.setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

        //获取参数信息*/
        TypedArray arrays = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);

        arrays.recycle();

    }


    /** 更新布局 */
    public void updateUIForMode() {

        final LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        if (this == mHeaderLayout.getParent()) {
            removeView(mHeaderLayout);
        }
        if (mode.showHeaderLoadingLayout()) {
            this.addView(mHeaderLayout, 0, lp);  //添加头布局
        }

        if (this == mFooterLayout.getParent()) {
            removeView(mFooterLayout);
        }
        if (mode.showFooterLoadingLayout()) {
            super.addView(mFooterLayout, -1, lp);
        }

        refreshLoadingViewsSize();

//        mCurrentMode = (mode != Mode.BOTH) ? mode : Mode.PULL_FROM_START;

    }


    /** 设置加载条布局的尺寸 */
    protected final void refreshLoadingViewsSize() {

        final int maximumPullScroll = (int) (getMaximumPullScroll() * 1.2f);

        int pLeft = getPaddingLeft();
        int pTop = getPaddingTop();
        int pRight = getPaddingRight();
        int pBottom = getPaddingBottom();

        if (mode.showHeaderLoadingLayout()) {
            mHeaderLayout.setHeight(maximumPullScroll);
            pTop = -maximumPullScroll;
        } else {
            pTop = 0;
        }

        if (mode.showFooterLoadingLayout()) {
            mFooterLayout.setHeight(maximumPullScroll);
            pBottom = -maximumPullScroll;
        } else {
            pBottom = 0;
        }

        setPadding(pLeft, pTop, pRight, pBottom);

    }

//
//        /** 实现viewGroup的方法 */
//        @Override
//        public boolean onInterceptTouchEvent(MotionEvent event) {
//
//            if (!isPullToRefreshEnabled()) {
//                return false;
//            }
//
//            final int action = event.getAction();
//
//            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//                mIsBeingDragged = false;
//                return false;
//            }
//
//            switch (action) {
//                case MotionEvent.ACTION_MOVE: {
//                    // If we're refreshing, and the flag is set. Eat all MOVE events
//                    if (!mScrollingWhileRefreshingEnabled && isRefreshing()) {
//                        return true;
//                    }
//
//                    if (isReadyForPull()) {
//                        final float y = event.getY(), x = event.getX();
//                        final float diff, oppositeDiff, absDiff;
//
//                        // We need to use the correct values, based on scroll
//                        // direction
//                        diff = y - mLastMotionY;
//                        oppositeDiff = x - mLastMotionX;
//
//                        absDiff = Math.abs(diff);
//
//                        if (absDiff > mTouchSlop && (!mFilterTouchEvents || absDiff > Math
//                                .abs(oppositeDiff))) {
//                            if (mode.showHeaderLoadingLayout() && diff >= 1f
//                                    && isReadyForPullStart()) {
//                                mLastMotionY = y;
//                                mLastMotionX = x;
//                                mIsBeingDragged = true;
//                                if (mode == Mode.BOTH) {
//                                    mCurrentMode = Mode.PULL_FROM_START;
//                                }
//                            } else if (mode.showFooterLoadingLayout() && diff <= -1f
//                                    && isReadyForPullEnd()) {
//                                mLastMotionY = y;
//                                mLastMotionX = x;
//                                mIsBeingDragged = true;
//                                if (mode == Mode.BOTH) {
//                                    mCurrentMode = Mode.PULL_FROM_END;
//                                }
//                            }
//                        }
//                    }
//                    break;
//                }
//                case MotionEvent.ACTION_DOWN: {
//                    if (isReadyForPull()) {
//                        mLastMotionY = mInitialMotionY = event.getY();
//                        mLastMotionX = mInitialMotionX = event.getX();
//                        mIsBeingDragged = false;
//                    }
//                    break;
//                }
//            }
//
//            return mIsBeingDragged;
//
//
//        }

//    private boolean isReadyForPull() {
//        switch (mMode) {
//            case PULL_FROM_START:
//                return isReadyForPullStart();
//            case PULL_FROM_END:
//                return isReadyForPullEnd();
//            case BOTH:
//                return isReadyForPullEnd() || isReadyForPullStart();
//            default:
//                return false;
//        }
//    }


    private int getMaximumPullScroll() {
        return Math.round(getHeight() / FRICTION);
    }


    @Override
    public Mode getCurrentMode() {
        return mCurrentMode;
    }

    @Override
    public final Mode getMode() {
        return mode;
    }


    @Override
    public void getState(State state) {

    }

    @Override
    public final boolean isPullToRefreshEnabled() {
        return mode.permitsPullToRefresh();
    }


    /** 自定义的Mode枚举 ====================================================================================== */
    public enum Mode {

        /** 所有效果全不可用 */
        Disabled(0x1),

        /** 只能下拉刷新 */
        PULL_FROM_START(0x2),

        /** 只能上拉加载 */
        PULL_FROM_END(0x3),

        /** 全部都可用 */
        BOTH(0x3),

        /** 手动刷新 */
        MANUAL_REFRESH_ONLY(0x5);


        private int value;

        Mode(int Tag) {
            value = Tag;

        }

        public static Mode mapIntToValue(final int modeInt) {
            for (Mode value : Mode.values()) {
                if (modeInt == value.getIntValue()) {
                    return value;
                }
            }

            // If not, return default
            return getDefault();
        }

        int getIntValue() {
            return value;
        }

        public static Mode getDefault() {
            return PULL_FROM_START;
        }

        boolean permitsPullToRefresh() {

            return (this != Disabled || this != MANUAL_REFRESH_ONLY);
        }

        public boolean showHeaderLoadingLayout() {
            return this == PULL_FROM_START || this == BOTH;
        }

        public boolean showFooterLoadingLayout() {
            return this == PULL_FROM_END || this == BOTH || this == MANUAL_REFRESH_ONLY;
        }

    }


    public static enum State {

        RESET(0x0),

        PULL_TO_REFRESH(0x1),

        RELEASE_TO_REFRESH(0x2),

        REFRESHING(0x8),

        MANUAL_REFRESHING(0x9),

        OVERSCROLLING(0x10);

        static State mapIntToValue(final int stateInt) {
            for (State value : State.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }

            return RESET;
        }

        private int mIntValue;

        State(int intValue) {
            mIntValue = intValue;
        }

        int getIntValue() {
            return mIntValue;
        }
    }


    /**
     * 下面是自定义的接口==============================================================================
     *
     * 底部滑动监听
     */

    public static interface OnLastItemVisibleListener {


        /** 滑到底部调用 */
        public void onLastItemVisible();

    }

    /** 事件监听 */
    public static interface OnPullEventListener<V extends View> {

        /**
         * 在用户进行滑动式发生改变
         *
         * @param refreshView View which has had it's state change.
         * @param state       The new state of View.
         */
        public void onPullEvent(final RefreshBase<V> refreshView, State state, Mode direction);

    }

    /** 刷新监听 */
    public static interface OnRefreshListener<V extends View> {

        /**
         * onPullDownToRefresh will be called only when the user has Pulled from
         * the start, and released.
         */
        public void onPullDownToRefresh(final RefreshBase<V> refreshView);

        /**
         * onPullUpToRefresh will be called only when the user has Pulled from
         * the end, and released.
         */
        public void onPullUpToRefresh(final RefreshBase<V> refreshView);

    }


}
