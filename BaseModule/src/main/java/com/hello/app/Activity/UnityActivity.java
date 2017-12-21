package com.hello.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hello.app.R;

public class UnityActivity extends Activity {


    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }


    public void StartActivity0(String name) {
        Intent intent = new Intent(mContext, CheckActivity.class);
        intent.putExtra("CheckActivity", name);
        this.startActivity(intent);
    }

    public void StartActivity1(String name) {
        Intent intent = new Intent(mContext, SunSetActivity.class);
        intent.putExtra("SunSetActivity", name);
        this.startActivity(intent);
    }


}
