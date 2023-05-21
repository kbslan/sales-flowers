package com.dy.sales.flowers.vo.enums;

import com.dy.sales.flowers.vo.constant.SsoConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 和前端交互的错误码定义
 *
 * @author chao.lan
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS("0000", "success"),
    /**
     * 异常状态码
     */
    SYS_EXCEPTION("1000", "系统异常"),
    PARAM_EXCEPTION("1001", "参数缺失"),
    MOBILE_REGISTERED("1002", "手机号已被注册"),
    USER_NAME_REGISTERED("1003", "账号名称已被使用"),
    USER_NOT_FOUND("1004", "账号不存在"),
    USER_PASSWORD_ERROR("1005", "密码错误"),
    USER_DISABLED("1006", "账号被禁用，请联系管理员"),
    LOGIN_FAILED("1007", "登陆失败"),
    RESET_PASSWORD_NOT_EQUAL("1008", "重置密码失败, 旧密码输入错误"),
    RESET_PASSWORD_FAILED("1009", "重置密码失败"),
    COOKIE_NOT_EXIST("1010", "cookieName: " + SsoConstants.COOKIE_NAME + "不存在"),
    COOKIE_CHECK_FAILED("1011", "token校验失败"),
    COOKIE_EXPIRED("1012", "token已过期，请重新登陆"),
    NOT_LOGIN_IP("1013", "当前设备ip非登录ip"),
    USER_AGENT_ERROR("1014", "userAgent错误"),
    REQUEST_ILLEGAL("1015", "权限不足")


    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;


}
