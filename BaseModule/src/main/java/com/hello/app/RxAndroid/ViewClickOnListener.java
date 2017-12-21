package com.hello.app.RxAndroid;

import android.os.Looper;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;


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
public class ViewClickOnListener<Void> implements Observable.OnSubscribe<Void> {

    final View view;

    ViewClickOnListener(View view) {
        this.view = view;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        checkUiThread();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };
        view.setOnClickListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                view.setOnClickListener(null);
            }
        });
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException(
                    "Must be called from the main thread. Was: " + Thread.currentThread());
        }
    }
}
