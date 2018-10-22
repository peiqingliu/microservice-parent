package com.liupeiqing.rpc.provider;

/**
 * 服务提供者，运行在服务器端，提供服务接口定义与服务实现类
 * @author liupeqing
 * @date 2018/10/17 20:33
 */
public class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
