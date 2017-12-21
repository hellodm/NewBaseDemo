package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.TextureMapView;
import com.hello.app.Fragment.AnimMapFragment;
import com.hello.app.MyView.recyclerView.ItemTouchHelperCallback;
import com.hello.app.MyView.recyclerView.MyRecycleView;
import com.hello.app.MyView.recyclerView.MyRecyclerAdapter;
import com.hello.app.MyView.recyclerView.RecyclerViewHeader;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;
import com.hello.app.fragmentAnim.Stepper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RemoteCopyActivity extends Activity implements MyRecycleView.ScrollViewListener {


    @InjectView(R.id.common_title)
    public RelativeLayout mTitle;

    @InjectView(R.id.recycler_view)
    public MyRecycleView mRecycleView;


    MapView mMapView;
//    LinearLayout mLayout;

    Scroller mScroller;

    float mYScroll;

    float mScrollerLastY;

    float mScrollerLastY1;

    float screenW = 1080;

    float screenH = 1920;

    float viewH;

    int firstY;

    ItemTouchHelperCallback mCallBack;

    Bitmap mCache;

    RecyclerViewHeader mViewHeader;

    private AnimMapFragment mMapFragment;

    private FragmentManager fm;

    private Stepper mStepper;

    private MyRunnable mRunnable;

    private BaiduMap mBaiduMap;

    private MyRecyclerAdapter mAdapter;

    private List<String> mList = new ArrayList<>();

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  //根据字符串的长度显示view的宽度
        view.destroyDrawingCache();
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        ButterKnife.inject(this);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
//      mHeadView.setDrawingCacheEnabled(true);
//      mCache = mHeadView.getDrawingCache();
//      mCache=  Bitmap.createBitmap(mCache);
//      container.setImageBitmap(mCache);
//      container.setVisibility(View.VISIBLE);
//      mHeadView.setVisibility(View.GONE);
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
//        container.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                container.setVisibility(View.GONE);
//            }
//        },20);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }

    private void init() {
        viewH = ViewUtil.dip2px(this, 200);
        mRecycleView.setHasFixedSize(true);
//        mRecycleView.setLayoutManager(new FullyLinearLayoutManager(this));
//        mRecycleView.setLayoutManager(new GridLayoutManager(this,3));
        mRecycleView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleView.setScrollViewListener(this);

        mViewHeader = RecyclerViewHeader.fromXml(this, R.layout.item_map);
        mMapView = (MapView) mViewHeader.findViewById(R.id.map_view);
//        mLayout = (LinearLayout) mViewHeader.getChildAt(0);
        mBaiduMap = mMapView.getMap();
        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDown();
            }
        });
        mScroller = new Scroller(this);
        mStepper = new Stepper(mRecycleView, new MyRunnable());

        for (int i = 0; i < 20; i++) {
            mList.add("test==" + i);
        }

        mAdapter = new MyRecyclerAdapter(mList);
        mViewHeader.attachTo(mRecycleView);
        mRecycleView.setAdapter(mAdapter);
        mCallBack = new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(mCallBack);
//        touchHelper.attachToRecyclerView(mRecycleView);

    }

    @OnClick(R.id.button_go_down)
    public void goDown() {

        if (mScroller.isFinished()) {
//            viewH = mContainer.getMeasuredHeight();

            int offsetY = (int) (screenH - viewH);
            mScroller.startScroll(0, 0, 0, offsetY, 2000);
            mStepper.prod();
//            mapScrollY(0, (int) ((screenH-viewH)/2));
        }
//        container.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.button_go_up)
    public void goUp() {

////        mContainer.setScrollY(a);
//        mContainer.setTranslationY(a);
//        mLinList.setTranslationY(a);
//        a+=10f;

        if (mScroller.isFinished()) {

            int offsetY = (int) (screenH - viewH);
            mScroller.startScroll(0, 0, 0, -(offsetY), 1000);
            mStepper.prod();
        }
    }


    @Override
    public void onScrollChanged(MyRecycleView recycleView, int x, int y, int oldx, int oldy) {

        Log.i("MyRunnable", "onScrollChanged  y=" + y + "    oldy=" + oldy);
        if (y <= viewH) {
            float scale = y / viewH;
//            mTitle.setAlpha(scale);
            mTitle.setBackgroundColor(Color.argb((int) (255 * scale), 74, 168, 168));
        } else {
            mTitle.setAlpha(1);
        }


    }

    private void goTranslationY(View layout, float offset) {

        ViewGroup.LayoutParams lp = layout.getLayoutParams();
        lp.height = (int) (viewH + offset);
        layout.setLayoutParams(lp);

    }

    private void mapScrollY(int x, int y) {
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.scrollBy(x, y), 1000);
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
//                mHeadView.setTranslationY(mYScroll);
//                goTranslationY(mHeadView,mYScroll-mScrollerLastY1);
//                mRecycleView.setTranslationY(mYScroll);

//                mMapView.setTranslationY(mYScroll);
                mViewHeader.setTranslationY(mYScroll);
                mRecycleView.setTranslationY(mYScroll);
//                int size = mRecycleView.getChildCount();
//                for (int i = 0; i < size; i++) {
//                    View view = mRecycleView.getChildAt(i);
//                    view.setTranslationY(mYScroll);
//                }
//                container.setTranslationY(mYScroll);

                mStepper.prod();

                mScrollerLastY1 = mYScroll;

            }
        }
    }


}
