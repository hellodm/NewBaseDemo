package com.hello.app.java;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述--继承和组合的构造方式的执行顺序
 *
 * 子类先执行父类的组合属性==父类的构造方法==子类的组合属性==父类的构造方法
 *
 * @author dong
 * @Time: 2015/2/5
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class Sandwich extends PortableLunch {

    private Bread mBread = new Bread("Sandwich");

    private Cheese mCheese = new Cheese();

    Sandwich() {
        System.out.println("Sandwich()");
    }

    public static void main(String[] args) {

        new Sandwich();
    }


}

class Meal {

    Meal() {
        System.out.println("Meal()");
    }
}

class Cheese {

    Cheese() {
        System.out.println("Cheese()");
    }
}

class Bread {

    Bread(String name) {
        System.out.println("Bread==" + name);
    }
}

class Lunch extends Meal {

    Lunch() {
        System.out.println("Lunch()");
    }
}

class PortableLunch extends Lunch {

    private Bread mBread = new Bread("PortableLunch");

    PortableLunch() {
        System.out.println("PortableLunch()");
    }
}




