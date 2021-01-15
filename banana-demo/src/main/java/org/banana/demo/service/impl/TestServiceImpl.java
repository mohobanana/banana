package org.banana.demo.service.impl;

import org.banana.api.dto.TestFunctionOutDto;
import org.banana.common.annotation.PointCutAnno;
import org.banana.common.utils.PrintUtil;
import org.banana.demo.mapper.TestMapper;
import org.banana.demo.model.Test;
import org.banana.demo.model.TestExample;
import org.banana.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper mapper;

    @PointCutAnno
    public TestFunctionOutDto testMethod(String msg) {
        TestFunctionOutDto outDto = new TestFunctionOutDto();
        System.out.println(msg);
        outDto.setMsg(msg);
        TestExample ex = new TestExample();
        TestExample.Criteria criteria = ex.createCriteria();
        ex.setOrderByClause("orderCode desc limit 1");
        List<Test> tests = mapper.selectByExample(ex);
        PrintUtil.printFields(tests);
        return outDto;
    }
}
