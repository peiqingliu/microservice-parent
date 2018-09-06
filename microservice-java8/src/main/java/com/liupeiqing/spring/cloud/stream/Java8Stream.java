package com.liupeiqing.spring.cloud.stream;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author liupeiqing
 * @data 2018/8/18 15:47
 * Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
 *
 * Stream 使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
 *
 * Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
 *
 * 这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。
 *
 * 元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。
 *
 * | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
 *
 * 什么是 Stream？
 * Stream（流）是一个来自数据源的元素队列并支持聚合操作
 *
 * 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
 *
 * 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
 *
 * 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
 *
 * 生成流
 * 在 Java 8 中, 集合接口有两个方法来生成流：
 *
 * stream() − 为集合创建串行流。
 *
 * parallelStream() − 为集合创建并行流。
 *
 * List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
 * List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
 */
public class Java8Stream {

    public static void main(String[] args) {
        //1
        List<String> strings = Arrays.asList("a","b","c","d","");
        List<String> filted = strings.stream().filter(string -> !StringUtils.isEmpty(string)).collect(Collectors.toList());
       // System.out.print(filted);

        //forEach  Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。
        Random random = new Random();
       // random.ints().limit(10).forEach(System.out ::print);

        //map  map 方法用于映射每个元素到对应的结果，
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> newNumbers = numbers.stream().map(i -> i*2).collect(Collectors.toList());
       // List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.println(newNumbers);

        //filter 方法用于通过设置的条件过滤出元素

        //limit 方法用于获取指定数量的流。

        //sorted 方法用于对流进行排序

    }
}
