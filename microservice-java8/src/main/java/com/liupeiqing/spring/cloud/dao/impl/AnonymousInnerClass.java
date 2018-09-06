package com.liupeiqing.spring.cloud.dao.impl;

import com.liupeiqing.spring.cloud.dao.InterfaceDemo;

/**
 * @author liupeiqing
 * @data 2018/8/18 15:11
 * 使用匿名内部类测试  实现一个接口
 *
 * 匿名内部类由于没有名字，所以它的创建方式有点儿奇怪。创建格式如下：
 * new 父类构造器（参数列表）|实现接口（）
 *     {
 *      //匿名内部类的类体部分
 *     }
 */
public class AnonymousInnerClass {


    public static void main(String[] args) {
        AnonymousInnerClass anonymousInnerClass = new AnonymousInnerClass();
        /**new InterfaceDemo() 其实就是内部类
         *
         * 1. 对于匿名内部类的使用它是存在一个缺陷的，就是它仅能被使用一次，创建匿名内部类时它会立即创建一个该类的实例，该类的定义会立即消失，
         *  所以匿名内部类是不能够被重复使用。对于上面的实例，
         *  如果我们需要对test()方法里面内部类进行多次使用，建议重新定义类，而不是使用匿名内部类。
         *  2.当所在的方法的形参需要被内部类里面使用时，该形参必须为final。
         *  因为外部传入到内部的参数 本质上是一个东西，但是在编译的时候，内部的参数是可以被改变的，因此这不合理  所以我们要用final修饰
         *
         */
        anonymousInnerClass.test(new InterfaceDemo() {
            String string = "hahaah";
            @Override
            public String getUserName() {
                return string;
            }
        });
    }

    public void test(InterfaceDemo interfaceDemo){
        interfaceDemo.getUserName();
    }
}
