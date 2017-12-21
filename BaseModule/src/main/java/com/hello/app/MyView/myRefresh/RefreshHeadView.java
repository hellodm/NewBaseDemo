package com.hello.app.MyView.myRefresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by dong on 2014/7/31.
 */
public class RefreshHeadView extends LinearLayout implements IHeadLayout {


    public RefreshHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setLastUpdatedLabel(CharSequence label) {

    }

    @Override
    public void setLoadingDrawable(Drawable drawable) {

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }
}
