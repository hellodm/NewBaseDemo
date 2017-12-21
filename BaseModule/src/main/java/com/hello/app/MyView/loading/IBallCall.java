package com.hello.app.MyView.loading;

import com.hello.app.MyView.loading.LoadingBall.BallState;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author Caochong
 * @Time: 2016/1/11
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public interface IBallCall<E> {

    void notifyBallState(E state);

}
