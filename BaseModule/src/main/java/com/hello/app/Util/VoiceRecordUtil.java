package com.hello.app.Util;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.hello.app.R;

import java.io.IOException;

/**
 * Created by dong on 2014/7/21.
 * 处理声音的工具类
 */
public class VoiceRecordUtil {

    public static MediaRecorder recorder;

    private static MediaPlayer player;

    private static AudioRecord audioRecord;

    public static String RECORDSTATE = "";  //录制状态

    private static String PLAYSTATE = "";  //播放状态

    private static VolumeListener mVolumeListener = null;


    public static void setVolumeListener(VolumeListener volumeListener) {
        mVolumeListener = volumeListener;
    }

    /** 录制的状态枚举:开始、停止、释放 */
    private enum recordState {
        start, stop, release
    }

    /** 播放声音的枚举:开始，停止，暂停 */
    private enum playState {
        start, stop, release
    }


    /**
     * 设置录制状态
     */
    public static void setRecordState(recordState state) {

        if (recorder == null) {

            return;
        }
        try {

            switch (state) {

                case start:
                    recorder.prepare();
                    RECORDSTATE = Constant.RECORD_PREPARE;
                    recorder.start();
                    RECORDSTATE = Constant.RECORD_START;

                    break;
                case stop:
                    recorder.stop();
                    RECORDSTATE = Constant.RECORD_STOP;
                    break;
                case release:
                    recorder.reset();
                    RECORDSTATE = Constant.RECORD_RESET;
                    recorder.release();
                    RECORDSTATE = Constant.RECORD_RELEASE;
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.i("录制的枚举", RECORDSTATE + "");
        }
    }

    /**
     * 设置录制状态
     */
    private static void setPlayerState(playState state) {

        if (player == null) {

            return;
        }
        try {

            switch (state) {

                case start:

                    if (PLAYSTATE.equals(Constant.RECORD_START)) {
                        return;
                    }
                    if (RECORDSTATE.equals(Constant.RECORD_STOP) && recorder != null) {

                        setRecordState(recordState.release);
                    }
                    player.prepare();
                    player.start();
                    PLAYSTATE = Constant.RECORD_START;
                    break;
                case stop:
                    player.stop();
                    PLAYSTATE = Constant.RECORD_STOP;
                    break;
                case release:
                    if (PLAYSTATE.equals(Constant.RECORD_START)) {
                        player.stop();
                    }
                    player.reset();
                    player.release();
                    PLAYSTATE = Constant.RECORD_RELEASE;
                    break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.i("播放的枚举状态", PLAYSTATE + "");
        }
    }


    /**
     * 开始录音
     *
     * @param path_name 存储路径
     */
    public static void startRecord(String path_name) {

        if (recorder == null || RECORDSTATE.equals(Constant.RECORD_RELEASE)) {

            recorder = new MediaRecorder();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path_name);

        setRecordState(recordState.start);

        updateMicStatus();

    }

    /** 停止录制 */
    public static void stopRecord() {

        setRecordState(recordState.stop);

    }

    /** 释放资源 */
    public static void releaseRecord() {

        setRecordState(recordState.release);

    }


    /**
     * 创建播放器
     */
    public static void createPlayer(String path) {

//      player = MediaPlayer.create(context, uri);

        if (player == null) {
            player = new MediaPlayer();
        }

        try {
            player.setDataSource(path);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** 开始播放 */
    public static void startPlay() {

        setPlayerState(playState.start);
    }

    /** 停止播放 */
    public static void stopPlay() {
        setPlayerState(playState.stop);
    }


    /** 释放资源 */
    public static void releasePlay() {
        setPlayerState(playState.release);
    }


    /**
     * 更新话筒状态 分贝是也就是相对响度 分贝的计算公式K=20lg(Vo/Vi) Vo当前振幅值 Vi基准值为600：我是怎么制定基准值的呢？ 当20
     * * Math.log10(mMediaRecorder.getMaxAmplitude() / Vi)==0的时候vi就是我所需要的基准值
     * 当我不对着麦克风说任何话的时候，测试获得的mMediaRecorder.getMaxAmplitude()值即为基准值。
     * Log.i("mic_", "麦克风的基准值：" + mMediaRecorder.getMaxAmplitude());前提时不对麦克风说任何话
     */
    private static int BASE = 600;

    private static int SPACE = 60;// 间隔取样时间

    private static Handler mHandler = null;

    private static MyRunable runable = null;

    public static void updateMicStatus() {

        if (runable == null) {

            runable = new MyRunable();
        }

        if (mHandler == null) {
            mHandler = new Handler();
        }

        if (recorder == null && RECORDSTATE.equals(Constant.RECORD_START)) {
            return;
        }
        // int vuSize = 10 * mMediaRecorder.getMaxAmplitude() / 32768;
        int ratio = recorder.getMaxAmplitude() / BASE;
        int db = 0;// 分贝
        if (ratio > 1) {

            db = (int) (20 * Math.log10(ratio));
        }

        mHandler.postDelayed(runable, SPACE);
        mVolumeListener.getVolume(db);




                        /*
                         * if (db > 1) { vuSize = (int) (20 * Math.log10(db)); Log.i("mic_",
			 * "麦克风的音量的大小：" + vuSize); } else Log.i("mic_", "麦克风的音量的大小：" + 0);
			 */
    }


    /**
     * 自定义的runable
     */
    private static class MyRunable implements Runnable {

        @Override
        public void run() {

            updateMicStatus();

        }
    }


    /** 回调接口--声音监听接口 */

    public interface VolumeListener {

        public void getVolume(int volume);//接口方法
    }


}
