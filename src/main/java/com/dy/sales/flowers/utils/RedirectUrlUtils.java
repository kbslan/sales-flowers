package com.dy.sales.flowers.utils;

import com.dy.sales.flowers.vo.constant.SsoConstants;

import javax.servlet.http.HttpServletRequest;

public class RedirectUrlUtils {

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";
    private static final String _HTTP = "http";


    public static String buildRedirectUrl(HttpServletRequest request) {
        return SsoConstants.LOGIN_DOMAIN;
    }
}
