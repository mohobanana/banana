package org.banana.demo.function;

import org.banana.api.dto.TestFunctionInDto;
import org.banana.api.dto.TestFunctionOutDto;
import org.banana.common.annotation.Function;
import org.banana.common.annotation.Functions;
import org.banana.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;

@Functions
public class TestFunction {
    @Autowired
    private TestService testService;

    @Function
    public TestFunctionOutDto testFunction(TestFunctionInDto inDto){
        return testService.testMethod(inDto.getMsg());
    }
}
