package com.hello.app.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hello.app.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NotifyActivity extends Activity {


    private String packageName = "";

    View view;

    private static List<Message> mMessages = new ArrayList<Message>();

    private int size;

    private static boolean isShow = false;

    @InjectView(R.id.notify_dialog_more_bg)
    public RelativeLayout mLayoutMore;

    @InjectView(R.id.notify_content)
    public RelativeLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notify_fixed);
        ButterKnife.inject(this);
        Log.i("NotifyActivity", "onCreate");
        putNotifyMessage();
    }

    @Override
    protected void onResume() {
        Log.i("NotifyActivity", "onResume");
        super.onResume();

    }


    /**
     * 显示view
     */
    private void showView() {

        size = mMessages.size();
        if (size <= 0) { //当消息数量为0
            return;
        }
        Message message = mMessages.get(0);
        view = LayoutInflater.from(this).inflate(R.layout.dialog_notify_fixed, null);
        if (size >= 2) { //当消息数量大于1
            mLayoutMore.setVisibility(View.VISIBLE);
        } else {
            mLayoutMore.setVisibility(View.GONE);
        }
        updateChildView(mContentView, message);
        isShow = true;
    }


    /** 更新子view */
    private void updateChildView(RelativeLayout viewParent, int layoutId) {
        View viewChild = LayoutInflater.from(this).inflate(layoutId, null);
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

    /** 更新子view */
    private void updateChildView(RelativeLayout viewParent, Message message) {

        if (viewParent.getChildCount() > 0) {
            viewParent.removeAllViews();
        }
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        View viewChild = NotifyDialog.getInstance(this).getView(message,
                new NotifyDialog.OnDialogCloseListener() {
                    @Override
                    public void colseDialog() {
                        closePage();
                    }
                });
        viewParent.addView(viewChild, lp);

    }


    private void updateView() {
        if (size >= 2) {
            mLayoutMore.setVisibility(View.VISIBLE);
        } else {
            mLayoutMore.setVisibility(View.GONE);
        }
        updateChildView(mContentView, mMessages.get(0));

    }

    /**
     * put消息
     */
    public void putNotifyMessage() {

        //TODO 在这里进行消息数据的读取
        Message message = new Message();
        message.instruct = "C0001";
        message.state = 1;
        Message message1 = new Message();
        message1.instruct = "C0002";
        message1.state = 1;
        Message message2 = new Message();
        message2.instruct = "C0003";
        message2.state = 1;

        mMessages.add(message);
        mMessages.add(message1);
        mMessages.add(message2);
        size = mMessages.size();

        showDialog();//显示dialog

    }

    public void showDialog() {
        if (isShow && view != null) {
            updateView();
        } else {
            showView();
        }

    }


    private void closePage() {

        mMessages.get(0).state = 2;
        mMessages.remove(0);
        size -= 1;

        Log.i("NotifyActivity", "size=" + size);

        if (size < 1) {
            isShow = false;
            finish();
        } else {
            updateView();
        }

    }
}
