package com.hello.app.ijkmedia.control;

import android.view.SurfaceView;

import com.hello.app.ijkmedia.IRenderView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/10/10
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public interface IMediaControl {


    public void initMedia(IRenderView view, MediaCallBack callBack);

    public void initMedia(SurfaceView view, SurfaceView subtitlesView);

    public void setMediaUri(String uri);

    public void playMedia();

    public boolean isPlaying();

    public void pauseMedia();

    public void stopMedia();

    public void destroyMedia();

    public interface MediaCallBack {

        public void onNewLayout(int width, int height, int visibleWidth, int visibleHeight,
                int sarNum, int sarDen);

        public void onPlayStart();

        public void onPlaying();

        public void onPlayPause();

        public void onPlayStop();

        public void onPlayError(String error);
    }


}
