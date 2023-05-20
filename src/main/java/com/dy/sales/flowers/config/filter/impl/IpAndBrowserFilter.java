package com.dy.sales.flowers.config.filter.impl;

import com.alibaba.fastjson.JSON;
import com.dy.sales.flowers.config.LoginConfig;
import com.dy.sales.flowers.config.filter.SsoFilter;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.vo.enums.ResultCode;
import com.dy.sales.flowers.vo.response.UserContext;
import com.dy.sales.flowers.utils.IPUtils;
import com.dy.sales.flowers.utils.UserAgentUtils;
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
import java.io.IOException;


/**
 * ip和浏览器校验
 *
 * @author chao.lan
 */
@Slf4j
@Component
@Order(4)
public class IpAndBrowserFilter implements SsoFilter {

    @Resource
    private LoginConfig loginConfig;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        UserContext userContext = UserContext.getInstance();
        if (!userContext.isLogged()) {
            chain.doFilter(request, response);
            return;
        }
        if (!userContext.isWebLogin()) {
            chain.doFilter(request, response);
            return;
        }
        if (loginConfig.isIpCheck()) {
            String loginIp = userContext.fetchExt("ip");
            String reqIp = IPUtils.getIpAddr(request);
            if (StringUtils.isEmpty(loginIp) || StringUtils.isEmpty(reqIp) || !StringUtils.equals(loginIp, reqIp)) {
                log.error("ip错误，请在登录设备访问,登录ip:{},当前ip:{},userContext:{}", loginIp, reqIp, JSON.toJSONString(userContext));
                throw new LoginException(ResultCode.NOT_LOGIN_IP);
            }
        }

        if (loginConfig.isAgentCheck()) {
            String loginUserAgent = userContext.fetchExt("userAgent");
            String reqUserAgent = UserAgentUtils.getUserAgent(request);
            if (StringUtils.isEmpty(loginUserAgent) || StringUtils.isEmpty(reqUserAgent) || !StringUtils.equals(loginUserAgent, reqUserAgent)) {
                log.error("userAgent错误，请在登录设备访问,登录userAgent:{},当前userAgent:{},userContext:{}", loginUserAgent, reqUserAgent, JSON.toJSONString(userContext));
                throw new LoginException(ResultCode.USER_AGENT_ERROR);
            }
        }
        chain.doFilter(request, response);
    }


}

