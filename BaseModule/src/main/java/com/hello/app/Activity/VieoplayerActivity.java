package com.hello.app.Activity;

import android.app.Activity;
import android.media.MediaCodec;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.hello.app.R;
import com.hello.app.ijkmedia.AndroidMediaController;
import com.hello.app.ijkmedia.IRenderView;
import com.hello.app.ijkmedia.IjkVideoView;
import com.hello.app.ijkmedia.control.IMediaControl;
import com.hello.app.ijkmedia.control.IjkMediaControl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VieoplayerActivity extends Activity implements IMediaControl.MediaCallBack {

    @InjectView(R.id.viedoView)
    SurfaceView mVideoView;

    private boolean mBackPressed;

    private IMediaControl mMediaControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieoplayer);
        ButterKnife.inject(this);
        initData();


    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaControl.stopMedia();
        IjkMediaPlayer.native_profileEnd();
    }

    private void initData() {

        mMediaControl = new IjkMediaControl();
        mMediaControl.initMedia((IRenderView) mVideoView, this);

    }


    @OnClick(R.id.butt_play)
    public void play() {

//        mVideoView.setVideoPath("http://193.168.0.1:6200");
//        mVideoView.setVideoPath("rtsp://192.168.15.1/h264");
//        mVideoView.setVideoPath("http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8");
//        mVideoView.start();

        mMediaControl.setMediaUri("rtsp://192.168.15.1/h264");
//        mMediaControl.setMediaUri("http://193.168.0.1:6200");
        mMediaControl.playMedia();

    }


    @Override
    public void onNewLayout(int width, int height, int visibleWidth, int visibleHeight, int sarNum,
            int sarDen) {

    }

    @Override
    public void onPlayStart() {
        mMediaControl.playMedia();
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPlayPause() {
        mMediaControl.pauseMedia();
    }

    @Override
    public void onPlayStop() {
        mMediaControl.stopMedia();
    }

    @Override
    public void onPlayError(String error) {
    }
}
