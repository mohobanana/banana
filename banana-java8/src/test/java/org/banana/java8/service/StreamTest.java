package org.banana.java8.service;

import org.banana.java8.model.Apple;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Desc: StreamTest
 * Created by mskj-mohao on 2021/6/23 11:30 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class StreamTest {

    @Test
    public void test1(){
        List<Apple> list = Arrays.asList(
                new Apple("green", 2),
                new Apple("red", 1),
                new Apple("green", 1));

        //1 通过 Collection 提供的stream()或parallelStream()
        Stream<Apple> stream1 = list.stream();

        //2 通过Arrays 中的静态方法stream()
        Apple[] apples = new Apple[10];
        Stream<Apple> stream2 = Arrays.stream(apples);

        //3 通过Stream 的静态方法of
        Stream<Apple> stream3 = Stream.of(new Apple("green", 2),
                new Apple("red", 1),
                new Apple("green", 1));

        //4 创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, x -> x + 2);
        stream4.limit(10).forEach(System.out::println);
        //生成
        Stream<Double> stream5 = Stream.generate(()->Math.random()*10);
        stream5.limit(10).forEach(System.out::println);
    }
}
