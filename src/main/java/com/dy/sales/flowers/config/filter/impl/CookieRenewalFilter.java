package com.dy.sales.flowers.config.filter.impl;


import com.dy.sales.flowers.config.LoginConfig;
import com.dy.sales.flowers.config.filter.SSOFilter;
import com.dy.sales.flowers.vo.constant.SsoConstants;
import com.dy.sales.flowers.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * cookie续期
 */
@Slf4j
@Component
@Order(5)
public class CookieRenewalFilter implements SSOFilter {

    @Resource
    private LoginConfig loginConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie cookie = CookieUtils.getCookie(request, SsoConstants.COOKIE_NAME);
        if (loginConfig.isRenewal()) {
            assert cookie != null;
            //cookie是否续期
            if (loginConfig.isRenewal()) {
                CookieUtils.set(response, SsoConstants.COOKIE_NAME,
                        cookie.getValue(), SsoConstants.COOKIE_DOMAIN,
                        SsoConstants.COOKIE_PATH, SsoConstants.MAX_AGE,
                        Boolean.TRUE, SsoConstants.SECURE);
                addCookie(request, response);
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    private void addCookie(HttpServletRequest request, HttpServletResponse response) {
        //用户
//        Cookie userId = CookieUtils.getCookie(request, "userId");
//        if (Objects.nonNull(userId) && StringUtils.isNotEmpty(userId.getValue())) {
//            CookieUtils.set(response, "userId", userId.getValue(), SsoConstants.COOKIE_DOMAIN,
//                    SsoConstants.COOKIE_PATH, SsoConstants.MAX_AGE, Boolean.TRUE, SsoConstants.SECURE
//            );
//        }

    }
}

