package com.hello.app.FTP;

/**
 * Created by mozzie on 16-11-14.
 */
public interface ResultCallBack<R> {

    void callBack(String code, R result);
}
