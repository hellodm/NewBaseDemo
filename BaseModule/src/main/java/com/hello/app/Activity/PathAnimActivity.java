package com.hello.app.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hello.app.MyView.QQMessageView;
import com.hello.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PathAnimActivity extends Activity {

    @InjectView(R.id.image_start)
    ImageView mStartView;

    @InjectView(R.id.lin_bac)
    LinearLayout mLinBack;

    @InjectView(R.id.image_src)
    ImageView mSrcView;

    @InjectView(R.id.image_photo)
    ImageView mPhotoView;

    @InjectView(R.id.image_drag)
    QQMessageView mDragView;

    @InjectView(R.id.image_end)
    ImageView mEndView;

    private ValueAnimator mAnimator;

    private ObjectAnimator mAnimX;

    private ObjectAnimator mAnimY;

    PathMeasure mMeasure;

    private float a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);
        ButterKnife.inject(this);
        mAnimator = new ValueAnimator();
    }

    @OnClick(R.id.image_start)
    public void goStartAnim() {
        PointF start = getViewPoint(mStartView);
        PointF end = getViewPoint(mEndView);
        mMeasure = getPathMeasure(start, end);
        setAnimator(mMeasure);
        startAnim();

    }

    @OnClick(R.id.image_src)
    public void goStartPhoto() {

        PointF pW = getWindowSize();
        PointF start = getViewPoint(mSrcView);
        PointF end = new PointF(pW.x / 2 - mSrcView.getMeasuredWidth() / 2,
                pW.y / 2 - mSrcView.getMeasuredHeight() / 2);
        mMeasure = getPhotoPathMeasure(start, end);
        float scale = pW.x / mSrcView.getMeasuredWidth();
        setPhotoAnimator(mMeasure, scale);
        mLinBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.lin_bac)
    public void goBackPhoto() {

        PointF start = getViewPoint(mPhotoView);
        PointF end = getViewPoint(mSrcView);
        mMeasure = getPhotoPathMeasure(start, end);
        float scale = start.x / mSrcView.getMeasuredWidth();
        setPhotoAnimator(mMeasure, scale);
        mLinBack.setVisibility(View.GONE);
    }

    @OnClick(R.id.image_drag)
    public void goDrag() {
    }

    private PointF getWindowSize() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        return new PointF(metric.widthPixels, metric.heightPixels);

    }


    private void setPhotoAnimator(PathMeasure measure, float scale) {
        AnimatorSet set = new AnimatorSet();
        mAnimator = ValueAnimator.ofFloat(0, measure.getLength() / 2);
        mAnimX = ObjectAnimator.ofFloat(mPhotoView, "scaleX", 1f, scale);
        mAnimY = ObjectAnimator.ofFloat(mPhotoView, "scaleY", 1f, scale);
        set.setDuration(500);
        set.setTarget(mPhotoView);
        set.setInterpolator(new LinearInterpolator());//
        set.playTogether(mAnimator, mAnimX, mAnimY);
//        set.playTogether(mAnimator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
                mPhotoView.setTranslationX(points[0]);
                mPhotoView.setTranslationY(points[1]);
            }
        });
        set.start();

    }

    private void setAnimator(PathMeasure measure) {
        mAnimator = ValueAnimator.ofFloat(0, measure.getLength());
        mAnimator.setDuration(3000);
//        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(0);
        mAnimator.setInterpolator(new BounceInterpolator());//弹球效果
//        mAnimator.setInterpolator(new LinearInterpolator());//
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float[] points = new float[2];
                mMeasure.getPosTan(value, points, null);
                mStartView.setTranslationX(points[0]);
                mStartView.setTranslationY(points[1]);
//                mStartView.setTranslationZ(a=a+5.0f);
//                    ViewCompat.offsetLeftAndRight(mStartView, (int) points[0]);
//                    ViewCompat.offsetTopAndBottom(mStartView, (int) points[1]);
            }
        });
    }


    private void startAnim() {
        mAnimator.start();
    }


    private PointF getViewPoint(View view) {
        int[] points = new int[2];
        view.getLocationOnScreen(points);
        return new PointF(points[0], points[1]);
    }

    private PathMeasure getPathMeasure(PointF start, PointF end) {

        Path path = new Path();
//        path.addCircle(450,900,300, Path.Direction.CCW); //圆圈路径
//        path.addOval(200,400,900,1000, Path.Direction.CW);
//        path.moveTo(start.x,start.y);
//        path.quadTo(900,0,end.x,end.y);//贝塞尔二阶
        path.cubicTo(start.x, start.y, 900, 0, end.x, end.y);//贝塞尔三阶
        return new PathMeasure(path, false);

    }

    private PathMeasure getPhotoPathMeasure(PointF start, PointF end) {

        Path path = new Path();
        path.moveTo(start.x, start.y);
//        path.lineTo(end.x,end.y);
        path.lineTo(end.x, end.y);//贝塞尔二阶
        return new PathMeasure(path, true);

    }

}
