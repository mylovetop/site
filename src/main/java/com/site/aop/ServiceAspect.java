package com.site.aop;


import com.site.Interceptor.MethodCacheAfterAdvice;
import com.site.Interceptor.MethodCacheAfterAdviceAnnotation;
import com.site.Interceptor.MethodCacheInterceptor;
import com.site.Interceptor.MethodCacheInterceptorAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjie on 2014/11/12.
 * 切面 拦截
 */
@Component
@Aspect
public class ServiceAspect {

    @Autowired
    private MethodCacheInterceptorAnnotation methodCacheInterceptorAnnotation;

    @Autowired
    private MethodCacheAfterAdviceAnnotation methodCacheAfterAdviceAnnotation;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com..service.impl..*find*(..))")
    public void find(){}

    @Pointcut("execution(* com..service.impl..*get*(..))")
    public void get(){}

    @Pointcut("execution(* com..service.impl..*create*(..))")
    public void create(){}

    @Pointcut("execution(* com..service.impl..*update*(..))")
    public void update(){}

    @Pointcut("execution(* com..service.impl..*save*(..))")
    public void save(){}

    @Pointcut("execution(* com..service.impl..*insert*(..))")
    public void insert(){}

    @Pointcut("execution(* com..service.impl..*delete*(..))")
    public void delete(){}

//    @Around("find()")
    public Object aroundFind(ProceedingJoinPoint joinPoint) throws Throwable{
        return methodCacheInterceptorAnnotation.cache(joinPoint);
    }

//    @After("create()")
    public void afterReturnCreate(JoinPoint joinPoint) throws Throwable{
        methodCacheAfterAdviceAnnotation.afterReturning(joinPoint);
    }


}
