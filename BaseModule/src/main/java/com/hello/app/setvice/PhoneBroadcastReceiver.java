package com.hello.app.setvice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class PhoneBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out
                .println(">>PhoneBroadcastReceiver>>" + Environment.getExternalStorageDirectory());
        Intent service = new Intent(context, PhoneService.class);
        context.startService(service);   //启动服务
    }

} 
