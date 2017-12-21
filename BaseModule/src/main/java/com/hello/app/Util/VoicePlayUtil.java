package com.hello.app.Util;

import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dong on 2014/7/21.
 * 处理声音的工具类
 */
public class VoicePlayUtil {


    private static MediaPlayer player;


    private static String PLAYSTATE = "";  //播放状态


    /** 播放声音的枚举:开始，停止，暂停 */
    private enum playState {
        start, stop, release
    }


    /**
     * 设置播放状态
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
//                    if(VoiceRecordUtil.RECORDSTATE.equals(Constant.RECORD_STOP)&&VoiceRecordUtil.recorder!=null){
//
//                        VoiceRecordUtil.setRecordState(recordState.release);
//                    }
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
     * 创建播放器
     */
    public static void createPlayer(String path) {

        if (player == null) {
            player = new MediaPlayer();
        }

        player = new MediaPlayer();
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


}
