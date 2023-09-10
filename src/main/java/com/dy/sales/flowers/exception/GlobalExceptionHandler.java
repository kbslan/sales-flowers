package com.dy.sales.flowers.exception;


import com.dy.sales.flowers.vo.response.HttpResult;
import com.dy.sales.flowers.vo.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 * @author chao.lan
 * @since 2023-05-20
 */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 登录业务异常
     */
    @ExceptionHandler(value = {LoginException.class})
    @ResponseBody
    public HttpResult exceptionHandler(LoginException e) {
        log.error("登录业务异常", e);
        return HttpResult.failed(e.getResultCode());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseBody
    public HttpResult exceptionHandler(BusinessException e) {
        log.error("接口业务异常", e);
        return HttpResult.failed(e.getResultCode());
    }

    /**
     * 其他异常或非检查异常处理
     */
    @ExceptionHandler
    @ResponseBody
    public HttpResult exceptionHandler(Exception e) {
        log.error("接口异常", e);
        return HttpResult.failed(ResultCode.SYS_EXCEPTION);
    }

}
