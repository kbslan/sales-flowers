package com.dy.sales.flowers.exception;

import com.dy.sales.flowers.vo.enums.ResultCode;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {
    private static final long serialVersionUID = 8428906561668002059L;

    private final ResultCode resultCode;

    public LoginException(ResultCode resultCode) {
        super(resultCode.getDesc());
        this.resultCode = resultCode;
    }

}