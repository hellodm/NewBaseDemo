package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.hello.app.Fragment.YearFragment;
import com.hello.app.MyView.CalendarYearView;
import com.hello.app.MyView.MedalView;
import com.hello.app.R;
import com.hello.app.Util.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 测试枚举的Activity
 */
public class EnumActivity extends Activity implements YearFragment.OnFragmentListener {

    @InjectView(R.id.text)
    TextView textView;

    @InjectView(R.id.button_red)
    Button btn_red;

    @InjectView(R.id.button_green)
    Button btn_green;

    @InjectView(R.id.button_blue)
    Button btn_blue;

    @InjectView(R.id.grid_year)
    GridView grid;

    @InjectView(R.id.grid1)
    GridView grid1;

    @InjectView(R.id.lin_frag)
    View linFrag;

    @InjectView(R.id.medalView)
    MedalView mMedalView;


    YearFragment mFragment;

    FragmentManager mFragmentManager;

    FragmentTransaction mTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enum);

        ButterKnife.inject(this);

        btn_red.setTag(colors.RED);
        btn_green.setTag(colors.GREEN);
        btn_blue.setTag(colors.BLUE);

        for (MyEnum e : MyEnum.values()) {

            Util.logStr("测试枚举", e.getName() + "===" + e.getColor() + "===" + e.getIndex());

        }

        grid.setAdapter(new MyAdapter(this));
        grid1.setAdapter(new MyAdapter1(this));

        mFragment = YearFragment.newInstance("1989", "12");
        mFragmentManager = this.getFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.add(R.id.lin_frag, mFragment);
        mTransaction.commit();


    }


    @OnClick(R.id.button_red)
    public void pressRed(Button btn) {
        mMedalView.setData(
                BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_medal));
        switchColor((colors) btn.getTag());

    }

    @OnClick(R.id.button_green)
    public void pressGreen(Button btn) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_medal);
        mMedalView.setData(bitmap, bitmap, bitmap, bitmap);
        switchColor((colors) btn.getTag());

    }

    @OnClick(R.id.button_blue)
    public void pressBlue(Button btn) {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_medal);
        mMedalView.setData(bitmap, bitmap, bitmap);
        switchColor((colors) btn.getTag());

    }

    private void switchColor(colors color) {

        switch (color) {

            case RED:
                textView.setTextColor(Color.RED);
                textView.setText("这是红色");

                break;
            case GREEN:
                textView.setTextColor(Color.GREEN);
                textView.setText("这是绿色");
                break;
            case BLUE:
                textView.setTextColor(Color.BLUE);
                textView.setText("这是蓝色");
                break;


        }


    }

    @Override
    public void onFragment(Uri uri) {

    }


    /**
     * 颜色枚举--enum起始是个类
     */
    public enum colors {
        RED, GREEN, BLUE
    }


    /**
     * 新建一个枚举类zhong
     */

    private enum MyEnum {

        /**
         * 括号里的选项和构造方法是相对应的,要是自定义方法必须在enum实例后面添加分号;
         */
        RED("红色", 1, Color.RED), GREEN("绿色", 2, Color.GREEN), BLUE("蓝色", 3, Color.BLUE);


        /**
         * 属性
         */

        private String name;

        private int index;

        private int color;

        //构造方法
        MyEnum(String name, int index, int color) {

            this.name = name;
            this.index = index;
            this.color = color;

        }


        /**
         * 自定义的方法
         */
        private String getStringName(int index) {

            /**枚举enum也可以for循环遍历*/

            for (MyEnum e : MyEnum.values()) {

                if (e.getIndex() == index) {

                    return e.getName();
                }


            }

            return null;


        }


        //get.set方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

    }

    class MyAdapter1 extends BaseAdapter {

        Context mContext;

        MyAdapter1(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 13;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = new CalendarYearView(mContext, null);
                convertView.setLayoutParams(new AbsListView.LayoutParams(165, 165));
            }

            return convertView;
        }
    }


    class MyAdapter extends BaseAdapter {

        Context mContext;

        MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 500;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = new CalendarYearView(mContext, null);
                convertView.setLayoutParams(new AbsListView.LayoutParams(360, 360));
            }

            return convertView;
        }
    }


}
