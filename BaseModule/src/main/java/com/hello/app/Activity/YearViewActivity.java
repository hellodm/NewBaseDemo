package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hello.app.Fragment.MyFragment;
import com.hello.app.MyView.CalendarYearView;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class YearViewActivity extends Activity implements View.OnTouchListener {

    @InjectView(R.id.list_year)
    public ListView mListView;

    @InjectView(R.id.lin_all)
    public View mLinAll;

    @InjectView(R.id.lin_click)
    public View mLinClick;

    public float mX;

    public float mY;

    Timer mTimer = new Timer();

    int a = 0;

    int dur = 1000;

    int k = 1000 / 10;

    private MyListAdapter mMyListAdapter;

    private ViewUtil.ScreenSize screen;

    private float w;

    private float h;

    private int xx;

    private int yy;

    private PopupWindow mPopupWindow;

    private View layout;

    private MyFragment mFragment;

    private FragmentManager mFragmentManager;

    private FragmentTransaction mTransaction;

    /** handler更新动画 */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            a = a + 1;
            int x_off = (int) (mX + a * (250 - mX) / k);
            int y_off = (int) (mY + a * (600 - mY) / k);
            mPopupWindow.update(x_off, y_off, 600, 600);

        }
    };

    TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(41);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_view);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        screen = ViewUtil.getWindowScreen(ViewUtil.WINDOW_PX, this);
        layout = LayoutInflater.from(this).inflate(R.layout.pop_window, null);
//            mPopupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT, false);
        mPopupWindow = new PopupWindow(layout, (int) (screen.width / 2),
                (int) (screen.height / 2), false);
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setClippingEnabled(false);

        mMyListAdapter = new MyListAdapter(this);
        mListView.setAdapter(mMyListAdapter);

        w = (screen.width - 4 * ViewUtil.dip2px(this, 15));
        w = w / 3;
        h = (w / CalendarYearView.scale);

        //初始化fragment
        mFragmentManager = getFragmentManager();
        mFragment = MyFragment.newInstance(Color.RED);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("onTouch", "x==" + (int) event.getX() + "y==" + (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            xx = (int) event.getX();
            yy = (int) event.getY();

        }
        return false;
    }

    @OnClick(R.id.lin_click)
    public void goAnim() {

        int x_type = Animation.ABSOLUTE;
        int y_type = Animation.ABSOLUTE;
        TranslateAnimation animation = new TranslateAnimation(x_type, 0, x_type,
                250, y_type, 0, y_type, 600);
        animation.setDuration(1000);
        mLinClick.startAnimation(animation);
    }

    private class MyListAdapter extends BaseAdapter {

        private Context mContext;

        private GridHolder mHolder;


        public MyListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                mHolder = new GridHolder(mContext);
                convertView = mHolder.getView();
            } else {
                mHolder = (GridHolder) convertView.getTag();
            }

            return convertView;
        }
    }

    public class GridHolder implements AdapterView.OnItemClickListener {

        @InjectView(R.id.text_year)
        public TextView textYear;

        @InjectView(R.id.text_values)
        public TextView textValues;

        @InjectView(R.id.grid_year)
        public GridView mGridView;

        /** **************************************显示windowManager*************************************************** */
        WindowManager mManager;

        WindowManager.LayoutParams mLayoutParams = null;

        private Context mContext;

        private View convertView;

        private LayoutInflater mInflater;

        private MyAdapter mAdapter;

        private int w_pop;

        private int h_pop;

        private boolean isView = false;

        public GridHolder(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mAdapter = new MyAdapter(mContext);


        }

        public View getView() {

            convertView = mInflater.inflate(R.layout.item_year_view, null);
            ButterKnife.inject(this, convertView);
            mGridView.setLayoutParams(
                    new LinearLayout.LayoutParams((int) screen.width, (int) h * 4 + 10));
            mGridView.setAdapter(mAdapter);
            mGridView.setOnTouchListener(YearViewActivity.this);
            mGridView.setOnItemClickListener(this);
            convertView.setTag(this);

            return convertView;
        }

        /** gridview的点击监听 */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Log.i("location", "x==" + location[0] + "y==" + location[1]);
            Log.i("view", "x==" + view.getWidth() + "y==" + view.getHeight());
            //计算偏移量
//            getAnimation(view, location[0], location[1]);
//            show(parent, xx, yy);
//            showWindow(xx, yy);
//            mTimer.schedule(mTask, 1,40);
//            showFragment(xx - 130, yy);
            showFragment(view.getWidth(), view.getHeight(), location[0], location[1]);

        }

        /** ******************************************显示FragmentDo动画*************************************************** */

        private void showFragment(float width, float height, float x, float y) {
            mFragment.setPosition(width, height, x + width / 2 - 540, y + height / 2 - 1000, 0, 0);
            mTransaction = mFragmentManager.beginTransaction();
            mTransaction.replace(R.id.lin_frag, mFragment);
            mTransaction.commit();
        }

        /** 显示windowManager */
        private void showWindow(int x, int y) {

            if (mManager == null) {
                mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            }
            if (isView) {
                mManager.removeView(layout);
            }

            mLayoutParams = new WindowManager.LayoutParams();
//            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            mX = mLayoutParams.x = x - layout.getWidth() / 2;
            mY = mLayoutParams.y = y;
            mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mLayoutParams.format = PixelFormat.RGBA_8888;

            mManager.addView(layout, mLayoutParams);
            isView = true;

//            mLayoutParams.x = 300;
//            mLayoutParams.y = 600;
//            mManager.updateViewLayout(layout, mLayoutParams);

        }


        /** **************************************显示PopUpWindow*************************************************** */

        private void show(ViewGroup viewGroup, int x, int y) {
            Log.i("显示pop", "x==" + x + "y==" + y);

            int[] location = new int[2];
            viewGroup.getLocationOnScreen(location);

            //设置偏移量
            //填充layout并且计算布局尺寸
//            setContentView(layout);
//            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            w_pop = layout.getMeasuredWidth();
//            h_pop = layout.getMeasuredHeight();
            //计算偏移量
            x = location[0] + x - mPopupWindow.getWidth() / 2;
            y = location[1] + y - mPopupWindow.getHeight() / 2;

            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                // 显示在Activity的根视图中心
                mPopupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, x, y);
            } else {
                mPopupWindow.dismiss();
            }
        }

        /** 设置偏移动画 */
        private void getAnimation(View view, float x, float y) {
            int x_type = Animation.ABSOLUTE;
            int y_type = Animation.ABSOLUTE;

            TranslateAnimation mTranslate = new TranslateAnimation(x_type, 0, x_type,
                    540 - x - view.getWidth() / 2, y_type, 0, y_type,
                    960 - y - view.getHeight() / 2);
            mTranslate.setDuration(1000);

            ScaleAnimation mScale = new ScaleAnimation(1f, 4f, 1f, 4f, Animation.ABSOLUTE, 0.5f,
                    Animation.ABSOLUTE, 0.5f);
//            mScale.setStartOffset(1000);
            mScale.setDuration(1000);

            AnimationSet set = new AnimationSet(mContext, null);
//            set.addAnimation(mTranslate);
            set.addAnimation(mScale);

            view.startAnimation(set);

        }


        private class MyAdapter extends BaseAdapter {

            private Context mContext;


            public MyAdapter(Context context) {
                mContext = context;

            }


            @Override
            public int getCount() {
                return 12;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = new CalendarYearView(mContext, null);
//                    convertView.setOnTouchListener(YearViewActivity.this);
                    convertView.setLayoutParams(new AbsListView.LayoutParams((int) w, (int) h));
                } else {

                }

                return convertView;
            }
        }

    }


}