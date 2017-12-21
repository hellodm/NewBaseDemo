package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBroadcastActivity extends Activity {


    //    @InjectView(R.id.text_info)
    TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commca);

        ButterKnife.inject(this);

        textInfo.setText("保存吗");

    }


    @OnClick(R.id.btn_send)
    public void sendBroadcast() {

    }


}
