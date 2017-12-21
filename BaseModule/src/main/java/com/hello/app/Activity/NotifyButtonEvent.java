package com.hello.app.Activity;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * @author Caochong
 * @Time: 2015/5/18
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class NotifyButtonEvent {

    public final static String BUTTON_TO_CLOSE = "G0001001";

    public final static String BUTTON_TO_LINK = "G0001002";

    public final static String BUTTON_TO_CALL = "G0001003";

    public final static String BUTTON_TO_API = "G0001004";

    public final static String BUTTON_TO_BREAK = "C0001001";

    public final static String BUTTON_TO_FAULT = "C0002001";

    public final static String BUTTON_TO_ACCIDENT = "C0003001";

    public final static String BUTTON_TO_STATE = "G0001001";


    public static void clickButton(Message.Button mButton,
            NotifyDialog.OnDialogCloseListener mListener) {

        if (mButton == null) {
            return;
        }

        if (BUTTON_TO_CLOSE.equals(mButton.operate)) {

            mListener.colseDialog();

        } else if (BUTTON_TO_LINK.equals(mButton.operate)) {

        } else if (BUTTON_TO_CALL.equals(mButton.operate)) {

        } else if (BUTTON_TO_API.equals(mButton.operate)) {

        } else if (BUTTON_TO_BREAK.equals(mButton.operate)) {

        } else if (BUTTON_TO_FAULT.equals(mButton.operate)) {

        } else if (BUTTON_TO_ACCIDENT.equals(mButton.operate)) {

        } else if (BUTTON_TO_STATE.equals(mButton.operate)) {

        }


    }


}
