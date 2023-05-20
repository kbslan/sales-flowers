package com.dy.sales.flowers.config.filter.impl;

import com.dy.sales.flowers.config.filter.SsoFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域设置
 * @author chao.lan
 */
@Slf4j
@Component
@Order(1)
public class CrossOriginFilter implements SsoFilter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        Pattern pattern = Pattern.compile(SsoConstants.SSO_ORIGIN_REG);
        response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("utf-8");
        String origin = request.getHeader("Origin");
        // 允许GET、POST的外域请求
        response.setHeader("Access-Control-Allow-Methods", "HEAD,GET,POST,PUT,DELETE");
        // 允许请求带cookie到服务器
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        if (StringUtils.isNotBlank(origin) && pattern.matcher(origin).matches()) {
        if (StringUtils.isNotBlank(origin)) {
            // 允许访问的域
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        filterChain.doFilter(request, response);

    }
}
