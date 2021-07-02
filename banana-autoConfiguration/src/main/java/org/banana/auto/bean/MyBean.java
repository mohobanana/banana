package org.banana.auto.bean;

import javax.annotation.PostConstruct;

/**
 * Desc: MyBean
 * Created by mskj-mohao on 2021/7/2 11:09 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
public class MyBean {
    private String name;
    private Integer number;

    @PostConstruct
    private  void init(){
        System.out.println("=======> MyBean被加载");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
