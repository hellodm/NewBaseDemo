/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hello.app.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;

import com.hello.app.ijkmedia.IMediaController;

import java.util.ArrayList;

public class IjkMediaController extends MediaController implements IMediaControl {

    private ActionBar mActionBar;

    public IjkMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public IjkMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        initView(context);
    }

    public IjkMediaController(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
        if (isShowing()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override
    public void show() {
        super.show();
        if (mActionBar != null) {
            mActionBar.show();
        }
    }

    @Override
    public void hide() {
        super.hide();
        if (mActionBar != null) {
            mActionBar.hide();
        }
        for (View view : mShowOnceArray) {
            view.setVisibility(View.GONE);
        }
        mShowOnceArray.clear();
    }

    //----------
    // Extends
    //----------
    private ArrayList<View> mShowOnceArray = new ArrayList<View>();

    public void showOnce(@NonNull View view) {
        mShowOnceArray.add(view);
        view.setVisibility(View.VISIBLE);
        show();
    }

    @Override
    public void initMedia(SurfaceView view, VoutLayoutCallBack callBack) {

    }

    @Override
    public void initMedia(SurfaceView view, SurfaceView subtitlesView) {

    }

    @Override
    public void setMediaUri(String uri) {

    }

    @Override
    public void playMedia() {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pauseMedia() {

    }

    @Override
    public void stopMedia() {

    }

    @Override
    public void destroyMedia() {

    }
}
