package org.banana.config;

import org.banana.config.application.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Desc: TestDemo
 * Created by mskj-mohao on 2021/12/15 11:20 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class TestDemo {

    @Test
    public void printTest(){
        System.out.println("1");
    }
}
