package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.hello.app.MyView.SunSetView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SunSetActivity extends Activity {

    @InjectView(R.id.sunSet)
    public SunSetView sunSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_set);
        ButterKnife.inject(this);
    }


    @OnClick(R.id.button_sun)
    public void goReset() {
        sunSet.reset();

    }
}
