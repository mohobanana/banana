package org.banana.demo.function;

import org.banana.api.dto.TestFunctionInDto;
import org.banana.api.dto.TestFunctionOutDto;
import org.banana.common.annotation.Function;
import org.banana.common.annotation.Functions;
import org.banana.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;

@Functions
public class DemoFunction {
    @Autowired
    private DemoService demoService;

    @Function
    public TestFunctionOutDto testFunction(TestFunctionInDto inDto){
        return demoService.testMethod(inDto.getMsg());
    }
    @Function
    public String getToken(TestFunctionInDto inDto){
        return demoService.getToken();
    }
    @Function
    public String getGrade(TestFunctionInDto inDto){
        return demoService.getGrade(inDto.getMsg());
    }
}
