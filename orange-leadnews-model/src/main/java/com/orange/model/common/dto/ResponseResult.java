package com.orange.model.common.dto;

import java.io.Serializable;

import com.orange.model.common.enums.AppHttpCodeEnum;

import lombok.Getter;

/**
 * 通用的结果返回类
 */
@Getter
public class ResponseResult<T> implements Serializable {

    private String host;
    private Integer code;
    private String errorMessage;
    private T data;

    public ResponseResult() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
    }

    public ResponseResult(AppHttpCodeEnum code) {
        this.code = code.getCode();
        this.errorMessage = code.getErrorMessage();
    }

    public ResponseResult(AppHttpCodeEnum code, String errorMessage) {
        this.code = code.getCode();
        this.errorMessage = errorMessage;
    }

    public ResponseResult(T data) {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.data = data;
    }

    /**
     * 操作成功，无数据。
     *
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult<?> okResult() {
        return new ResponseResult<>();
    }

    /**
     * 操作成功，有数据。
     * 
     * @param data 数据
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static <T> ResponseResult<T> okResult(T data) {
        return new ResponseResult<>(data);
    }

    /**
     * 操作失败
     *
     * @param code 状态码
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult<?> errorResult(AppHttpCodeEnum code) {
        return new ResponseResult<>(code);
    }

    /**
     * 操作失败，自定义错误消息。
     *
     * @param code 状态码
     * @param errorMessage 错误消息
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult<?> errorResult(AppHttpCodeEnum code, String errorMessage) {
        return new ResponseResult<>(code, errorMessage);
    }

}
