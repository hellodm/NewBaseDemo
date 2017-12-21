package com.hello.app.test.presenter;

import com.hello.app.test.view.ILoginView;

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
public class LoginPresenter {

    ILoginView mILoginView;

    LoginPresenter() {

    }

    public LoginPresenter(ILoginView iView) {
        mILoginView = iView;
    }


    public void login(String userId) {

        //网络或者数据库的查询
        mILoginView.loginSuccess(userId, "小明");


    }


}
