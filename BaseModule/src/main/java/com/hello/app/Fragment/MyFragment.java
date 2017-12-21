package com.hello.app.Fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.hello.app.R;

/**
 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {

    private static final String COLOR = "color";


    private int colorBg;

    private OnFragmentInteractionListener mListener;


    public static MyFragment newInstance(int color) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }


    public MyFragment() {
    }


    private float startX;

    private float startY;

    private float endX;

    private float endY;

    private float width;

    private float height;


    public void setPosition(float width, float height, float startX, float startY, float endX,
            float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = width;
        this.height = height;
    }


    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

        Log.i("Animator", "transit=" + transit + "   enter=" + enter + "   nextAnim=" + nextAnim);
        if (nextAnim == 0 && enter) {

            float scaleX = 1f;
            float scaleY = 1f;

            scaleX = width / 1080;
            scaleY = height / 1920;

            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 1f);
            ValueAnimator scaleAnimX = ObjectAnimator.ofFloat(this, "scaleX", scaleX, 1f);
            ValueAnimator scaleAnimY = ObjectAnimator.ofFloat(this, "scaleY", scaleY, 1f);

            ValueAnimator tranAnimX = ObjectAnimator.ofFloat(this, "x", startX, endX);
            ValueAnimator tranAnimY = ObjectAnimator.ofFloat(this, "y", startY, endY);

            AnimatorSet set = new AnimatorSet();
            set.setDuration(300);
            set.setInterpolator(new AccelerateInterpolator());
//            set.playTogether(fadeAnim, scaleAnimX, scaleAnimY,tranAnimX,tranAnimY);
            set.playTogether(scaleAnimX, scaleAnimY, tranAnimX, tranAnimY, fadeAnim);
//            set.playTogether(scaleAnimX, scaleAnimY);
//            set.playTogether(tranAnimX, tranAnimY);

            return set;
        } else if (nextAnim == 0 && !enter) {
            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
            fadeAnim.setDuration(300);
            return fadeAnim;
        } else {
            return null;
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            colorBg = getArguments().getInt(COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
//        view.setBackgroundColor(colorBg);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

}
