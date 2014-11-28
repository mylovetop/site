package com.site.Interceptor;

import net.sf.ehcache.Cache;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangjie on 2014/11/28.
 * * 拦截器MethodCacheAfterAdvice，
 * 作用是在用户进行create/update/delete操作时来刷新/remove相关cache内容，
 * 这个拦截器实现了AfterReturningAdvice接口，
 * 将会在所拦截的方法执行后执行在public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3)方法中所预定的操作
 * 支持注解的 拦截
 */
@Component
public class MethodCacheAfterAdviceAnnotation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private Cache cache;

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void afterReturning(JoinPoint joinPoint) throws Throwable {
        //获取目标class的全名，如：com.co.cache.test.TestServiceImpl，然后循环cache的key list，remove cache中所有和该class相关的element。
        String className = joinPoint.getThis().getClass().getName();;
        List list = cache.getKeys();
        for (int i = 0; i < list.size(); i++){
            String cacheKey = String.valueOf(list.get(i));
            if (cacheKey.startsWith(className)){
                cache.remove(cacheKey);
                logger.debug("remove cache " + cacheKey);
            }
        }
    }
}
