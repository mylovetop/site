package com.site.Interceptor;

import net.sf.ehcache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zdsoft on 14-7-7.
 * 拦截器MethodCacheAfterAdvice，
 * 作用是在用户进行create/update/delete操作时来刷新/remove相关cache内容，
 * 这个拦截器实现了AfterReturningAdvice接口，
 * 将会在所拦截的方法执行后执行在public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3)方法中所预定的操作
 */
public class MethodCacheAfterAdvice implements AfterReturningAdvice, InitializingBean {

    private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);

    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public MethodCacheAfterAdvice() {
        super();
    }

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o2) throws Throwable {

        //获取目标class的全名，如：com.co.cache.test.TestServiceImpl，然后循环cache的key list，remove cache中所有和该class相关的element。
        String className = o2.getClass().getName();
        List list = cache.getKeys();
        for (int i = 0; i < list.size(); i++){
            String cacheKey = String.valueOf(list.get(i));
            if (cacheKey.startsWith(className)){
                cache.remove(cacheKey);
                logger.debug("remove cache " + cacheKey);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");
    }
}
