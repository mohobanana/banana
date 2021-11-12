package org.banana.demo.service.impl;

import org.banana.api.dto.TestFunctionOutDto;
import org.banana.common.annotation.PointCutAnno;
import org.banana.common.utils.PrintUtil;
import org.banana.demo.aspect.TestAspectProxy;
import org.banana.demo.mapper.TestMapper;
import org.banana.demo.model.Test;
import org.banana.demo.model.TestExample;
import org.banana.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private static Logger logger = LoggerFactory.getLogger(TestAspectProxy.class);
    @Autowired
    private TestMapper mapper;

    @Transactional()
    @Override
    @PointCutAnno(tradeType = "test")
    public TestFunctionOutDto testMethod(String msg) {
        TestFunctionOutDto outDto = new TestFunctionOutDto();
        System.out.println(msg);
        outDto.setMsg(msg);
        logger.info("service executing:{}",msg);
        TestExample ex = new TestExample();
        TestExample.Criteria criteria = ex.createCriteria();
        ex.setOrderByClause("orderCode desc limit 1");
        List<Test> tests = mapper.selectByExample(ex);
        PrintUtil.printFields(tests);
        return outDto;
    }
}
