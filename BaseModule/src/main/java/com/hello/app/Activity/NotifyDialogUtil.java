package com.hello.app.Activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.hello.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments
 *
 * @author Caochong
 * @Time: 2015/5/6
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class NotifyDialogUtil {

    //    @InjectView(R.id.notifyDialog)
//    NotifyDialog mNotifyDialog;

    static WindowManager mManager = null;

    static WindowManager.LayoutParams mLayoutParams = null;

    View view;

    private static NotifyDialogUtil instance;

    private static Context mContext;

    private MyAdapter mAdapter;

    private static List<Message> mMessages = new ArrayList<Message>();

    private int size;

    private boolean isShow = false;


    static {
        initWindow();
    }


    public static NotifyDialogUtil getInstance(Context context) {
        mContext = context;

        if (instance == null) {
            instance = new NotifyDialogUtil();
        }
        if (mManager == null) {
            mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return instance;
    }

    @InjectView(R.id.notify_content)
    public RelativeLayout mContentView;

    private void showView() {

        size = mMessages.size();
        if (size <= 0) { //锟斤拷锟斤拷息
            return;
        }
        Message message = mMessages.get(0);
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_notify_fixed, null);
        ButterKnife.inject(this, view);
        if (size >= 2) { //锟斤拷锟斤拷锟斤拷息--锟斤拷示锟铰凤拷锟斤拷式锟斤拷锟斤拷
            view.findViewById(R.id.notify_dialog_more_bg).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.notify_dialog_more_bg).setVisibility(View.GONE);
        }
        updateChildView(mContentView, message.getLayoutId());
        mManager.addView(view, mLayoutParams);
        isShow = true;
    }

    /** 更新子view */
    private void updateChildView(RelativeLayout viewParent, int layoutId) {
        View viewChild = LayoutInflater.from(mContext).inflate(layoutId, null);
        viewChild.findViewById(R.id.notify_button_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePage();
                    }
                });
        if (viewParent.getChildCount() > 0) {
            viewParent.removeAllViews();
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        viewParent.addView(viewChild, lp);
    }


    /** 锟斤拷锟斤拷view */
    private static void initWindow() {

        if (mLayoutParams == null) {
            mLayoutParams = new WindowManager.LayoutParams();
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            mLayoutParams.gravity = Gravity.CENTER;
            mLayoutParams.x = 0;
            mLayoutParams.y = 0;
            mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            mLayoutParams.format = PixelFormat.RGBA_8888;
        }
    }


    private void updateView() {
        if (size >= 2) { //锟斤拷锟斤拷锟斤拷息--锟斤拷示锟铰凤拷锟斤拷式锟斤拷锟斤拷
            view.findViewById(R.id.notify_dialog_more_bg).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.notify_dialog_more_bg).setVisibility(View.GONE);
        }
        updateChildView(mContentView, mMessages.get(0).getLayoutId());

    }


    private void closePage() {

        mMessages.get(0).state = 2;
        mMessages.remove(0);
        size -= 1;
        if (size < 1) {
            mManager.removeView(view);
            isShow = false;
        } else {
            updateView();
        }

    }


    /**
     * put锟斤拷息
     */
    public void putNotifyMessage(Message message) {
        mMessages.add(message);

    }

    public void showDialog() {
        if (isShow && view != null) {
            updateView();
        } else {
            showView();
        }

    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}