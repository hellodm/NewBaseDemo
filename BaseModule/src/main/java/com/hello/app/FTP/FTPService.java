package com.hello.app.FTP;

import android.util.Log;

import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mozzie on 16-11-13.
 */

public class FTPService {


    /**
     * 获取所有文件从目录
     *
     * @param directory 目录
     * @param action    回调
     */
    public void getImagesFrom(final String directory,
            final Action2<FTPFile[], List<FileBean>> action) {

        Observable.create(new Observable.OnSubscribe<FTPFile[]>() {
            @Override
            public void call(Subscriber<? super FTPFile[]> subscriber) {
                try {
                    FTPFile[] files = action.onAction();
                    if (files == null) {
                        action.onSuccess(FTPCode.error_directory_empty, null);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onNext(files);
                        subscriber.onCompleted();
                    }
                } catch (IllegalArgumentException e) {
                    subscriber.onError(e);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .flatMap(new Func1<FTPFile[], Observable<FTPFile>>() {
                    @Override
                    public Observable<FTPFile> call(FTPFile[] ftpFiles) {
                        return Observable.from(ftpFiles);
                    }
                })
                .filter(new Func1<FTPFile, Boolean>() {
                    @Override
                    public Boolean call(FTPFile ftpFile) {
                        return ftpFile != null;
                    }
                })
                .map(new Func1<FTPFile, FileBean>() {
                    @Override
                    public FileBean call(FTPFile ftpFile) {
                        FileBean bean = new FileBean();
                        bean.setName(ftpFile.getName());
                        bean.setPath(directory + "/" + ftpFile.getName());
                        bean.setTime(ftpFile.getTimestamp().getTimeInMillis() / 1000);
                        if (ftpFile.isFile()) {
                            bean.setType(1);
                        } else if (ftpFile.isDirectory()) {
                            bean.setType(2);
                        }

                        return bean;

                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io()) //设置操作在io线程
                .subscribe(new Subscriber<List<FileBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        action.onSuccess(FTPCode.error_other, null);
                    }

                    @Override
                    public void onNext(List<FileBean> fileBeen) {
                        action.onSuccess(FTPCode.code_success, fileBeen);
                    }
                });

    }

    /**
     * copy文件
     */
    public void copyFileToMobile(final Action<String> action) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(action.onAction());
                    subscriber.onCompleted();
                } catch (IllegalArgumentException e) {
                    action.onSuccess(e.getMessage());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io()) //设置操作在io线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        action.onSuccess(FTPCode.error_other);
                    }

                    @Override
                    public void onNext(String code) {
                        action.onSuccess(code);
                    }
                });
    }

    /**
     * copy文件
     */
    public void get64(final Action<String> action) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(action.onAction());
                    subscriber.onCompleted();
                } catch (IllegalArgumentException e) {
                    action.onSuccess(e.getMessage());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//设置观察在主线程
                .subscribeOn(Schedulers.io()) //设置操作在io线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        action.onSuccess(FTPCode.error_other);
                    }

                    @Override
                    public void onNext(String code) {
                        action.onSuccess(code);
                    }
                });
    }

    public interface Action<P> {

        P onAction() throws Exception;

        void onSuccess(P p);
    }


    public interface Action2<P, R> {

        P onAction() throws Exception;

        void onSuccess(String code, R r);


    }


}
