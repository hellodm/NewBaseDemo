package com.hello.app.RxAndroid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/8
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@ListenerClass(
        targetType = "android.view.View",
        setter = "setOnClickListener",
        type = "android.view.View.OnClickListener",
        method = {@ListenerMethod(
                name = "OnRepeatClick",
                parameters = {"android.view.View"}
        )}
)
public @interface OnRepeatClick {

    int[] value();
}
