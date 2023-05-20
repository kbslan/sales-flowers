package com.dy.sales.flowers.config.interceptor;

import com.dy.sales.flowers.config.filter.FilterChainFactory;
import com.dy.sales.flowers.exception.LoginException;
import com.dy.sales.flowers.vo.constant.SsoConstants;
import com.dy.sales.flowers.vo.response.UserContext;
import com.dy.sales.flowers.utils.CookieUtils;
import com.dy.sales.flowers.utils.HttpCtxUtil;
import com.dy.sales.flowers.utils.RedirectUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 */
@Slf4j
@Component
public class SsoLoginInterceptor implements HandlerInterceptor {

    /*   sso拦截器执行filter顺序如下
     *        ↓   ↑
     * ---------------------
     * | CrossOriginFilter |  设置response的跨域支持、响应头信息
     * ---------------------
     *        ↓   ↑
     * ---------------------
     * |RequestCheckFilter |   校验request中是否存在登录cookie
     * ---------------------
     *        ↓    ↑
     * ---------------------
     * |  AuthTokenFilter  |   校验cookie值是否有效——服务端校验，并设置用户上下文&租户ID上下文
     * ---------------------
     *        ↓    ↑
     * ---------------------
     * | IpAndBrowserFilter|   校验登录ip&userAgent与当前请求ip&userAgent是否一致，默认关闭
     * ---------------------
     *        ↓    ↑
     * ---------------------
     * |CookieRenewalFilter|   cookie续期，可设置开关是否续期 {spring.sso.renewal}
     * ---------------------
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            FilterChainFactory.getFilterChain().doFilter(request, response);
        } catch (LoginException e) {
            redirectLoginPage(request, response, e);
            return false;
        } catch (Exception e) {
            log.error("发生未知异常", e);
            return false;
        }
        return true;
    }

    private void redirectLoginPage(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        cleanCookie(request, response);
        if (HttpCtxUtil.isJsonType(request)) {
            PrintWriter out = response.getWriter();
            out.print(e.getMessage());
            out.flush();
            out.close();
        } else {
            String redirectUrl = RedirectUrlUtils.buildRedirectUrl(request);
            response.sendRedirect(redirectUrl);
        }
    }

    private void cleanCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.clearCookieByName(
                request,
                response,
                SsoConstants.COOKIE_NAME,
                SsoConstants.COOKIE_DOMAIN,
                SsoConstants.COOKIE_PATH);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.remove();
    }

}

