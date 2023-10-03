package com.orange.common.exception;

import com.orange.model.common.enums.AppHttpCodeEnum;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private AppHttpCodeEnum code;

    public CustomException() {
        super();
    }

    public CustomException(AppHttpCodeEnum code) {
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(AppHttpCodeEnum code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
