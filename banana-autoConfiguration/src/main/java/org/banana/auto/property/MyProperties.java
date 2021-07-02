package org.banana.auto.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc: MyProperties
 * Created by mskj-mohao on 2021/7/2 10:59 AM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@ConfigurationProperties(
        prefix = "banana",
        ignoreUnknownFields = true
)
public class MyProperties {
    private String name;
    private Integer number;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }
}
