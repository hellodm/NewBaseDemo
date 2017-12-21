package com.hello.app.Activity;

import android.app.Activity;
import android.os.Bundle;


import com.hello.app.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解研究的Activity
 * <br/><font color="#FF0000">换行</font>
 */

@CommentActivity.Test(name = "这是name秒速")
public class CommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


    }


    @Target(ElementType.TYPE)            //@Target 用于表明下面注解应该作用在哪里
    @Retention(RetentionPolicy.RUNTIME)    //@Retention 用于表明下面注解应该保留在哪里
    public @interface Test {


        String name();

    }


}
