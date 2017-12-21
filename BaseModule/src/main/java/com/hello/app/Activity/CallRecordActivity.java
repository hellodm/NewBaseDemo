package com.hello.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import com.hello.app.R;
import com.hello.app.setvice.PhoneService;


public class CallRecordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_record);

        Intent service = new Intent(this, PhoneService.class);
        startService(service); // 启动服务
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent service = new Intent(this, PhoneService.class);
        stopService(service);
    }
}
