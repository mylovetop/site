package com.site.Interceptor;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zhangjie on 2014/11/28.
 * 使用任意一个现有开源Cache Framework，要求可以Cache系统中Service或则DAO层的get/find等方法返回结果，如果数据更新（使用Create/update/delete方法），则刷新cache中相应的内容。
 * * 支持注解的 拦截
 */
@Component
public class MethodCacheInterceptorAnnotation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Cache cache;

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public MethodCacheInterceptorAnnotation() {
        super();
    }




    private String getCacheKey(String targetName, String methodName, Object[] arguments){
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if (null != arguments && arguments.length != 0){
            for (int i = 0; i < arguments.length; i++){
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    public Object cache(ProceedingJoinPoint methodInvocation) throws Throwable {
        String targetName = methodInvocation.getThis().getClass().getName();
        String methodName = methodInvocation.toString();


        Object[] arguments = methodInvocation.getArgs();
        Object result;

        logger.debug("Find object from cache is " + cache.getName());

        String cacheKey = getCacheKey(targetName, methodName, arguments);
        logger.debug("cacheKey:" + cacheKey);
        Element element = cache.get(cacheKey);
        if (null == element){
            logger.debug("hold up method, get method result and create cache...");
            result = methodInvocation.proceed();//获取拦截方法的返回值
            element = new Element(cacheKey, (Serializable)result);
            cache.put(element);
        }

        logger.debug("element:{}", element);

        logger.debug("element.getObjectValue():{}", element.getObjectValue());
        return element.getObjectValue();
    }
}
