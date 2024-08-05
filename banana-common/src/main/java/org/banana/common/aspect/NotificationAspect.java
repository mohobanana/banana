package org.banana.common.aspect;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.druid.support.logging.Log;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.banana.common.annotation.Notification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class NotificationAspect {

    @Pointcut("@annotation(org.banana.common.annotation.Notification)")
    public void cutController(){}

    @Around(value = "cutController()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Notification annotation = method.getAnnotation(Notification.class);
        String[] person = annotation.person();
        if(person.length!=0){

            System.out.println(new Gson().toJson(person));
        }
        System.out.println(new Gson().toJson(joinPoint.getArgs()));
        Object result = joinPoint.proceed(joinPoint.getArgs());
        System.out.println(new Gson().toJson(result));
    }
}
