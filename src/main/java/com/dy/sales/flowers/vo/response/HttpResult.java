package com.dy.sales.flowers.vo.response;

import com.dy.sales.flowers.utils.HostIpUtils;
import com.dy.sales.flowers.vo.enums.ResultCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * http 交互对象
 *
 * @author chao.lan
 */
@Getter
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -284033306887605604L;
    /**
     * 业务状态码
     */
    private final String code;


    /**
     * 业务提示
     */
    private final String message;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 实例IP
     */
    private final String ip = HostIpUtils.getLocalHostIP();


    public HttpResult(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getDesc();
        this.data = data;
    }

    public static <T> HttpResult<T> success() {
        return new HttpResult<>(null);
    }

    public static <T> HttpResult<T> success(T data) {
        return new HttpResult<>(data);
    }

    public static <T> HttpResult<T> failed(final ResultCode resultCode) {
        return failed(resultCode.getCode(), resultCode.getDesc());
    }
    public static <T> HttpResult<T> failed(final String code, final String message) {
        return new HttpResult<>(code, message);
    }

}
