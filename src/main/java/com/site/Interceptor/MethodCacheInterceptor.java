package com.site.Interceptor;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by zdsoft on 14-7-7.
 * 使用任意一个现有开源Cache Framework，要求可以Cache系统中Service或则DAO层的get/find等方法返回结果，如果数据更新（使用Create/update/delete方法），则刷新cache中相应的内容。
 */
public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {

    private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);

    private Cache cache;


    public void setCache(Cache cache){
        this.cache = cache;
    }

    public MethodCacheInterceptor() {
        super();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");
    }

    /**
     * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值，
     * 否则，返回数据库查询结果，并将查询结果放入cache
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String targetName = methodInvocation.getThis().getClass().getName();
        String methodName = methodInvocation.getMethod().getName();

        Object[] arguments = methodInvocation.getArguments();
        Object result;

        logger.debug("Find object from cache is " + cache.getName());

        String cacheKey = getCacheKey(targetName, methodName, arguments);
        System.out.println("cacheKey:" + cacheKey);
        Element element = cache.get(cacheKey);
        if (null == element){
            logger.debug("Hold up method , Get method result and create cache........!");
            result = methodInvocation.proceed();//获取所拦截方法的返回值
//            System.out.println("result:" + result);
            element = new Element(cacheKey, (Serializable)result);

            cache.put(element);
        }
        System.out.println("element:"+element);
        System.out.println("element.getObjectValue()：" + element.getObjectValue());
        return element.getObjectValue();
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
}
