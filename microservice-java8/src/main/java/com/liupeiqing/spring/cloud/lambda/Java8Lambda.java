package com.liupeiqing.spring.cloud.lambda;

import com.liupeiqing.spring.cloud.dao.MathOperation;

import java.util.Map;

/**
 * @author liupeiqing
 * @data 2018/8/18 14:56
 *
 * Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
 *
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 *
 * 使用 Lambda 表达式可以使代码变的更加简洁紧凑。
 *
 * lambda 表达式的语法格式如下：
 *
 * (parameters) -> expression
 * 或
 * (parameters) ->{ statements; }
 */
public class Java8Lambda {

    public static void main(String[] args) {
        Java8Lambda java8Lambda = new Java8Lambda();

        /**
         * lambda
         */
        //参数声明类型 int
        MathOperation addition  = (int a,int b) -> a+b;

        // 不用类型声明  {}去掉了  只有一个执行体
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (a,b) ->{
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        /**
         * 匿名内部类 实现接口
         */
        MathOperation mathOperation2 = new MathOperation() {
            @Override
            public int operation(int a, int b) {
                return a+b;
            }
        };

        System.out.println("10 + 5 = " + java8Lambda.operate(10, 5, addition ));
        System.out.println("10 + 5 = " + java8Lambda.operate(10, 5, mathOperation2));
        System.out.println("10 - 5 = " + java8Lambda.operate(10, 5, subtraction));
        System.out.println("10 * 5 = " + java8Lambda.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + java8Lambda.operate(10, 5, division));


    }
    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
}
