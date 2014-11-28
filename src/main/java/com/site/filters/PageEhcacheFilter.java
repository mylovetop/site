package com.site.filters;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by zhangjie on 2014/11/13.
 * 页面缓存过滤器
 */
public class PageEhcacheFilter extends SimplePageCachingFilter{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final static String FILTER_URL_PATTERNS = "patterns";
    private static String[] cacheURLs;

    private void init() throws CacheException{
        String patterns = filterConfig.getInitParameter(FILTER_URL_PATTERNS);
        cacheURLs = StringUtils.split(patterns, ",");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException, LockTimeoutException, Exception {
        if (cacheURLs == null){
            init();
        }
        String url = request.getRequestURI();
        boolean flag = false;
        if (cacheURLs != null && cacheURLs.length > 0){
            for (String cacheURL: cacheURLs){
                if (url.contains(cacheURL.trim())){
                    flag = true;
                    break;
                }
            }
        }
        if (flag){
            String query = request.getQueryString();
            if (null != query){
                query = "?" + query;
            }
            logger.debug("当前请求被缓存：{}", url + query);
            super.doFilter(request, response, chain);
        }else {
            chain.doFilter(request, response);
        }

    }

    @SuppressWarnings("unchecked")
    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
        logRequestHeaders(request);
        final Enumeration accepted = request.getHeaders(header);
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf(value) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see net.sf.ehcache.constructs.web.filter.Filter#acceptsGzipEncoding(javax.servlet.http.HttpServletRequest)
     * <b>function:</b> 兼容ie6/7 gzip压缩
     */
    @Override
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
        boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
        boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
        return acceptsEncoding(request, "gzip") || ie6 || ie7;
    }
}
