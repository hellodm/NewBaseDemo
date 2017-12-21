package com.hello.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hello.app.MyView.GravityView;
import com.hello.app.R;

import butterknife.InjectView;


/** 模拟重力小球下落上弹得Activity */
public class GravityActivity extends Activity {

    @InjectView(R.id.gravity)
    GravityView gravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_activity);
        getSl();


    }


    private void getSl() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
