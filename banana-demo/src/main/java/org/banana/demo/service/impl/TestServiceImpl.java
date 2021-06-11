package org.banana.demo.service.impl;

import org.banana.api.dto.TestFunctionOutDto;
import org.banana.demo.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl implements TestService {

    @Transactional()
    @Override
    public TestFunctionOutDto testMethod(String msg) {
        TestFunctionOutDto outDto = new TestFunctionOutDto();
        outDto.setMsg(msg);
        return outDto;
    }
}
