package com.dy.sales.flowers.config.filter.impl;

import com.dy.sales.flowers.config.filter.SsoFilter;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.utils.CookieUtils;
import com.dy.sales.flowers.vo.constant.SsoConstants;
import com.dy.sales.flowers.vo.enums.ResultCode;
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
 * 验证cookie是否存在
 *
 * @author chao.lan
 */
@Slf4j
@Component
@Order(2)
public class RequestCheckFilter implements SsoFilter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = CookieUtils.getTokenValue(request, SsoConstants.COOKIE_NAME);
        //校验cookie是否存在
        if (StringUtils.isBlank(token)) {
            log.error("cookieName:{} 不存在", SsoConstants.COOKIE_NAME);
            throw new LoginException(ResultCode.COOKIE_NOT_EXIST);
        }

        filterChain.doFilter(request, response);
    }
}