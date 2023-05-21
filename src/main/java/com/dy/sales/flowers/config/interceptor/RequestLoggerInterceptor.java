package com.dy.sales.flowers.config.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;


/**
 * 请求参数记录
 * @author chao.lan
 */
@Slf4j
@Component
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("trace", UUID.randomUUID().toString().replace("-", ""));
        Map<String, String[]> paramsMap = request.getParameterMap();
        String uri = request.getRequestURI();
        if ("/ready".equals(uri) || "/health".equals(uri)) {
            if (log.isDebugEnabled()) {
                log.debug("请求参数记录:uri:{},paramsMap:{}", uri, JSON.toJSONString(paramsMap));
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("请求参数记录:uri:{},paramsMap:{}", uri, JSON.toJSONString(paramsMap));
            }
        }
        return super.preHandle(request, response, handler);
    }

}
