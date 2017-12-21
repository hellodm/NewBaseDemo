package com.hello.app.setvice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.hello.app.Brodcast.SuspendReceiver;

public class MyService extends Service {

    public final static String ACTION_DESKTOP = "android.myapplication.desktop";

    public MyService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("MyService", ACTION_DESKTOP);
        createAlarmIntent(getApplicationContext());

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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
