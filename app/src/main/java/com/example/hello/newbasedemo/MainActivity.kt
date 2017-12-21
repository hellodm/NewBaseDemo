package com.example.hello.newbasedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.inject(this)
    }

    @OnClick(R.id.button_1)
    fun goButton() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
    }

    fun go(a: Int, b: Int, c: Int) {

    }


}

