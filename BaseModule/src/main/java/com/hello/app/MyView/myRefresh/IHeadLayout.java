package com.hello.app.MyView.myRefresh;

import android.graphics.drawable.Drawable;

/**
 * Created by dong on 2014/7/31.
 */
public interface IHeadLayout {


    /** 设置上次更新状态标签 */
    public void setLastUpdatedLabel(CharSequence label);

    public void setLoadingDrawable(Drawable drawable);

    /** 设置下拉标签 */
    public void setPullLabel(CharSequence pullLabel);

    /** 设置正在刷新标签 */
    public void setRefreshingLabel(CharSequence refreshingLabel);

    /** 设置释放标签 */
    public void setReleaseLabel(CharSequence releaseLabel);


}
