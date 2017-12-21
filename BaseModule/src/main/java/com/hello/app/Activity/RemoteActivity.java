package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.hello.app.Fragment.AnimMapFragment;
import com.hello.app.MyView.MyScrollView;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;
import com.hello.app.fragmentAnim.Stepper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RemoteActivity extends Activity implements MyScrollView.ScrollViewListener {

    @InjectView(R.id.container)
    public LinearLayout mContainer;

    @InjectView(R.id.button_go_down)
    public Button mGoDown;

    @InjectView(R.id.lin_list)
    public LinearLayout mLinList;

    @InjectView(R.id.map_view)
    public TextureMapView mMapView;

    @InjectView(R.id.common_title)
    public LinearLayout mTitle;

    @InjectView(R.id.scrollView_)
    public MyScrollView mScrollView;

    Scroller mScroller;

    float mYScroll;

    float mScrollerLastY;

    float screenW = 1080;

    float screenH = 1920;

    float viewH;

    int firstY;

    private AnimMapFragment mMapFragment;

    private Stepper mStepper;

    private MyRunnable mRunnable;

    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        ButterKnife.inject(this);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }

    private void init() {
        mScroller = new Scroller(this);
        mStepper = new Stepper(mContainer, new MyRunnable());
        mBaiduMap = mMapView.getMap();
        mScrollView.setScrollViewListener(this);
        viewH = ViewUtil.dip2px(this, 200);
        //初始化地图
        mMapFragment = new AnimMapFragment(this);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.container, mMapFragment);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.button_go_down)
    public void goDown() {

        if (mScroller.isFinished()) {
//            viewH = mContainer.getMeasuredHeight();

            int offsetY = (int) (screenH - viewH);
            mScroller.startScroll(0, 0, 0, offsetY * 3 / 4, 2000);
            mStepper.prod();
        }


    }

    @OnClick(R.id.button_go_up)
    public void goUp() {

////        mContainer.setScrollY(a);
//        mContainer.setTranslationY(a);
//        mLinList.setTranslationY(a);
//        a+=10f;

        if (mScroller.isFinished()) {

            int offsetY = (int) (screenH - mContainer.getMeasuredHeight());
            mScroller.startScroll(0, 0, 0, -(offsetY * 3 / 4), 800);
            mStepper.prod();
        }
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {

        Log.i("MyRunnable", "onScrollChanged  y=" + y + "    oldy=" + oldy);
        if (y <= viewH) {
            float scale = y / viewH;
            mTitle.setAlpha(scale);
        } else {
            mTitle.setAlpha(1);
        }


    }

    private void goTranslationY(ViewGroup layout, int offset) {

        ViewGroup.LayoutParams lp = layout.getLayoutParams();
        lp.height = (int) (viewH + offset);
        layout.setLayoutParams(lp);

    }

    private void mapScrollY(int x, int y) {
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.scrollBy(x, y));

    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {

            if (mScroller.isFinished()) {
//                mapScrollY(0, (int) mYScroll / 2);
                mScrollerLastY = mYScroll;

            } else {
                mScroller.computeScrollOffset();
                int y = mScroller.getCurrY();
                mYScroll = mScrollerLastY + y;

                float scale = 4 * y / mScroller.getFinalY();

                Log.i("MyRunnable", "CurrY=" + mScroller.getCurrY() + "    mYScroll=" + mYScroll);
//
                mMapView.setTranslationY(mYScroll);
                mLinList.setTranslationY(mYScroll);

//                goTranslationY(mContainer,y);
//                goTranslationY(mLinList,y);
                mStepper.prod();

            }
        }
    }


}
