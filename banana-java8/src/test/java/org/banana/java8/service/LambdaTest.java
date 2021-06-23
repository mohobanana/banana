package org.banana.java8.service;

import org.banana.java8.model.Apple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Desc: LambdaTest
 * Created by mskj-mohao on 2021/6/23 11:30 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class LambdaTest {

    /**
     * 常用类
     * Consumer<T>:无返回，对入参处理
     * Supplier<T>:无输入，返回固定参数
     * Function<T,R>:输入T，返回R
     * Predicate<T>:输入T，返回boolean，用作判断
     */

    private static List<Apple> findApple(List<Apple> apples, Predicate predicate) {
        List<Apple> list = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                list.add(apple);
            }
        }
        return list;
    }

    @Test
    public void test1(){
        List<Apple> list = Arrays.asList(
                new Apple("green", 2),
                new Apple("red", 1),
                new Apple("green", 1));
        List<Apple> greenApples = findApple(list, new GreenFilter());

        List<Apple> redApples = findApple(list, (Predicate<Apple>) apple -> "red".equals(apple.getColor()));

        Predicate<Apple> weightAppleFilter = apple -> apple.getWeight() >= 2;
        List<Apple> weightApples = findApple(list, weightAppleFilter);

    }

    /**
     * 方法应用：
     *  对象::方法
     *  类::静态方法
     *  类::实例方法
     * 构造器引用：
     *  类::new
     * 数组引用：
     *  Type[]::new
     */


    private static class GreenFilter implements Predicate<Apple> {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }
}
