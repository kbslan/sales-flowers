package com.dy.sales.flowers.config.filter;

import com.dy.sales.flowers.utils.RedirectUrlUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public interface SSOFilter {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    default String buildRedirectUrl(HttpServletRequest request) {
        return RedirectUrlUtils.buildRedirectUrl(request);
    }
}

