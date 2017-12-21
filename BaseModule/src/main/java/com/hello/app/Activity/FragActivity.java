package com.hello.app.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hello.app.Fragment.MyFragment;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FragActivity extends Activity implements MyFragment.OnFragmentInteractionListener {

    MyFragment mFragment;

    FragmentManager mFragmentManager;

    FragmentTransaction mTransaction;

    MyFragment myFragment;

    MyFragment myFragment1;

    MyFragment myFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        ButterKnife.inject(this);
        mFragment = (MyFragment) getFragmentManager().findFragmentById(
                R.id.myFragment);
        myFragment = MyFragment.newInstance(Color.RED);
        myFragment1 = MyFragment.newInstance(Color.BLUE);
        myFragment2 = MyFragment.newInstance(Color.GREEN);
        mFragmentManager = getFragmentManager();


    }


    @OnClick(R.id.button_click1)
    public void goButton1() {

        myFragment.setPosition(0, 0, -400, -650, 0, 0);
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.lin_frag, myFragment);
        mTransaction.commit();
    }


    @OnClick(R.id.button_click2)
    public void goButton2() {

        myFragment1.setPosition(0, 0, 0, -650, 0, 0);
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.lin_frag, myFragment1);
        mTransaction.commit();
    }

    @OnClick(R.id.button_click3)
    public void goButton3() {

        myFragment2.setPosition(0, 0, 400, -650, 0, 0);
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.replace(R.id.lin_frag, myFragment2);
        mTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}




