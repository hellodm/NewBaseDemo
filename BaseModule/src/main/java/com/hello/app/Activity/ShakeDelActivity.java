package com.hello.app.Activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.hello.app.Fragment.ShakeFragment;
import com.hello.app.MyView.ShakeView;
import com.hello.app.R;
import com.hello.app.Service.SuspendService;
import com.hello.app.Util.ViewUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ShakeDelActivity extends Activity
        implements ShakeFragment.OnFragmentInteractionListener {

    private static final float DEGREE_0 = 1.8f;

    private static final float DEGREE_1 = -2.5f;

    private static final float DEGREE_2 = 2.5f;

    private static final float DEGREE_3 = -1.5f;

    private static final float DEGREE_4 = 1.5f;

    private static final int ANIMATION_DURATION = 80;

    @InjectView(R.id.image_shake)
    ImageView imageShake;

    @InjectView(R.id.image_shake1)
    ShakeView imageShake1;

    @InjectView(R.id.image_shake2)
    ShakeView imageShake2;

    @InjectView(R.id.image_shake3)
    ShakeView imageShake3;

    private ShakeFragment fragmentShake;


    private RotateAnimation mra;

    private RotateAnimation mrb;

    private float mDensity;

    private int mCount = 0;

    private float icon_width;

    private boolean isFirst = false;

    private float x_temp;

    private float y_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_del);
        ButterKnife.inject(this);
        initAnimation();

        this.startService(new Intent(this, SuspendService.class));

        fragmentShake = (ShakeFragment) this.getFragmentManager()
                .findFragmentById(R.id.fragment_shake);

        imageShake.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                shakePhone(ShakeDelActivity.this, 100);
                imageShake.startAnimation(mra);
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SuspendService.class));
    }

    void initAnimation() {

        icon_width = ViewUtil.dip2px(this, 72);

        mra = new RotateAnimation(DEGREE_1, -DEGREE_1, icon_width / 2, icon_width / 2);
        mrb = new RotateAnimation(-DEGREE_1, DEGREE_1, icon_width / 2, icon_width / 2);

        mDensity = ViewUtil.getDesc(getWindowManager()) / 160;
        mra.setDuration(ANIMATION_DURATION);
        mrb.setDuration(ANIMATION_DURATION);

        mra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageShake.startAnimation(mrb);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mrb.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageShake.startAnimation(mra);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        imageShake.startAnimation(mra);

    }

    /**
     * final Activity activity  ：调用该方法的Activity实例
     * long milliseconds ：震动的时长，单位是毫秒
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public void shakePhone(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    public void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    /** View添加监听 */
    private void addListener(ImageView view) {

    }

//    /** view的触摸监听 */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        float eventX = event.getX();
//        float eventY = event.getY();
//
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                Log.e("ACTION_OUT", "ACTION_DOWN");
//                if (imageShake3.isShake()) {
////                        imageShake3.stopShake();
//                    imageShake3.stopShake();
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.e("ACTION_OUT", "ACTION_MOVE");
////                    imageShake3.goView(eventX, eventY, imageShake3);
//                imageShake3.translationView(eventX, eventY, imageShake3);
//
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.e("ACTION_OUT", "ACTION_UP");
//                imageShake3.startShake();
//
//                break;
//
//        }
//        return true;
//    }


    @OnClick(R.id.button_stop)
    public void goStop() {
        imageShake2.stopShake();
        imageShake3.stopShake();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
