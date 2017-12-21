package com.hello.app.Activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.hello.app.MyView.CheckScoreView;
import com.hello.app.MyView.loading.KLoadingView;
import com.hello.app.MyView.loading.KSmileView;
import com.hello.app.MyView.loading.LoadingRecycleView;
import com.hello.app.MyView.seekbar.CaoSeekBar;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SeekBarActivity extends Activity {

    @InjectView(R.id.checkView)
    public CheckScoreView mCheckScoreView;

    @InjectView(R.id.button_switch)
    public Button mButton;

    @InjectView(R.id.mySeekBar)
    public CaoSeekBar mCaoSeekBar;

    @InjectView(R.id.loadingView)
    public KLoadingView mLoadingView;

    @InjectView(R.id.smileView)
    public KSmileView mSmileView;

    @InjectView(R.id.LoadingRecycleView)
    public LoadingRecycleView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        ButterKnife.inject(this);
        mCaoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLoadingView.pullProgress(progress);
                mSmileView.pullProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private boolean isSwitch;

    @OnClick(R.id.button_switch)
    public void goSwitch() {

        AnimationDrawable animationDrawable = (AnimationDrawable) mButton.getBackground();
        animationDrawable.start();
        mRecycleView.start();
        if (isSwitch) {
            isSwitch = false;
            mCheckScoreView.setCheckState(CheckScoreView.CheckState.CHECKING);
            mCaoSeekBar.setVisibility(View.GONE);
        } else {
            isSwitch = true;
            mCheckScoreView.setCheckState(CheckScoreView.CheckState.NORMAL);
            mCaoSeekBar.setVisibility(View.VISIBLE);

        }
    }

}
