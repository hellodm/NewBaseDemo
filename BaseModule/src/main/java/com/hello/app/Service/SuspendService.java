package com.hello.app.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hello.app.R;

public class SuspendService extends Service {


    WindowManager mManager = null;

    WindowManager.LayoutParams mLayoutParams = null;

    View view;

    private float mTouchStartX;

    private float mTouchStartY;


    public SuspendService() {
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
        view = LayoutInflater.from(this).inflate(R.layout.item_suspend, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("SuspendService", "onStartCommand()");
        createView();
        return super.onStartCommand(intent, flags, startId);

    }

    /** 初始化view */
    private void createView() {
//        mManager = (WindowManager) this.getApplicationContext()
//                .getSystemService(Context.WINDOW_SERVICE);
        mManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.format = PixelFormat.RGBA_8888;

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                float touchX = event.getRawX();
                float touchY = event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY() + view.getHeight() / 2;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        updateViewPosition(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:
                        updateViewPosition(touchX, touchY);
                        mTouchStartX = mTouchStartY = 0;
                        break;


                }
                return true;
            }
        });
        mManager.addView(view, mLayoutParams);

    }

    private void updateViewPosition(float x, float y) {

        mLayoutParams.x = (int) (x - mTouchStartX);
        mLayoutParams.y = (int) (y - mTouchStartY);
        mManager.updateViewLayout(view, mLayoutParams);
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


