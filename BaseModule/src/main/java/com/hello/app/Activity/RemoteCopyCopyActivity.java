package com.hello.app.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.hello.app.MyView.recyclerView.HomeMapTitleLayout;
import com.hello.app.MyView.recyclerView.ItemTouchHelperCallback;
import com.hello.app.MyView.recyclerView.MyRecycleView;
import com.hello.app.MyView.recyclerView.MyRecyclerAdapter;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;
import com.hello.app.fragmentAnim.Stepper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RemoteCopyCopyActivity extends Activity
        implements MyRecycleView.ScrollViewListener, HomeMapTitleLayout.OnTitleClickListener {


    @InjectView(R.id.common_title)
    public RelativeLayout mTitle;


    @InjectView(R.id.map_view_mask)
    public ImageView mImageView;

    @InjectView(R.id.home_map_title)
    public HomeMapTitleLayout mMapTitleLayout;

    @InjectView(R.id.recycler_view)
    public MyRecycleView mRecycleView;

    @InjectView(R.id.container)
    public FrameLayout container;

    @InjectView(R.id.map_view)
    TextureMapView mMapView;

    LinearLayoutManager mLayoutManager;

    Scroller mScroller;

    float mYScroll;

    float mScrollerLastY;

    float mScrollerLastY1;

    float screenW = 1080;

    float screenH = 1920;

    float viewH;

    int firstY;

    ItemTouchHelperCallback mCallBack;

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
        setContentView(R.layout.activity_recycle_copy);
        ButterKnife.inject(this);
        init();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void jump() {

        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();

        List<Pair<View, String>> pairs = new ArrayList<Pair<View, String>>();
        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
            RecyclerView.ViewHolder holderForAdapterPosition
                    = (RecyclerView.ViewHolder) mRecycleView
                    .findViewHolderForAdapterPosition(i);
            View itemView = holderForAdapterPosition.itemView;
            pairs.add(Pair.create(itemView, "unique_key_" + i));
        }
        Bundle bundle = ActivityOptions
                .makeSceneTransitionAnimation(this, pairs.toArray(new Pair[]{}))
                .toBundle();
        startActivity(getIntent(), bundle);

    }

    @Override
    protected void onPause() {
        mImageView.setVisibility(View.VISIBLE);
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.getMap().snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                mMapView.onPause();
                if (bitmap != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
//
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.setVisibility(View.GONE);
            }
        }, 50);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }

    private void init() {
        mMapTitleLayout.setBacFade(true);
        mMapTitleLayout.setOnTitleClickListener(this);
        viewH = ViewUtil.dip2px(this, 200);

        mBaiduMap = mMapView.getMap();

        mScroller = new Scroller(this);
        mStepper = new Stepper(mRecycleView, new MyRunnable());

        for (int i = 0; i < 20; i++) {
            mList.add("test==" + i);
        }

        mRecycleView.setHasFixedSize(true);
//        mRecycleView.setLayoutManager(new FullyLinearLayoutManager(this));
//        mRecycleView.setLayoutManager(new GridLayoutManager(this,3));
        mRecycleView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleView.setScrollViewListener(this);

        mRecycleView.addHeader(mMapView);
        mAdapter = new MyRecyclerAdapter(mList);
        mRecycleView.setAdapter(mAdapter);
        mCallBack = new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(mCallBack);
        touchHelper.attachToRecyclerView(mRecycleView);

        mMapTitleLayout.setCars();
        mMapTitleLayout.updateTitle();

    }
//
//    @OnClick(R.id.button_go_down)
//    public void goDown() {
//
//        if (mScroller.isFinished()) {
////            viewH = mContainer.getMeasuredHeight();
//
//            int offsetY = (int) (screenH - viewH);
//            mScroller.startScroll(0, 0, 0, offsetY, 2000);
//            mStepper.prod();
////            mapScrollY(0, (int) ((screenH-viewH)/2));
//        }
//
//
//
//    }
//
//    @OnClick(R.id.button_go_up)
//    public void goUp() {
//
//////        mContainer.setScrollY(a);
////        mContainer.setTranslationY(a);
////        mLinList.setTranslationY(a);
////        a+=10f;
//
//        if (mScroller.isFinished()) {
//
//            int offsetY = (int) (screenH - viewH);
//            mScroller.startScroll(0, 0, 0, -(offsetY), 1000);
//            mStepper.prod();
//        }
//    }


    @Override
    public void onScrollChanged(MyRecycleView recycleView, int x, int y, int oldx, int oldy) {

        Log.i("MyRunnable", "onScrollChanged  y=" + y + "    oldy=" + oldy);
        if (y <= viewH) {
            float scale = y / viewH;
//            mTitle.setAlpha(scale);
            mTitle.setBackgroundColor(Color.argb((int) (255 * scale), 74, 168, 168));
            mMapTitleLayout.setBackgroundScale(scale);
        } else {
            mTitle.setAlpha(1);
            mMapTitleLayout.setAlpha(1);

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

    @Override
    public void goDown() {
        if (mScroller.isFinished()) {
//            viewH = mContainer.getMeasuredHeight();

            int offsetY = (int) (screenH - viewH);
            mScroller.startScroll(0, 0, 0, offsetY, 1000);
            mStepper.prod();
//            mapScrollY(0, (int) ((screenH-viewH)/2));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void goUp() {
//        if (mScroller.isFinished()) {
////
//            int offsetY = (int) (screenH - viewH);
//            mScroller.startScroll(0, 0, 0, -(offsetY), 1000);
//            mStepper.prod();
//        }

        jump();

    }

    @Override
    public void clickCar(String carId) {

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

                mMapView.setTranslationY(mYScroll);
                mImageView.setTranslationY(mYScroll);
//                mViewHeader.setTranslationY(mYScroll);
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
