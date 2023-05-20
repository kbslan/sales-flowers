package com.dy.sales.flowers.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 浏览器信息工具类
 *
 */
public class UserAgentUtils {

    /**
     * 混淆浏览器版本信息，取 MD5 中间部分字符
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = Md5Utils.toMD5(request.getHeader("user-agent"));
        if (StringUtils.isEmpty(userAgent)) {
            return null;
        }
        return userAgent.substring(3, 8);
    }

}
