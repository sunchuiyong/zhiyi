package com.yinggu.config;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 前后端分离有时候会有两次请求，第一次为OPTIONS请求，默认会拦截所有请求，但是第一次请求又获取不到jwt，所以会出错。
         * https://www.cnblogs.com/lyh233/p/14472245.html
         */
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
           // System.out.println("OPTIONS请求，放行");
            return true;
        }


        String token = request.getHeader("token");
        //System.out.println(token);
        if (token == null) {
            throw new IllegalArgumentException("token 不能为空");
        }
        if (!JWTUtil.verify(token)) {
            throw new IllegalArgumentException("token 验证失败，请重新登录！");
        }
        return true;
    }
}
