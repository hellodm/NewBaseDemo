package com.hello.app.Util;

import android.view.View;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/4/29
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public abstract class OnClickEvent implements View.OnClickListener {

    public static long lastTime;

    public abstract void singleClick(View v);


    @Override
    public void onClick(View v) {

    }


    public boolean onDoubleClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time < 500) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }

}
