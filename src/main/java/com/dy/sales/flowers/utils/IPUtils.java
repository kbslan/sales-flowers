package com.dy.sales.flowers.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IPUtils {

    /**
     * 系统的本地IP地址
     */
    public static String LOCAL_IP;

    /**
     * 系统的本地服务器名
     */
    public static String HOST_NAME;

    static {
        String ip = "";
        try {
            InetAddress inetAddr = InetAddress.getLocalHost();
            HOST_NAME = inetAddr.getHostName();
            byte[] addr = inetAddr.getAddress();
            for (int i = 0; i < addr.length; i++) {
                if (i > 0) {
                    ip += ".";
                }
                ip += addr[i] & 0xFF;
            }
        } catch (UnknownHostException e) {
            ip = "unknown";
            log.error(e.getMessage());
        } finally {
            LOCAL_IP = ip;
        }
    }


    /**
     * <p>
     * 获取用户真实的IP
     * </p>
     *
     * @param request 当前请求
     * @return IP 地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Real-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("REMOTE-HOST");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                /** 根据网卡取本机配置的IP */
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("IPHelper error." + e.getMessage());
                }
            }
        }
        if (ip != null) {
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            int position = ip.indexOf(",");
            if (position > 0) {
                ip = ip.substring(0, position);
            }
        }
        return ip;
    }

}

