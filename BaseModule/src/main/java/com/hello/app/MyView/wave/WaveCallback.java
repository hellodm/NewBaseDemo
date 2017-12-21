package com.hello.app.MyView.wave;

/**
 * Created by mozzie on 16-12-19.
 */

public interface WaveCallback {

    void startWave();

    void onVolume(int volume, byte[] data);

    void startLoading();

    void completeWave();

}
