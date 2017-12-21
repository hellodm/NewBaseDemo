package com.hello.app.MyView.recyclerView;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/9
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
