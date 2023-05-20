package com.dy.sales.flowers.config.filter;

import com.dy.sales.flowers.utils.RedirectUrlUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 自定义过滤器接口
 *
 * @author chao.lan
 */
public interface SsoFilter {


    /**
     * 执行过滤逻辑
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    责任链
     * @throws IOException      IO异常
     * @throws ServletException servlet容器异常
     */
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;


    /**
     * 异常后重定向url
     *
     * @param request 请求对象
     * @return url
     */
    default String buildRedirectUrl(HttpServletRequest request) {
        return RedirectUrlUtils.buildRedirectUrl(request);
    }
}

