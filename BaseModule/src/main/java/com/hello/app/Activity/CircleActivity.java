package com.hello.app.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.hello.app.MyView.CircleImageSurfaceView;
import com.hello.app.R;
import com.hello.app.Util.BitmapUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CircleActivity extends Activity {

    @InjectView(R.id.circle)
    CircleImageSurfaceView circle;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        ButterKnife.inject(this);

        bitmap = BitmapUtil.getBitmapFromID(this, R.drawable.test1);

        bitmap = BitmapUtil.getRound(bitmap, 100);
        circle.setBitmap_in(bitmap);
        circle.setBitmap_out(bitmap);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapUtil.releaseBitmap(bitmap);
    }
}
