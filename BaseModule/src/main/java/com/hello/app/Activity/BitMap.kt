package com.hello.app.Activity

import android.os.Bundle
import butterknife.ButterKnife

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2014/12/9
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
class BitMap : MainActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.inject(this)
    }
}

