package com.hello.app.TestClass;

import android.os.Build;

import com.hello.app.Base.BaseBuilder;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @version 3.3.0
 * @Time: 2014/10/29
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class TestBuild {

    private int a;

    private int b;


    private TestBuild(Builder builder) {

        a = builder.a;
        a = builder.b;

    }


    public static class Builder implements BaseBuilder {

        private final int a;

        private final int b;


        private int c;

        private int d;

        private int e;

        private int f;


        public Builder(int a, int b) {
            this.a = a;
            this.b = b;

        }


        public Builder c(int val) {

            this.c = val;

            return this;
        }


        public Builder d(int val) {

            this.d = val;

            return this;
        }

        public Builder e(int val) {

            this.e = val;

            return this;
        }

        public Builder f(int val) {

            this.f = val;

            return this;
        }


        @Override
        public TestBuild build() {
            return new TestBuild(this);
        }
    }

    public void main(String[] attrs) {

        TestBuild test = new Builder(1, 2).c(3).d(4).e(5).f(6).build();


    }


}
