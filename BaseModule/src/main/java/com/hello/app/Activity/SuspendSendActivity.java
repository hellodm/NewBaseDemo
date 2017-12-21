package com.hello.app.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.hello.app.Brodcast.SuspendReceiver;
import com.hello.app.R;
import com.hello.app.setvice.MyService;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuspendSendActivity extends Activity {

//    SuspendReceiver mSuspendReceiver;

    final static String ACTION_LOCK = "android.myapplication.lock";

    final static String ACTION_BANNER = "android.myapplication.banner";

    final static String ACTION_DESKTOP = "android.myapplication.desktop";

    final static String ACTION_FIXED = "android.myapplication.fixed";

    final static String ACTION_DYNAMIC = "android.myapplication.dynamic";

    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suspend_send);
        ButterKnife.inject(this);
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mSuspendReceiver = new SuspendReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ACTION_LOCK);
//        filter.addAction(ACTION_DESKTOP);
//        filter.addAction(ACTION_FIXED);
//        filter.addAction(ACTION_DYNAMIC);
//        registerReceiver(mSuspendReceiver, filter);
    }


    @OnClick(R.id.button_send_lock)
    public void sendLock() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(ACTION_LOCK);
                sendBroadcast(intent);
            }
        }, 2000);


    }


    private PendingIntent createAlarmIntent() {
        final Intent i = new Intent();
        i.setClass(this, SuspendReceiver.class);
        i.setAction(SuspendReceiver.ACTION_DESKTOP);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return pi;
    }

    @OnClick(R.id.button_send_banner)
    public void sendBanner() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(ACTION_BANNER);
                sendBroadcast(intent);
            }
        }, 2000);


    }

    @OnClick(R.id.button_send_desktop)
    public void sendDesktop() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent();
//                intent.setAction(ACTION_DESKTOP);
//                sendBroadcast(intent);
//            }
//        }, 5000);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final long timeNow = System.currentTimeMillis();
        long delay = 5000;
        final long nextCheckTime = timeNow + delay;
        final PendingIntent pi = createAlarmIntent();
        alarmManager.set(AlarmManager.RTC, nextCheckTime, pi);

    }

    @OnClick(R.id.button_send_fixed)
    public void sendFixed() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(ACTION_FIXED);
                sendBroadcast(intent);
            }
        }, 2000);


    }

    @OnClick(R.id.button_send_dynamic)
    public void sendDynamic() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(ACTION_FIXED);
                sendBroadcast(intent);
            }
        }, 2000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mSuspendReceiver != null) {
//            unregisterReceiver(mSuspendReceiver);
//        }

    }


}
