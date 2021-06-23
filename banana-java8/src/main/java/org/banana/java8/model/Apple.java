package org.banana.java8.model;

/**
 * Desc: Apple
 * Created by mskj-mohao on 2021/6/23 9:47 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class Apple {

    private String color;
    private long weight;


    public Apple(String color, long weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
