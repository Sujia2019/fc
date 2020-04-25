package com.easyarch.cache;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisCache {


    @Pointcut("execution(* com.easyarch.dao..*.*(..))") // the pointcut expression
    private void anyOldTransfer() {

    } // the pointcut signature


    @Before("com.ease.archiecture.springaop.aopannotation.AopTest.anyOldTransfer()")
//    @After()
//    @Around()
    private void addBeforePrint(JoinPoint point) {

        Object[] objs = point.getArgs();
        for(Object obj:objs){
            System.out.println(obj.toString());
        }
        System.out.println("add----before");
    }
}
