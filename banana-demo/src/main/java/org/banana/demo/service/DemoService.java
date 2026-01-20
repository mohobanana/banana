package org.banana.demo.service;

import org.banana.api.dto.TestFunctionOutDto;

public interface DemoService {
    public TestFunctionOutDto testMethod(String msg);

    public String getToken();

    String getGrade(String companyName);
}
