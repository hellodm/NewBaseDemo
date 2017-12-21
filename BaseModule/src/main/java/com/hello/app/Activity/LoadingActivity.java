package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hello.app.MyView.LoadingView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/** 双圈loading */
public class LoadingActivity extends Activity {

    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.text_top)
    TextView textTop;

    @InjectView(R.id.lin_top)
    LinearLayout linTop;

    @InjectView(R.id.load)
    LoadingView load;

    private Animation anim_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.inject(this);
        //TODO 注释什么意思
        text.setText("注释");

        anim_in = new AnimationUtils().loadAnimation(this, R.anim.anim_top_pull_drop);


    }


    @OnClick(R.id.btn_start)
    public void click(Button button) {
        text.setText(button.getText());

    }


    @OnClick(R.id.text_top)
    public void pullDown() {

        if (linTop.isShown()) {

            linTop.setVisibility(View.GONE);
        } else {
            linTop.startAnimation(anim_in);
            linTop.setVisibility(View.VISIBLE);
        }
    }


}
