package com.hello.app.RxAndroid;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2016/11/7
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class RxView {

    @NonNull
    public static Observable<Void> clicks(@NonNull View view) {
        checkNotNull(view, "view == null");
        return Observable.create(new ViewClickOnListener(view));
    }

    private static <T> T checkNotNull(T value, String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    public static void clickRepeat(@NonNull View view, long time,
            final View.OnClickListener onClickListener) {
        checkNotNull(view, "view == null");
        checkNotNull(onClickListener, "onClickListener == null");
        Observable.create(new ViewClickOnListener<View>(view))
                .throttleFirst(time, TimeUnit.MILLISECONDS).subscribe(
                new Action1<View>() {
                    @Override
                    public void call(View view) {
                        if (onClickListener != null) {
                            onClickListener.onClick(view);
                        }
                    }
                });
    }


}
