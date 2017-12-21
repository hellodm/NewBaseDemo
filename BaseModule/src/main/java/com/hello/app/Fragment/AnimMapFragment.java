package com.hello.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.baidu.mapapi.map.MapFragment;
import com.hello.app.Util.ViewUtil;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/3
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class AnimMapFragment extends MapFragment {

    private AnimatorSet setOut;

    private ValueAnimator tranAnimX_in, tranAnimY_in;

    private ValueAnimator scaleAnimX_Out, scaleAnimY_Out;


    public AnimMapFragment(Activity activity) {
        int startY = (int) ViewUtil.dip2px(activity, 200);
        int endY = (int) ViewUtil.dip2px(activity, 1920);
        setOut = new AnimatorSet();
        setOut.setInterpolator(new LinearInterpolator());
        setOut.setDuration(1000);
        tranAnimX_in = ObjectAnimator.ofFloat(this, "x", 0, 0);
        tranAnimY_in = ObjectAnimator.ofFloat(this, "y", 0, 0);
        scaleAnimX_Out = ObjectAnimator.ofFloat(this, "scaleX", 0.1f, 1f);
        scaleAnimY_Out = ObjectAnimator.ofFloat(this, "scaleY", 0.1f, 1f);
        setOut.playTogether(scaleAnimX_Out, scaleAnimY_Out, tranAnimX_in, tranAnimY_in);

    }


    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        Log.i("Animator", "transit=" + transit + "   enter=" + enter + "   nextAnim=" + nextAnim);
//        if (nextAnim == 0 && enter) {
//            return null;
//        } else if (nextAnim == 0 && enter == false) {
//
//            return setOut;
//        } else {
//            return null;
//        }

//        if(enter) {
//            return setOut;
//        }

        return super.onCreateAnimator(transit, enter, nextAnim);
    }


}
