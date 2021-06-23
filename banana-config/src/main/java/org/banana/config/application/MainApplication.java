package org.banana.config.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Desc: MainApplication
 * Created by mskj-mohao on 2021/4/13 7:53 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "org.banana.demo")
//扫描与web相关的注解@WebFilter、@WebServlet、@WebListener
@ServletComponentScan(basePackages = "org.banana.config.servlet")
@MapperScan(basePackages = {"org.banana.demo.mapper"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}