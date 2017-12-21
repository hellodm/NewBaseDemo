package com.hello.app.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.hello.app.R;
import com.hello.app.Util.SystemUtil;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SuspendActivity extends Activity {


    @InjectView(R.id.text_close)
    TextView textClose;

    private String packageName = "";

    private int INSTALLED = 0;


    private int NOTINSTALL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD      //解锁显示
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG|
//                |WindowManager.LayoutParams.TYPE_APPLICATION        //界面可以正常操作编辑
//                |WindowManager.LayoutParams.FLAG_BLUR_BEHIND
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_suspend);

        ButterKnife.inject(this);

//        SystemUtil.wakeScreen(this);//唤醒屏幕

    }


    @OnClick(R.id.text_close)
    public void goClose() {
        finish();

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

//        packageName = this.getPackageName();
//        if (isInstall(packageName)) {
//            // 获取目标应用安装包的Intent
//            Intent intent = getPackageManager().getLaunchIntentForPackage(
//                    packageName);
//            startActivity(intent);
//        }

    }

    @OnClick(R.id.button_go)
    public void goIn() {
        isAppOnTop();

        this.finish();
//        KeyguardManager km = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
//        KeyguardManager.KeyguardLock lock = km.newKeyguardLock(Activity.KEYGUARD_SERVICE);
//        lock.reenableKeyguard();//解锁

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

//        packageName = this.getPackageName();
//        if (isInstall(packageName)) {
//            // 获取目标应用安装包的Intent
//            Intent intent = getPackageManager().getLaunchIntentForPackage(
//                    packageName);
//            startActivity(intent);
//        }/

    }


    private boolean isInstall(String packageName) {
        // 判断是否安装
        if (new File("/data/data/" + packageName).exists()) {
            // 获取系统中安装的所有应用包名集合
            List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
                // 找出指定的应用
                if (packageName.equals(packageInfo.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isAppOnTop() {
        boolean result = false;
        final Context context = this;
        final String appPackageName = context.getPackageName();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final List<ActivityManager.RunningAppProcessInfo> processList = activityManager
                        .getRunningAppProcesses();
                if (processList != null && processList.size() > 0) {
                    for (ActivityManager.RunningAppProcessInfo info : processList) {
                        Log.d("AppHelper",
                                "isAppOnTop() processName=" + info.processName + "; importance="
                                        + info.importance
                                        + "; IMPORTANCE_VISIBLE = 200, IMPORTANCE_FOREGROUND = 100");
                        if (appPackageName.equals(info.processName)
                                && info.importance
                                <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
                                && info.importance
                                >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            result = true;
                            break;
                        }
                    }
                }
            } else {
                final List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
                        .getRunningTasks(1);
                if (tasksInfo != null && tasksInfo.size() > 0) {
                    final String topTaskPackageName = tasksInfo.get(0).topActivity.getPackageName();
                    result = appPackageName.equals(topTaskPackageName);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Log.i("AppHelper", "isAppOnTop() result=" + result);
        return result;
    }


}
