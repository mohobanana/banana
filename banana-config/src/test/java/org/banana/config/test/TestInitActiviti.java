package org.banana.config.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Desc: TestInitActiviti
 * Created by mskj-mohao on 2021/3/22 3:11 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/banana/applicationContext.xml")
public class TestInitActiviti {
    @Autowired
    ProcessEngineConfiguration conf;
    @Test
    public void testDb2() {
        ProcessEngine processEngine = conf.buildProcessEngine();
    }
}