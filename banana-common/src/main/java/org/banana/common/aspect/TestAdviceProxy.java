package org.banana.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.banana.common.utils.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Desc: AdviceProxy
 * Created by mskj-mohao on 2021/1/5 7:50 PM
 * Copr: © 2020 MSKJ.All rights reserved.
 **/

@Component
@Aspect
@EnableAspectJAutoProxy
public class TestAdviceProxy {
    private static Logger logger = LoggerFactory.getLogger(TestAdviceProxy.class);

    @Pointcut("@annotation(org.banana.common.annotation.PointCutAnno)")
    public void testPointCut() {
    }

    @Before("testPointCut()")
    public void beforeCall(JoinPoint joinPoint) throws InterruptedException {
        Class<?> aClass = joinPoint.getTarget().getClass();
        Object[] args = joinPoint.getArgs();
        logger.info(aClass.getSimpleName());
        PrintUtil.toJsonString(args);
    }
}