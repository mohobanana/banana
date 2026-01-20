package org.banana.demo.service.impl;

import org.banana.api.dto.TestFunctionOutDto;
import org.banana.common.util.RedisUtils;
import org.banana.demo.service.CozeService;
import org.banana.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private CozeService cozeService;

    @Override
    public TestFunctionOutDto testMethod(String msg) {
        TestFunctionOutDto outDto = new TestFunctionOutDto();
        outDto.setMsg(redisUtils.get(msg));
        return outDto;
    }

    @Override
    public String getToken() {
        return cozeService.getToken();
    }
    @Override
    public String getGrade(String companyName) {
        return cozeService.getGrade(companyName);
    }
}
