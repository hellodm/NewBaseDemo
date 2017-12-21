package com.hello.app.Brodcast;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hello.app.Activity.MainActivity;
import com.hello.app.Activity.NotifyActivity;
import com.hello.app.Activity.SuspendActivity;
import com.hello.app.R;
import com.hello.app.Service.DialogNotifyService;
import com.hello.app.setvice.MyService;

public class SuspendReceiver extends BroadcastReceiver {

    public final static String ACTION_DESKTOP = "android.myapplication.desktop";

    final static String ACTION_LOCK = "android.myapplication.lock";

    final static String ACTION_BANNER = "android.myapplication.banner";

    final static String ACTION_FIXED = "android.myapplication.fixed";

    final static String ACTION_DYNAMIC = "android.myapplication.dynamic";

    public SuspendReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        switch (action) {
            case ACTION_LOCK:
                Log.i("onReceive", ACTION_LOCK);
                KeyguardManager km = (KeyguardManager) context
                        .getSystemService(Context.KEYGUARD_SERVICE);
                sendBannerNotify(context);

                if (km.inKeyguardRestrictedInputMode() && km
                        .isKeyguardLocked()) {  //判断是否锁屏 版本需求16以上
                    intent = new Intent(context, SuspendActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                    context.startActivity(intent);
                }

                break;
            case ACTION_BANNER:
                Log.i("onReceive", ACTION_BANNER);
                sendBannerNotify(context);
                break;
            case ACTION_DESKTOP:
                Log.i("onReceive", ACTION_DESKTOP);
                Intent i = new Intent(context, MyService.class);
                intent.setAction(ACTION_DESKTOP);
                context.startService(i);
//                createAlarmIntent(context);
//                context.startService(new Intent(context, SuspendService.class));
                break;
            case ACTION_FIXED:
                Log.i("onReceive", ACTION_FIXED);

                intent = new Intent(context, NotifyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
                context.startActivity(intent);
                break;
            case ACTION_DYNAMIC:
                Log.i("onReceive", ACTION_DYNAMIC);
                context.startService(new Intent(context, DialogNotifyService.class));
                break;
        }


    }

    private PendingIntent createAlarmIntent(Context context) {

        final AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        final long timeNow = SystemClock.elapsedRealtime();
        long delay = 1000;
        final long nextCheckTime = timeNow + delay;

        final Intent i = new Intent();
        i.setClass(context, SuspendReceiver.class);
        i.setAction(SuspendReceiver.ACTION_DESKTOP);
        PendingIntent pi = PendingIntent
                .getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME, nextCheckTime, pi);
        return pi;
    }

    private static int a = 6;

    //
    private void sendBannerNotify(Context context) {
        NotificationManager manger = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("横幅通知");
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限==" + a++);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setLargeIcon(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.about_logo));
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pIntent1 = PendingIntent
                .getBroadcast(context, 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        //这句是重点
        builder.setFullScreenIntent(pIntent1, true);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        manger.notify(6, notification);
    }


}
