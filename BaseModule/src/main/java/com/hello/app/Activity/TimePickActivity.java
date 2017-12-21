package com.hello.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hello.app.MyView.CircleView;
import com.hello.app.MyView.TimePicker;
import com.hello.app.R;
import com.hello.app.Util.TimeUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimePickActivity extends Activity implements TimePicker.OnTimePickListener {

    //    @InjectView(R.id.pick)
//    TimePicker mTimePicker;

//    @InjectView(R.id.circle)
//    CircleView mCircleImageView;

    @InjectView(R.id.pick)
    TimePicker mTimePicker;

    @InjectView(R.id.text)
    TextView mTextView;


    @InjectView(R.id.imageCar)
    ImageView imageCar;


    Bitmap mCar;

    Bitmap mOffline;

    Bitmap mFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick);

        ButterKnife.inject(this);

        mTimePicker.setOnTimePickListener(this);

//        mCircleImageView.setImageResource(R.drawable.test);

//        mCar = BitmapFactory.decodeResource(getResources(), R.drawable.icon_car);
//        mOffline = BitmapFactory.decodeResource(getResources(), R.drawable.offline);
//        mFlag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
//
//        imageCar.setImageBitmap(
//                carOverLay(mCar, mFlag, 45));

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
//        mCircleImageView.recycle();

    }


    private void goIntent(Class c) {

        Intent intent = new Intent();
        intent.setClass(this, c);
        startActivity(intent);


    }

    @Override
    public void onTimePick(float min) {

        mTextView.setText(TimeUtil.secondTo((long) (min * 20)));
    }

//    private Bitmap carOverLay(Bitmap carHead, Bitmap overLay, float degree) {
//        Bitmap.Config srcConfig = Bitmap.Config.ARGB_8888;
//        Bitmap srcBitmap = Bitmap.createBitmap(overLay).copy(srcConfig, true);
//
//        Bitmap carBitmap = Bitmap.createBitmap(carHead, 0, 0, srcBitmap.getWidth(),
//                srcBitmap.getHeight());
//        carBitmap.setDensity(srcBitmap.getDensity());
//
//        PorterDuffXfermode MASK_TOP = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);
//
//        Paint paint = new Paint();
//        paint.setXfermode(MASK_TOP);
//        Canvas canvas = new Canvas(srcBitmap);
//
//        canvas.drawBitmap(carBitmap, 0f, 0f, paint);
//
//        final Matrix matrix = new Matrix();
//        matrix.setRotate(degree);
//
//        final int width = srcBitmap.getWidth();
//        final int height = srcBitmap.getHeight();
//        srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
//                width, height, matrix, true);
//
//        carHead.recycle();
//        overLay.recycle();
//
//        return srcBitmap;
//
//    }


}
