package org.banana.demo.service.impl;

import org.banana.demo.service.TestService;
import org.banana.common.utils.PrintUtil;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    public String testMethod(String msg) {
        PrintUtil.printInfoLog(msg);
        return msg;
    }
}
