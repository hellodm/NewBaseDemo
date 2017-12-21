package com.hello.app.Activity;

import com.hello.app.R;

import java.util.List;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2015/5/7
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class Message {


    public String instruct;//功能编号

    public String title;//标题

    public String txt;//文字内容

    public String cnt; //功能参数

    public int state;//1:未读取2：已读取（关闭）

    public long time;//消息来的时间

    public List<Button> button;

    public class Button {

        public String title;//标题

        public String operate;//操作编号

        public String parm;//操作编号

        public String propert;/*按钮属性: 0: 关闭1: 跳转连接2: 拨打电话 3: 固定功能*/
    }

    public static String COMMON_INSTRUCT = "G0001";

    public static String COMMON_ACCIDENT = "C0001";

    public static String COMMON_BREAK = "C0002";

    public static String COMMON_FAULT = "C0003";

    public static String COMMON_STATE = "C0004";

    public static String COMMON_BEAN = "LB0001";

    /**
     * 获取布局id
     */
    public int getLayoutId() {
        int resultId = 0;

        if (COMMON_BREAK.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_break;
        }
        if (COMMON_FAULT.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_fault;
        }
        if (COMMON_ACCIDENT.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_account;
        }
        if (COMMON_INSTRUCT.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_break;
        }
        if (COMMON_STATE.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_fault;
        }
        if (COMMON_BEAN.equals(instruct)) {
            resultId = R.layout.dialog_notify_fixed_account;
        }

        return resultId;
    }
}
