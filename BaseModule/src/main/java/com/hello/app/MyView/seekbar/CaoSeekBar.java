package com.hello.app.MyView.seekbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.hello.app.R;

import java.util.zip.Inflater;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/6/30
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class CaoSeekBar extends SeekBar {


    PopupWindow mPopupWindow;

    Context mContext;

    View mDragView;


    public CaoSeekBar(Context context) {
        this(context, null);
        init();
    }

    public CaoSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CaoSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    void init() {
        mContext = this.getContext();
        mDragView = LayoutInflater.from(mContext).inflate(R.layout.item_seek_drag, null);
        mPopupWindow = new PopupWindow(mDragView,
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);

        setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("OnSeekBarChangeListener", progress + "onProgressChanged");
//                mPopupWindow.showAtLocation(seekBar, Gravity.TOP, progress*10,
//                        (int) seekBar.getY());
                mPopupWindow.update(seekBar, progress, -5, 100, 50);
//                mPopupWindow.dismiss();
//                mPopupWindow.showAtLocation(seekBar, Gravity.TOP, progress*5,
//                        (int) seekBar.getY());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("OnSeekBarChangeListener", "onStartTrackingTouch");
//                mPopupWindow.showAtLocation(seekBar, Gravity.TOP, (int) seekBar.getX(),
//                        (int) seekBar.getY());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("OnSeekBarChangeListener", "onStopTrackingTouch");
                if (mPopupWindow.isShowing()) {
                }
                mPopupWindow.dismiss();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i("onTouchEvent","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i("onTouchEvent","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
//                Log.i("onTouchEvent","ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
