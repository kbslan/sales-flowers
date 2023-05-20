package com.dy.sales.flowers.utils;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequest工具类
 *
 */
@Slf4j
public class HttpCtxUtil {


    /**
     * 判断请求类型
     */
    public static boolean isJsonType(HttpServletRequest request){
        //rest返回json
        String url = request.getRequestURI();
        if(url != null && !"".equals(url)){
            if(url.startsWith("/rest/")){
                return true;
            }
        }
        String type = request.getHeader("Accept");
        if(type != null && (type.contains("application/json") || type.contains("*/*"))){
            return true;
        }else {
            return false;
        }
    }

}
