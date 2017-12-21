package com.hello.app.MyView.loading;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2016/1/5
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public interface ILoadingLayout<E> {

    public void pullProgress(int progress);

    public void notifyState(E state);


}
