package com.hello.app.ijkmedia.control;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.hello.app.ijkmedia.AndroidMediaController;
import com.hello.app.ijkmedia.IRenderView;

import java.io.File;
import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/10/15
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class IjkMediaControl implements IMediaControl {

    private String TAG = "IjkMediaControl";

    private AndroidMediaController mMediaController;

    private IRenderView.ISurfaceHolder mSurfaceHolder = null;

    private IMediaPlayer mMediaPlayer = null;

    private IRenderView mRenderView;

    private MediaCallBack mCallBack;

    private int mSurfaceWidth;

    private int mSurfaceHeight;

    private String mUri = "";

    private Context mContext;

    @Override
    public void initMedia(IRenderView view, MediaCallBack callBack) {
        mCallBack = callBack;
//        mMediaController = new AndroidMediaController(null, false);

//        mVideoView.setMediaController(mMediaController);
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mMediaPlayer = createPlayer();
        setRenderView(view);
        mMediaPlayer.setOnPreparedListener(mPreparedListener);
        mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
        mMediaPlayer.setOnErrorListener(mErrorListener);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.prepareAsync();
    }


    public void setRenderView(IRenderView renderView) {
        mRenderView = renderView;
        mRenderView.addRenderCallback(mSHCallback);
        if (mSurfaceHolder != null) {
            mSurfaceHolder.bindToMediaPlayer(mMediaPlayer);
        }

    }

    @Override
    public void initMedia(SurfaceView view, SurfaceView subtitlesView) {

    }

    @Override
    public void setMediaUri(String uri) {
        mUri = uri;
        if (TextUtils.isEmpty(uri) && mCallBack != null) {
            mCallBack.onPlayError("播放地址错误！");
        }
        String scheme = Uri.parse(mUri).getScheme();
        if (TextUtils.isEmpty(scheme) || scheme.equalsIgnoreCase("http")) {
            try {
                mMediaPlayer.setDataSource(mContext, Uri.parse(mUri));
            } catch (IOException e) {
                e.printStackTrace();
                if (mCallBack != null) {
                    mCallBack.onPlayError("播放器初始化出错!");
                }
            }
        }
    }

    @Override
    public void playMedia() {
        if (mMediaPlayer == null) {
            return;
        }
        mMediaPlayer.start();
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer == null) {
            return false;
        }
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void pauseMedia() {
        if (mMediaPlayer == null) {
            return;
        }

        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }

    }

    @Override
    public void stopMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void destroyMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }


    public IMediaPlayer createPlayer() {
        IMediaPlayer mediaPlayer = null;

        IjkMediaPlayer ijkMediaPlayer = null;
        if (mUri != null) {
            ijkMediaPlayer = new IjkMediaPlayer();
            ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
            ijkMediaPlayer
                    .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,
                    "mediacodec-handle-resolution-change", 0);

            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format",
                    IjkMediaPlayer.SDL_FCC_RV32);

            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

            ijkMediaPlayer
                    .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        }
        mediaPlayer = ijkMediaPlayer;

//        mediaPlayer = new TextureMediaPlayer(mediaPlayer);

        return mediaPlayer;
    }


    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {

        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {

        }
    };

    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener =
            new IMediaPlayer.OnVideoSizeChangedListener() {
                public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum,
                        int sarDen) {
                    if (mCallBack != null && mp != null) {
                        mCallBack
                                .onNewLayout(width, height, mp.getVideoWidth(), mp.getVideoHeight(),
                                        sarNum, sarDen);
                    }
                }
            };


    private IMediaPlayer.OnErrorListener mErrorListener =
            new IMediaPlayer.OnErrorListener() {
                public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
                    return false;
                }
            };


    IRenderView.IRenderCallback mSHCallback = new IRenderView.IRenderCallback() {
        @Override
        public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int w,
                int h) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceChanged: unmatched render callback\n");
                return;
            }

            mSurfaceWidth = w;
            mSurfaceHeight = h;
            mMediaPlayer.start();
        }

        @Override
        public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width,
                int height) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceCreated: unmatched render callback\n");
                return;
            }
            if (holder == null && mMediaPlayer != null) {
                mMediaPlayer.setDisplay(null);
                return;
            }
            mSurfaceHolder = holder;
            if (mMediaPlayer != null) {
                holder.bindToMediaPlayer(mMediaPlayer);
            }


        }

        @Override
        public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            mSurfaceHolder = null;
            if (mMediaPlayer != null) {
                mMediaPlayer.setDisplay(null);
            }
        }
    };


}
