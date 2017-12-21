package com.hello.app.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;

import com.hello.app.FTP.FTPService;
import com.hello.app.MyView.round.RoundedImageView;
import com.hello.app.MyView.wave.WaveMiView;
import com.hello.app.MyView.wave.WaveView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.functions.Action1;


public class WaveActivity extends Activity {

    @InjectView(R.id.waveView)
    WaveView mWaveView;

    @InjectView(R.id.waveView1)
    WaveMiView mWaveView1;

    @InjectView(R.id.image_color)
    RoundedImageView mImageView;

    @InjectView(R.id.image_color1)
    AppCompatImageView mImageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        ButterKnife.inject(this);

//
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @SuppressLint("ResourceAsColor")
    @OnClick(R.id.button_wave)
    public void goWave() {
        mWaveView.startWave();
        mWaveView1.startWave();
//        Log.i("WaveActivity", "开始方法");
//        boolean result = test();
//        Log.i("WaveActivity", "结束方法result="+result);
        mImageView.setImageDrawable(new ColorDrawable(Color.parseColor("#FFB6C1")));
        mImageView1.setImageDrawable(new ColorDrawable(Color.parseColor("#FFB6C1")));
        final AlertDialog dlg = new AlertDialog.Builder(this).create();

//        dlg.setCanceledOnTouchOutside(true);
//        dlg.setCancelable(true);
//        dlg.show();
//        dlg.setContentView(R.layout.item_pop);
//        dlg.getWindow().setCallback(this);

    }

    boolean result = false;

    private boolean test() {
        testAsync(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                result = true;
            }
        });
        return result;
    }

    private void testAsync(final Action1<Boolean> action1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                action1.call(true);
            }
        }).start();


    }

}
