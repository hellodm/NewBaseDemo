package com.hello.app.setvice;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(
                Context.TELEPHONY_SERVICE);

        telephonyManager.listen(new PhoneListener(),
                PhoneStateListener.LISTEN_CALL_STATE); // 注册监听器 监听电话状态
    }

    private final class PhoneListener extends PhoneStateListener {

        private String incomeNumber; // 来电号码

        private MediaRecorder mediaRecorder;

        private File file;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                System.out.println(">>>>>>CallState>>>>>>>>" + state);
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: // 来电
                        System.out.println(">>>>>>来电>>>>>>>>" + state);
                        this.incomeNumber = incomingNumber;
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK: // 接通电话
                        file = new File(Environment.getExternalStorageDirectory(),
                                this.incomeNumber + System.currentTimeMillis() + ".mp3");
                        System.out.println(">>>>>>接通>>>>>>>>" + state + "file=" + file);
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioChannels(2);
//						 mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);//这个设置就是获取双向声音
                        //获得声音数据源（下麦克风）
                        mediaRecorder
                                .setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder
                                .setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 按3gp格式输出
                        mediaRecorder
                                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mediaRecorder.setOutputFile(file.getAbsolutePath()); // 输出文件
                        mediaRecorder.setOnErrorListener(
                                new MediaRecorder.OnErrorListener() {
                                    @Override
                                    public void onError(MediaRecorder mr,
                                            int what,
                                            int extra) {
                                        Log.e("mediaRecorder",
                                                "OnErrorListener " + what + "," + extra);
                                    }
                                });
                        mediaRecorder.prepare(); // 准备
                        try {
                            mediaRecorder.start();
                        } catch (Exception e) {
                            Log.e("mediaRecorder", "Exception " + e.getMessage());
                        }
                        break;

                    case TelephonyManager.CALL_STATE_IDLE: // 挂掉电话
                        System.out.println(">>>>>>挂电话>>>>>>>>" + state);
                        if (mediaRecorder != null) {

                            System.out.println(">>>>"
                                    + Environment.getExternalStorageDirectory());
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                            AppliacationIMmpl.file = file;
                        }

                        break;

                }
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
