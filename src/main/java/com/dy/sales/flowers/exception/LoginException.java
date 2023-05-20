package com.dy.sales.flowers.exception;

import com.dy.sales.flowers.vo.enums.ResultCode;

public class LoginException extends RuntimeException {

    private final ResultCode resultCode;

    public LoginException(ResultCode resultCode) {
        super(resultCode.getDesc());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}