package com.hello.app.Activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hello.app.MyView.refresh.PullToRefreshScrollView;
import com.hello.app.MyView.refresh.base.PullToRefreshBase;
import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 主页Activity
 */
public class MainActivity extends Activity {


    @InjectView(R.id.button_ball)
    Button button;


    @InjectView(R.id.scroll)
    PullToRefreshScrollView scrollView;

    @InjectView(R.id.icon_main_close)
    ImageView mClose;

    @InjectView(R.id.icon_main_open)
    ImageView mOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Log.i("MainActivity", "onCreate");
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        Log.i("显示", ViewUtil.getWindow(this));
        getSl();

//        RemoteViews remoteViews = new RemoteViews(getPackageName(),0);
        scrollView.setBackgroundColor(-Integer.parseInt("0000FF", 16));
        scrollView.setBackgroundColor(Color.parseColor("#71ca52"));
        scrollView.setBackgroundColor(getResources().getColor(R.color.blue_year));

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        new View(this).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//            }
//        },50);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");

    }

    @OnClick(R.id.icon_main_close)
    public void goOpen() {
        mClose.setVisibility(View.INVISIBLE);
        mOpen.setVisibility(View.VISIBLE);
        mMeasure = getPathMeasure(new PointF(mOpen.getX(), mOpen.getY()),
                new PointF(mOpen.getX(), mOpen.getY() + 50));
        setAnimator(mMeasure);
        mAnimator.start();
    }

    @OnClick(R.id.icon_main_open)
    public void goClose() {
        mOpen.setVisibility(View.INVISIBLE);
        mClose.setVisibility(View.VISIBLE);

    }

    private ValueAnimator mAnimator;

    private PathMeasure mMeasure;

    private void setAnimator(PathMeasure measure) {
        mAnimator = ValueAnimator.ofFloat(0, measure.getLength());
        mAnimator.setDuration(500);
        mAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new BounceInterpolator());
//        mAnimator.setInterpolator(new OvershootInterpolator());
//        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
                ;
//                mOpen.setTranslationX(points[0]-915);
                mOpen.setTranslationY(-points[1] + 1422);
//                if(Math.abs(points[0]-pMain.x)<20&&Math.abs(points[1]-pMain.y)<20){
//                    pTouch.set(pMain.x,pMain.y);
//                    invalidate();
//                    isComplete=true;
//                }else if(!isComplete){
                Log.i("onAnimationUpdate", "x=" + points[0] + "y=" + points[1]);
//                }

            }
        });
    }

    private PathMeasure getPathMeasure(PointF start, PointF end) {

        Path path = new Path();
        path.reset();
        path.moveTo(start.x, start.y);
//        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);

        return new PathMeasure(path, true);

    }


    private void getSl() {

        Pattern pattern = Pattern.compile("^hello_\\d{3}$");
//        Toast.makeText(this, pattern.matcher("hello_001").matches() + "", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("MainActivity", "onConfigurationChanged");
        super.onConfigurationChanged(
                newConfig);/////////////////////////////////////////////////////////////////////////

        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("MainActivity", "onConfigurationChanged");
        }
    }

    @OnClick(R.id.button_constraint)
    public void goConstraint(Button button) {
        goIntent(ConstraintActivity.class);
    }

    @OnClick(R.id.button_recycler)
    public void goRecycler(Button button) {
        goIntent(RemoteCopyCopyActivity.class);
    }

    @OnClick(R.id.button_opencv)
    public void goOpenCv(Button button) {
        goIntent(RemoteCopyActivity.class);
    }


    @OnClick(R.id.button_call)
    public void goCall(Button button) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        goIntent(CallRecordActivity.class);
    }

    @OnClick(R.id.button_wave)
    public void goWave(Button button) {
        goIntent(WaveActivity.class);
    }

    @OnClick(R.id.button_ftp)
    public void goFTP(Button button) {
        goIntent(FTPActivity.class);
    }

    @OnClick(R.id.button_path_anim)
    public void goPathAnim(Button button) {
        goIntent(PathAnimActivity.class);
    }

    @OnClick(R.id.button_play_video)
    public void goVideoplay(Button button) {
        goIntent(VieoplayerActivity.class);
    }


    @OnClick(R.id.button_RX_java)
    public void goRXjava(Button button) {
        boolean isOpen;
//        isOpen = NotificationManagerCompat.from(this).areNotificationsEnabled();//4.2不起作用
//        NotificationManager n = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//24以下版本不起作用
//        isOpen= n.areNotificationsEnabled();
//        INotificationManager nm = INotificationManager.Stub.asInterface(
//                getSystemService(Context.NOTIFICATION_SERVICE));
//        boolean enabled = true; // default on
//        try {
//            enabled = nm.areNotificationsEnabledForPackage(mAppEntry.info.packageName);
//        } catch (android.os.RemoteException ex) {
//            // this does not bode well
//        }

        Log.i("NotificationManager", "NotificationManager开关" + IsNotificationEnabled(this));

//        ContentResolver cv = this.getContentResolver();
//        android.provider.Settings.System.getString(cv, Settings.System.NOTIFICATION_SOUND);

        goIntent(RXJavaActivity.class);
    }

    @OnClick(R.id.button_k_play)
    public void goKplay(Button button) {
        goIntent(KplayActivity.class);
    }

    @OnClick(R.id.button_sound_pool)
    public void goSoundPool(Button button) {
        goIntent(SoundPoolActivity.class);
    }

    @OnClick(R.id.button_seek)
    public void goSeekBar(Button button) {
        goIntent(SeekBarActivity.class);
    }

    @OnClick(R.id.button_title)
    public void goTitle(Button button) {
        goIntent(BaseTitleActivity.class);

    }


    @OnClick(R.id.button_voice_map)
    public void goVoiceMap(Button button) {
        goIntent(VoiceWsActivity.class);

    }

    @OnClick(R.id.button_suspend)
    public void goSuspend(Button button) {
        goIntent(SuspendSendActivity.class);

    }

    @OnClick(R.id.button_shake)
    public void goShake(Button button) {
        goIntent(ShakeDelActivity.class);

    }

    @OnClick(R.id.button_scratch)
    public void goScratch(Button button) {
        goIntent(ScratchActivity.class);

    }

    /**
     * 按钮事件监听
     */
    @OnClick(R.id.button_loading)
    public void loading(Button btn_loading) {
        goIntent(LoadingActivity.class);


    }

    @OnClick(R.id.button_ball)
    public void goBall(Button button) {
        goIntent(GravityActivity.class);

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                handler.sendEmptyMessage(0);
//            }
//        }, 0, 200);

    }


    @OnClick(R.id.button_bitmap)
    public void goBitmap(Button button) {
        goIntent(BitmapActivity.class);

    }

    @OnClick(R.id.button_web)
    public void goWebView(Button button) {
        goIntent(WebActivity.class);

    }

    @OnClick(R.id.button_list)
    public void goList(Button button) {
        goIntent(ListRefreshActivity.class);

    }

    @OnClick(R.id.button_enum)
    public void goEnum(Button button) {
        goIntent(EnumActivity.class);

    }

    @OnClick(R.id.button_voice)
    public void goVoice(Button button) {
        goIntent(MyVoiceActivity.class);

    }

    @OnClick(R.id.button_circle)
    public void goCircle(Button button) {
        goIntent(CircleActivity.class);

    }

    @OnClick(R.id.button_edit)
    public void goEdit(Button button) {
        goIntent(EditActivity.class);

    }

    @OnClick(R.id.btn_baidu)
    public void goBaidu() {
        goIntent(BaiduActivity.class);

    }

    @OnClick(R.id.btn_check)
    public void goCheck() {
        goIntent(CheckActivity.class);

    }

    @OnClick(R.id.btn_fragment)
    public void goFragment() {
        goIntent(FragActivity.class);

    }

    @OnClick(R.id.btn_compare)
    public void goCompare() {
        goIntent(CompareActivity.class);

    }

    @OnClick(R.id.btn_pick)
    public void goPick() {
        goIntent(TimePickActivity.class);

    }

    @OnClick(R.id.btn_communication)
    public void goCommunication() {
        goIntent(MyBroadcastActivity.class);

    }


    @OnClick(R.id.btn_clearCache)
    public void goClearCache() {
        goIntent(MyCacheActivity.class);

    }

    @OnClick(R.id.btn_sunSetView)
    public void goSunSetView() {
        goIntent(SunSetActivity.class);

    }

    @OnClick(R.id.btn_openGl)
    public void goopenGl() {
        goIntent(OpenGLActivity.class);

    }

    @OnClick(R.id.btn_Unity)
    public void gooUnity() {
        goIntent(OpenGLActivity.class);

    }

    @OnClick(R.id.btn_Year)
    public void gooYear() {
        goIntent(YearViewActivity.class);

    }

    private void goIntent(Class c) {

        Intent intent = new Intent();
        intent.setClass(this, c);
        startActivity(intent);


    }

    /**
     * 获取通知开关
     */
    public boolean IsNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        String pkg = getApplication().getPackageName();

        int uid = getApplicationInfo().uid;

        try {

            Class appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass
                    .getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (int) opPostNotificationValue.get(Integer.class);

            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg)
                    == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
        }
        return true;
    }

}
