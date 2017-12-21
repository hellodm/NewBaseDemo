package com.hello.app.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hello.app.MyView.GroupHeadView;
import com.hello.app.R;
import com.hello.app.Util.BitmapUtil;
import com.hello.app.Util.MapUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Bitmap学习的activity
 */
public class BitmapActivity extends Activity implements Runnable {


    @InjectView(R.id.image1)
    ImageView image1;

    @InjectView(R.id.image2)
    ImageView image2;

    @InjectView(R.id.image3)
    ImageView image3;

    @InjectView(R.id.frameView)
    ImageView frameView;

    @InjectView(R.id.group1)
    GroupHeadView groupView1;

    @InjectView(R.id.group2)
    GroupHeadView groupView2;

    @InjectView(R.id.group3)
    GroupHeadView groupView3;

    @InjectView(R.id.group4)
    GroupHeadView groupView4;

    @InjectView(R.id.image_cut)
    ImageView image_cut;

    @InjectView(R.id.image_cu1)
    ImageView image_cut1;

    @InjectView(R.id.image_cu2)
    ImageView image_cut2;

    @InjectView(R.id.image_cu3)
    ImageView image_cut3;

    @InjectView(R.id.image_cu4)
    ImageView image_cut4;

    @InjectView(R.id.image_loading)
    ImageView image_loading;

    @InjectView(R.id.image_ani)
    ImageView image_ani;

    @InjectView(R.id.lin_ani)
    LinearLayout lin_ani;

//    @InjectView(R.id.circle)
//    com.hello.app.MyView.CornerView circle;

    float x_;

    float y_;

    private float w_lin;

    private float h_lin;

    private int time;

    private float angular;

    private float speed;

    private int radius;  //圆周半径

    private float period = 60;//周期

    private boolean isRefresh = true;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 1) {
                image_ani.setTranslationX(msg.arg1);
                image_ani.setTranslationY(msg.arg2);
                isRefresh = true;
            }
            return true;
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        ButterKnife.inject(this);
//
        Bitmap bitmap = BitmapUtil
                .getRound(BitmapFactory.decodeResource(this.getResources(), R.drawable.test), 200);

        frameView.setImageResource(R.drawable.test1);

        groupView1.setNumber(1);
        groupView2.setNumber(2);
        groupView3.setNumber(3);
        groupView4.setNumber(4);
        Bitmap bitmapSrc = BitmapUtil.getBitmapFromID(this, R.drawable.test1);
        Bitmap bitmapSrc1 = BitmapUtil.getBitmapFromID(this, R.drawable.car2);
        Bitmap bitmapDst = BitmapFactory
                .decodeResource(this.getResources(), R.drawable.chat_template_right);

        Bitmap bitmap2 = BitmapUtil.cutGivenBitmap(bitmapSrc, bitmapDst);

//        image_cut.setImageBitmap(BitmapUtil.getBacBitmap(bitmap2));
        image_cut.setImageBitmap(BitmapUtil.getMBitmap(bitmapDst, bitmapSrc));

//        image_cut1.setImageBitmap(BitmapUtil.getMBitmap(bitmapDst, bitmapSrc1));
        image_cut1.setImageBitmap(
                MapUtil.getGroupBitmap(this, R.drawable.user_default_icon_dp_35, 200, bitmapSrc));
        image_cut2.setImageBitmap(
                MapUtil.getGroupBitmap(this, R.drawable.user_default_icon_dp_45, 200, bitmapSrc,
                        null));
        image_cut3.setImageBitmap(
                MapUtil.getGroupBitmap(this, R.drawable.user_default_icon_dp_35, 200, bitmapSrc,
                        bitmapSrc,
                        bitmapSrc));
        image_cut4.setImageBitmap(
                MapUtil.getGroupBitmap(this, R.drawable.user_default_icon_dp_35, 200, bitmapSrc,
                        null,
                        bitmapSrc, null));

        image_cut2.setImageBitmap(bitmapSrc1);
        image_cut3.setImageBitmap(MapUtil.getGrayBitmap(bitmapSrc1, 0f));
        image_cut4.setImageBitmap(MapUtil.getGrayBitmap(bitmapSrc1, 0.5f));

        goAnim();

        new Thread(this).start();


    }

    private void goAnimm() {
        w_lin = 400;
        h_lin = 400;
        radius = (int) (w_lin / 2 - 50);
        Message msg = mHandler.obtainMessage();
        msg.what = 1;

        while (true) {
            if (isRefresh) {
                time++;
                angular = (float) (2 * Math.PI / period);
                speed = angular * radius;

                x_ = (float) (w_lin / 2 + radius * Math.cos(Math.PI / 2 - angular * time));
                y_ = (float) (h_lin / 2 - radius * Math.sin(Math.PI / 2 - angular * time));

                msg.arg1 = (int) x_;
                msg.arg2 = (int) y_;
                mHandler.sendMessage(msg);
                isRefresh = false;
            }


        }


    }


    /** 显示动画 */
    private void goAnim() {
        //创建一个AnimationSet对象，参数为Boolean型，

        //true表示使用Animation的interpolator，false则是使用自己的

        AnimationSet animationSet = new AnimationSet(true);

        //创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明

//        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_loading_wepp);

        //将alphaAnimation对象添加到AnimationSet当中
        animationSet.addAnimation(animation);
        animationSet.setRepeatMode(Animation.INFINITE);

        //使用ImageView的startAnimation方法执行动画

        image_loading.startAnimation(animationSet);

    }


    @OnClick(R.id.button)
    public void button() {

//        image1.setImageBitmap(groupView.getDrawables());

    }

    @Override
    public void run() {
        goAnimm();
    }
}
