package com.hello.app.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hello.app.Activity.Message;
import com.hello.app.Activity.NotifyDialogUtil;

public class DialogNotifyService extends Service {


    private float mTouchStartX;

    private float mTouchStartY;


    public DialogNotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("SuspendService", "onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("SuspendService", "onCreate()");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SuspendService", "onStartCommand()");
//        NotifyDialogUtil.getInstance().putNotifyMessage(this);
        Message message = new Message();
        message.instruct = "C0001";
        message.state = 1;
        NotifyDialogUtil.getInstance(getBaseContext()).putNotifyMessage(message);
        NotifyDialogUtil.getInstance(getBaseContext()).putNotifyMessage(message);
        NotifyDialogUtil.getInstance(getBaseContext()).showDialog();
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onRebind(Intent intent) {
        Log.i("SuspendService", "onRebind()");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("SuspendService", "onDestroy()");
        super.onDestroy();
    }


}


