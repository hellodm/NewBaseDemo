package com.hello.app.media;

import android.view.SurfaceView;

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

    public void initMedia(SurfaceView view, VoutLayoutCallBack callBack);

    public void initMedia(SurfaceView view, SurfaceView subtitlesView);

    public void setMediaUri(String uri);

    public void playMedia();

    public boolean isPlaying();

    public void pauseMedia();

    public void stopMedia();

    public void destroyMedia();

    public interface VoutLayoutCallBack {

        public void onNewLayout(int width, int height, int visibleWidth, int visibleHeight,
                int sarNum, int sarDen);
    }


}
