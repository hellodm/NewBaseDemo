package com.hello.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.app.MyView.ShakeView;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ShakeFragment extends Fragment implements ShakeView.OnShakeListener {

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";

    @InjectView(R.id.gridView_shake)
    public GridView mGridView;

    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MyAdapter mAdapter;

    private ScreenSize screen;


    public ShakeFragment() {
    }

    public static ShakeFragment newInstance(String param1, String param2) {
        ShakeFragment fragment = new ShakeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shake, null);
        ButterKnife.inject(this, view);

        initView();
        return view;
    }


    /** 初始化Data */
    private void initData() {
        screen = getWindowScreen(this.getActivity());
        mAdapter = new MyAdapter(this.getActivity());

    }

    /** 初始化View数据 */
    private void initView() {
        mGridView.setVerticalSpacing(50);
        mGridView.setAdapter(mAdapter);

    }


    /** 初始化位置 */
    private void initSize() {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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

    /**
     * 获取屏幕尺寸
     */
    public ScreenSize getWindowScreen(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float w = metrics.widthPixels; // 屏幕宽度（像素）
        float h = metrics.heightPixels; // 屏幕高度（像素）
        return new ScreenSize(w, h);

    }


    /** shakeView的监听 */
    @Override
    public void notifyShake(int position) {

    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

    /** 内置的grid的Adapter */

    public class MyAdapter extends BaseAdapter {

        private Context mContext;

        private LayoutInflater mInflater;

        private float w;

        private float h;


        public MyAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ShakeView shakeView = new ShakeView(mContext, null);
            shakeView.setLayoutParams(
                    new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
            shakeView.setImageResource(R.drawable.about_logo);
            shakeView.setOnShakeListener(ShakeFragment.this, position);
            convertView = shakeView;
//
            return convertView;
        }


        /** 计算单个grid的尺寸 */
        private void calculateSize(ScreenSize screen) {

            if (screen == null) {
                return;
            }

        }


    }

    /**
     * 屏幕尺寸
     */
    public class ScreenSize {

        public float width;

        public float height;

        public ScreenSize(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }

}
