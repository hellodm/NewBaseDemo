package com.hello.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hello.app.R;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/5/6
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class NotifyDialog {

    private static NotifyDialog instance;

    private static Context mContext;


    public static NotifyDialog getInstance(Context context) {
        mContext = context;

        if (instance == null) {
            instance = new NotifyDialog();
        }
        return instance;
    }


    public View getView(Message message, final OnDialogCloseListener mListener) {

        String mInstruct = message.instruct;
        View viewChild = null;

        if (mInstruct.equals(Message.COMMON_ACCIDENT) || mInstruct
                .equals(Message.COMMON_STATE) || mInstruct.equals(Message.COMMON_FAULT)) {
            viewChild = getAccidentView(message, mListener);

        } else if (mInstruct.equals(Message.COMMON_BREAK)) {
            viewChild = getBreakView(message, mListener);

        } else if (mInstruct.equals(Message.COMMON_INSTRUCT)) {
            viewChild = getCommonView(message, mListener);
        }

        return viewChild;

    }


    /** 返回通用的3个view */
    private View getAccidentView(Message message, final OnDialogCloseListener mListener) {
        View viewChild = LayoutInflater.from(mContext).inflate(message.getLayoutId(), null);
        TextView mTitle = (TextView) viewChild.findViewById(R.id.notify_fixed_title);
        TextView mContent = (TextView) viewChild.findViewById(R.id.notify_fixed_content);
        Button mButtonView = (Button) viewChild.findViewById(R.id.notify_fixed_break_go);
        final Message.Button button = message.button == null || message.button.size() == 0 ? null
                : message.button.get(0);

        mTitle.setText(message.title);
        mContent.setText(message.txt);
        if (button == null) {
            mButtonView.setVisibility(View.GONE);
        } else {
            mButtonView.setVisibility(View.VISIBLE);
            mButtonView.setText(button.title);
            mButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo 按钮点击
                    NotifyButtonEvent.clickButton(button, mListener);
                }
            });
        }

        viewChild.findViewById(R.id.notify_button_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.colseDialog();
                    }
                });

        return viewChild;

    }


    /** 返回违章提醒的View */
    private View getBreakView(Message message, final OnDialogCloseListener mListener) {
        View viewChild = LayoutInflater.from(mContext).inflate(message.getLayoutId(), null);
        TextView mTitle = (TextView) viewChild.findViewById(R.id.notify_fixed_title);
        TextView mContent = (TextView) viewChild.findViewById(R.id.notify_fixed_break_content);
        TextView mScore = (TextView) viewChild.findViewById(R.id.notify_fixed_break_score);
        TextView mMoney = (TextView) viewChild.findViewById(R.id.notify_fixed_break_money);
        Button mButtonView = (Button) viewChild.findViewById(R.id.notify_fixed_break_go);
        final Message.Button button = message.button == null || message.button.size() == 0 ? null
                : message.button.get(0);

        mTitle.setText(message.title);
        mContent.setText(message.txt);
        mScore.setText(message.cnt);
        mMoney.setText(message.cnt);

        if (button == null) {
            mButtonView.setVisibility(View.GONE);
        } else {
            mButtonView.setVisibility(View.VISIBLE);
            mButtonView.setText(button.title);
            mButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo 按钮点击
                    NotifyButtonEvent.clickButton(button, mListener);
                }
            });
        }

        viewChild.findViewById(R.id.notify_button_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.colseDialog();
                    }
                });

        return viewChild;

    }


    /** 返回通用卡片的View */
    private View getCommonView(Message message, final OnDialogCloseListener mListener) {
        View viewChild = LayoutInflater.from(mContext).inflate(message.getLayoutId(), null);
        TextView mTitle = (TextView) viewChild.findViewById(R.id.notify_fixed_title);
        TextView mContent = (TextView) viewChild.findViewById(R.id.notify_fixed_break_content);
        TextView mScore = (TextView) viewChild.findViewById(R.id.notify_fixed_break_score);
        TextView mMoney = (TextView) viewChild.findViewById(R.id.notify_fixed_break_money);
        Button mButtonView = (Button) viewChild.findViewById(R.id.notify_fixed_break_go);
        final Message.Button button = message.button == null || message.button.size() == 0 ? null
                : message.button.get(0);

        mTitle.setText(message.title);
        mContent.setText(message.txt);
        mScore.setText(message.cnt);
        mMoney.setText(message.cnt);

        if (button == null) {
            mButtonView.setVisibility(View.GONE);
        } else {
            mButtonView.setVisibility(View.VISIBLE);
            mButtonView.setText(button.title);
            mButtonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo 按钮点击
                    NotifyButtonEvent.clickButton(button, mListener);
                }
            });
        }

        viewChild.findViewById(R.id.notify_button_close).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.colseDialog();
                    }
                });

        return viewChild;

    }


    public interface OnDialogCloseListener {

        public void colseDialog();

    }


}
