package com.dy.sales.flowers.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Slf4j
public class CookieUtils {

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals(cookieName)) {
                return cooky;
            }
        }
        return null;
    }

    public static boolean clearCookieByName(
            HttpServletRequest request,
            HttpServletResponse response,
            String cookieName,
            String domain,
            String path) {
        boolean result = false;
        Cookie ck = findCookieByName(request, cookieName);
        if (ck != null) {
            result = clearCookie(response, cookieName, domain, path);
        }
        return result;
    }

    public static Cookie findCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals(cookieName)) {
                return cooky;
            }
        }
        return null;
    }

    private static boolean clearCookie(
            HttpServletResponse response,
            String cookieName,
            String domain,
            String path) {
        boolean result = false;
        try {
            Cookie cookie = new Cookie(cookieName, "");
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            cookie.setPath(path);
            response.addCookie(cookie);
            log.info("clear cookie " + cookieName);
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement aStackTrace : stackTrace) {
                log.debug("clearCookie:" + aStackTrace.toString());
            }
            result = true;
        } catch (Exception e) {
            log.error("clear cookie " + cookieName + " throw exception:{}", e.toString());
        }
        return result;
    }

    public static void set(HttpServletResponse response, String key, String value, String domain,
                           String path, int maxAge, boolean isHttpOnly, boolean secure) {
        Cookie cookie = new Cookie(key, value);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setSecure(secure);
        response.addCookie(cookie);
    }

    public static void setByMap(HttpServletResponse response, Map<String, String> kayValMap, String domain,
                                String path, int maxAge, boolean isHttpOnly, boolean secure) {
        for (String key : kayValMap.keySet()) {
            set(response, key, kayValMap.get(key), domain, path, maxAge, isHttpOnly, secure);
        }
    }
}

