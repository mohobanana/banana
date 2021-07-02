package org.banana.auto.config;

import org.banana.auto.bean.MyBean;
import org.banana.auto.property.MyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc: Myconfiguratin
 * Created by mskj-mohao on 2021/7/2 10:55 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
@Configuration
@EnableConfigurationProperties({MyProperties.class})
@ConditionalOnProperty(
        name = "banana.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class MyConfiguration {
    private final MyProperties properties;

    public MyConfiguration(MyProperties properties){
        this.properties = properties;
    }

    @Bean
    MyBean bananaBean(){
        MyBean myBean = new MyBean();
        myBean.setName(properties.getName());
        myBean.setNumber(properties.getNumber());
        return myBean;
    }

}
