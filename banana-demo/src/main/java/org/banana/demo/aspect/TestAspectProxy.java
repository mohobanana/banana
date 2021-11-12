package org.banana.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.banana.common.annotation.PointCutAnno;
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
@EnableAspectJAutoProxy()
public class TestAspectProxy {
    private static Logger logger = LoggerFactory.getLogger(TestAspectProxy.class);

    @Pointcut("@annotation(org.banana.common.annotation.PointCutAnno)")
    public void testPointCut() {
    }

//    @Before("testPointCut()")
//    public void beforeCall(JoinPoint joinPoint) throws InterruptedException {
//        Class<?> aClass = joinPoint.getTarget().getClass();
//        Object[] args = joinPoint.getArgs();
//        logger.info(aClass.getSimpleName());
//        JSON.toJSONString(args, SerializerFeature.PrettyFormat);
//    }

    @Around("testPointCut()")
    public void aroundCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> aClass = joinPoint.getTarget().getClass();
        Object[] args = joinPoint.getArgs();
        ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(PointCutAnno.class).tradeType();
        logger.info(aClass.getSimpleName());
        logger.info(JSON.toJSONString(args, SerializerFeature.PrettyFormat));
        joinPoint.proceed();
    }

//    @Pointcut("execution(* org.banana.demo.*())")
//    public void allPointCut() {
//    }
//    @Before("allPointCut()")
//    public void debugCall(JoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        logger.info(signature.getClass()+"."+signature.getName()+ " executing...");
//    }
}