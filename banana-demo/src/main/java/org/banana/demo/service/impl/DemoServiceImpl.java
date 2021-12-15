package org.banana.demo.service.impl;

import org.banana.api.dto.TestFunctionOutDto;
import org.banana.demo.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public TestFunctionOutDto testMethod(String msg) {
        TestFunctionOutDto outDto = new TestFunctionOutDto();
        outDto.setMsg(msg);
        return outDto;
    }
}
