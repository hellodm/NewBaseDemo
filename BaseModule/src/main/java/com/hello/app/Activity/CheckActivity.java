package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hello.app.MyView.CheckSurfaceView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CheckActivity extends Activity {

    @InjectView(R.id.checkView)
    CheckSurfaceView checkView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ButterKnife.inject(this);

    }


    @OnClick(R.id.button)
    public void go() {
        Log.i("go()", checkView.getRefresh() + "");
        checkView.setisRefresh(checkView.getRefresh() ? false : true);

    }
}
