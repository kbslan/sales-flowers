package com.dy.sales.flowers.config.filter.impl;


import com.dy.sales.flowers.config.filter.SsoFilter;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.constant.SsoConstants;
import com.dy.sales.flowers.vo.response.UserContext;
import com.dy.sales.flowers.vo.response.UserModel;
import com.dy.sales.flowers.service.AuthService;
import com.dy.sales.flowers.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * token有效性验证
 * @author chao.lan
 */
@Slf4j
@Component
@Order(3)
public class AuthTokenFilter implements SsoFilter {
    @Resource
    private AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = CookieUtils.getTokenValue(request, SsoConstants.COOKIE_NAME);

        if (StringUtils.isBlank(token)) {
            throw new LoginException(ResultCode.COOKIE_NOT_EXIST);
        }

        try {
            UserModel userModel = authService.authToken(token);
            if (Objects.isNull(userModel)) {
                log.error("校验token失败，token:{}", token);
                throw new LoginException(ResultCode.COOKIE_CHECK_FAILED);
            }
            if (userModel.isExpired()) {
                log.error("Login cookie is expired, user:{}", userModel);
                throw new LoginException(ResultCode.COOKIE_EXPIRED);
            }
            log.info("解析请求新用户信息 user:{}", userModel);
            UserContext instance = UserContext.getInstance();
            instance.setUserId(userModel.getId());
            instance.setMobile(userModel.getMobile());
            instance.setUserName(userModel.getName());
            instance.setAdmin(userModel.isAdmin());
            instance.setPermissions(userModel.getPermissions());
            instance.setExtInfo(null);
        } catch (Exception e) {
            log.warn("校验token失败,msg:{}", e.getMessage());
            if (e instanceof LoginException) {
                throw e;
            }
            throw new LoginException(ResultCode.COOKIE_CHECK_FAILED);
        }
        filterChain.doFilter(request, response);
    }
}
