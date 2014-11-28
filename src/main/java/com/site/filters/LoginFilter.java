package com.site.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangjie on 2014/11/7.
 * 登录过滤器
 */
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String[] noFilter = new String[]{"login.html", "logout.html"};
        //请求的uri
        String uri = request.getRequestURI();

        //会员中心
        if (uri.indexOf("member/") != -1){
            //是否过滤
            boolean doFilter = true;
            for (String s: noFilter){
                if (uri.indexOf(s) != -1){
                    //如果uri中包含noFilter中内容 则不进行过滤
                    doFilter = false;
                    break;
                }
            }
            if (doFilter){
                Object obj = request.getSession().getAttribute("user");
                if (null == obj){
                    response.sendRedirect("/member/login.html");
                }else {
                    filterChain.doFilter(request, response);
                }
            }else {
                filterChain.doFilter(request, response);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }


}
