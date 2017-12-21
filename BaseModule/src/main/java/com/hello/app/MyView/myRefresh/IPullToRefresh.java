/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.hello.app.MyView.myRefresh;

import android.view.View;

import com.hello.app.MyView.myRefresh.RefreshBase.Mode;
import com.hello.app.MyView.myRefresh.RefreshBase.State;
import com.hello.app.MyView.refresh.base.PullToRefreshBase;

public interface IPullToRefresh<T extends View> {


    public Mode getCurrentMode();

    public Mode getMode();

    public void setMode(Mode mode);

    public void getState(State state);

    public boolean isRefreshing();

    public boolean isPullToRefreshEnabled();


    public void setOnPullEventListener(PullToRefreshBase.OnPullEventListener<T> listener);

    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener<T> listener);

}