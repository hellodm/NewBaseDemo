package com.hello.app.Activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hello.app.MyView.LoadingImageView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SoundPoolActivity extends Activity {


    private SoundPool mSoundPool;

    @InjectView(R.id.loadingimageView)
    LoadingImageView mLoadingImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool);
        ButterKnife.inject(this);
        initSoundPool();
//        mProgressWheel.startSpinning();

    }

    @Override
    protected void onDestroy() {
        mSoundPool.release();
        mSoundPool = null;
        super.onDestroy();
    }

    private void initSoundPool() {

        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mSoundPool.play(mSoundId, 0.5f, 0.5f, 1, 0, 1.5f);
            }
        });

    }

    int a = 0;

    @OnClick(R.id.btn_add)
    public void addQueue() {

    }

    @OnClick(R.id.btn_play)
    public void playQueue() {
        addVoice();

    }

    @OnClick(R.id.btn_load)
    public void goLoad() {
        mLoadingImageView.setLoading(mLoadingImageView.getLoadingState() == true ? false : true);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLoadingImageView.setProgress(a++);
                mHandler.postDelayed(this, 24);

            }
        });

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    private int mSoundId;

    private int priority = 100000;

    private void addVoice() {
        if (mSoundPool != null) {
            mSoundId = mSoundPool.load(this, R.raw.sdn_baoxintixing, 1);
        }

    }

}
