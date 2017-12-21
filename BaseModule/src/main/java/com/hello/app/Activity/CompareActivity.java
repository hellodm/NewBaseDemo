package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.hello.app.MyView.CompareSurfaceView;
import com.hello.app.MyView.ScoreView;
import com.hello.app.MyView.SmileView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CompareActivity extends Activity {

    @InjectView(R.id.compare)
    CompareSurfaceView mCompareView;

    @InjectView(R.id.SmileView)
    SmileView smileView;

    @InjectView(R.id.SmileView2)
    SmileView smileView2;

    @InjectView(R.id.SmileView3)
    SmileView smileView3;

    @InjectView(R.id.SmileView4)
    SmileView smileView4;


    @InjectView(R.id.scoreView)
    ScoreView scoreView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ButterKnife.inject(this);
//
        mCompareView.setValue(210);
        smileView.setData(true, 7, 54);
        smileView2.setData(true, 200, 40);
        smileView3.setData(true, 7, 0);
        smileView4.setData(true, 0, 10);
        smileView.setSuccess(true);
        scoreView.setScore(28);

    }

//    @OnClick(R.id.button1)
//    public void goReset() {
//        scoreView.setScore(3);
//        Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show();
//
//        if (TextUtils.isEmpty(edit_set.getText().toString())) {
//            return;
//        }
//        int value = Integer.parseInt(edit_set.getText().toString());
//        if (value <= 300) {
//            mCompareView.setValue(value);
//            mCompareView.resetView();
//        }
//
//    }


}
