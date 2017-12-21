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

package com.hello.app.ijkmedia;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hello.app.R;


public class Settings {

    private Context mAppContext;

    private SharedPreferences mSharedPreferences;

    public static final int PV_PLAYER__Auto = 0;

    public static final int PV_PLAYER__AndroidMediaPlayer = 1;

    public static final int PV_PLAYER__IjkMediaPlayer = 2;

    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;

    public Settings(Context context) {
        mAppContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public int getPlayer() {
        String key = mAppContext.getResources().getString(R.string.pref_key_player);
        String value = mSharedPreferences.getString(key, "");
        try {
            return Integer.valueOf(value).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getUsingMediaCodec() {
        return false;
    }

    public boolean getUsingMediaCodecAutoRotate() {
        return false;
    }

    public boolean getMediaCodecHandleResolutionChange() {
        return false;
    }

    public boolean getUsingOpenSLES() {
        return false;
    }

    public String getPixelFormat() {
        return "";
    }

    public boolean getEnableNoView() {
        return false;
    }

    public boolean getEnableSurfaceView() {
        return true;
    }

    public boolean getEnableTextureView() {
        return false;
    }

    public boolean getEnableDetachedSurfaceTextureView() {
        return false;
    }

    public boolean getUsingMediaDataSource() {
        return false;
    }

    public String getLastDirectory() {
        return "/";
    }

    public void setLastDirectory(String path) {
        String key = mAppContext.getResources().getString(R.string.pref_key_last_directory);
        mSharedPreferences.edit().putString(key, path).apply();
    }
}
