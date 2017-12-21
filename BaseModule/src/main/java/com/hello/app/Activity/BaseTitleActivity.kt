package com.hello.app.Activity

import android.app.Activity
import android.os.Bundle

import com.hello.app.MyView.MyTitleLinearLayout
import com.hello.app.R

import butterknife.ButterKnife
import butterknife.InjectView
import butterknife.OnClick

class BaseTitleActivity : Activity() {

    @InjectView(R.id.titleView_lin)
    internal var mLinView: MyTitleLinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_title)
        ButterKnife.inject(this)
    }


    @OnClick(R.id.title_center)
    fun addCenter() {

        mLinView!!.addText()


    }

    @OnClick(R.id.title_left)
    fun addLeft() {

        mLinView!!.addBackButton()


    }

    @OnClick(R.id.title_right)
    fun addRight() {


        mLinView!!.addMenuButton()

    }

}
