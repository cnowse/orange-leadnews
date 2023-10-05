package com.orange.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orange.model.common.dto.ResponseResult;
import com.orange.model.common.enums.AppHttpCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionCatch {

    /**
     * 其他异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        log.error("catch exception:{}", e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 自定义异常捕获
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult exception(CustomException e, HttpServletRequest req) {
        log.error("Error when :[{} {}] msg={}", req.getMethod(), req.getRequestURI(),
                e.getMessage() == null ? e.getCode() : e.getMessage());
        if (e.getMessage() != null) {
            return ResponseResult.errorResult(e.getCode(), e.getMessage());
        }
        return ResponseResult.errorResult(e.getCode());
    }

}
