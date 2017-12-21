package com.hello.app.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hello.app.MyView.CalendarYearView;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class YearFragment extends Fragment {

    private static final String YEAR_PARAM = "year";

    private static final String MONTH_PARAM = "month";

    @InjectView(R.id.list_year)
    public ListView mListView;

    private String year;

    private String month;

    private OnFragmentListener mListener;

    private MyListAdapter mMyAdapter;


    private ViewUtil.ScreenSize screen;

    private float w;

    private float h;

    private int xx;

    private int yy;

    private PopupWindow mPopupWindow;

    private View layout;


    private Context mContext;


    public static YearFragment newInstance(String year, String month) {
        YearFragment fragment = new YearFragment();
        Bundle args = new Bundle();
        args.putString(YEAR_PARAM, year);
        args.putString(MONTH_PARAM, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getString(YEAR_PARAM);
            month = getArguments().getString(MONTH_PARAM);
        }

        mMyAdapter = new MyListAdapter(this.getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_year, container, false);
        ButterKnife.inject(this, convertView);
        mContext = getActivity();
        init();
        return convertView;
    }


    /** 初始化 */
    private void init() {
        screen = ViewUtil.getWindowScreen(ViewUtil.WINDOW_PX, this.getActivity());
        layout = LayoutInflater.from(this.getActivity()).inflate(R.layout.pop_window, null);
        mPopupWindow = new PopupWindow(layout, (int) (screen.width / 2),
                (int) (screen.height / 2), false);
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setClippingEnabled(false);
        mMyAdapter = new MyListAdapter(mContext);
        mListView.setAdapter(mMyAdapter);

        w = (screen.width - 4 * ViewUtil.dip2px(mContext, 15));
        w = w / 3;
        h = (w / CalendarYearView.scale);
        mListView.setAdapter(mMyAdapter);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentListener {

        public void onFragment(Uri uri);
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


        private Context mContext;

        private View convertView;

        private LayoutInflater mInflater;

        private MyGridAdapter mAdapter;


        public GridHolder(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mAdapter = new MyGridAdapter(mContext);


        }

        public View getView() {

            convertView = mInflater.inflate(R.layout.item_year_view, null);
            ButterKnife.inject(this, convertView);
            mGridView.setLayoutParams(
                    new LinearLayout.LayoutParams((int) screen.width, (int) h * 4 + 10));
            mGridView.setAdapter(mAdapter);
            mGridView.setOnItemClickListener(this);
            convertView.setTag(this);

            return convertView;
        }


        /** gridview的点击监听 */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            show(xx, yy);
        }


        /**
         * 显示pop
         */
        public void show(int x, int y) {
            Log.i("显示pop", "x==" + x + "y==" + y);

            //设置偏移量
            int off_x = (int) (screen.width / 2.0);
            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                // 显示在Activity的根视图中心
                mPopupWindow.showAtLocation(
                        YearFragment.this.getActivity().getWindow().peekDecorView(),
                        Gravity.NO_GRAVITY, 0, 0);
            } else {
                mPopupWindow.dismiss();
            }
        }


        private class MyGridAdapter extends BaseAdapter {

            private Context mContext;


            public MyGridAdapter(Context context) {
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
                    convertView.setLayoutParams(new AbsListView.LayoutParams((int) w, (int) h));
                } else {

                }

                return convertView;
            }
        }

    }

}
