package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hello.app.MyView.AuthCodeView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ScratchActivity extends Activity {

    @InjectView(R.id.authCode)
    public AuthCodeView mAuthCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.text_again)
    public void goRefresh() {
        mAuthCodeView.refresh();
    }


}
