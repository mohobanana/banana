package org.banana.java8.service;

import org.banana.java8.model.Apple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * （惰性求值）触发终止操作一次性执行所有中间操作
 * 中间操作:filter,limit,skip,distinct
 * 终止操作:forEach,
 */
public class OperateStreamTest {

    List<Apple> list = Arrays.asList(
            new Apple("green", 2),
            new Apple("green", 2),
            new Apple("red", 1),
            new Apple("green", 1));

    /**
     * filter,limit,skip,distinct
     */
    @Test
    public void test1(){
        list.stream().filter(a->{
            System.out.println("中间操作");
            return "green".equals(a.getColor());
        }).distinct().limit(2).forEach(System.out::println);
    }

    /**
     * map,flatMap
     */
    @Test
    public void test2(){
        //map
        System.out.println("map");
        list.stream().map(this::copyApple).forEach((sm)->sm.forEach(System.out::println));
        //flatMap
        System.out.println("flatMap");
        list.stream().flatMap(this::copyApple).forEach(System.out::println);
    }

    public Stream<Apple> copyApple(Apple apple){
        List<Apple> list = new ArrayList<>();

        list.add(apple);
        list.add(apple);

        return list.stream();
    }

    /**
     * sorted(Comparable c)
     * sorted(Comparator c)
     */
    @Test
    public void test3(){
        list.stream().sorted(Comparator.comparingLong(Apple::getWeight)).forEach(System.out::println);
    }
}
