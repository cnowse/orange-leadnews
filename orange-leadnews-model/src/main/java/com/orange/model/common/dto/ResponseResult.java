package com.orange.model.common.dto;

import com.orange.model.common.enums.AppHttpCodeEnum;

import lombok.Data;

/**
 * 通用的结果返回类
 */
@Data
public class ResponseResult {

    private String host;
    private Integer code;
    private String errorMessage;
    private Object data;
    private Long currentPage;
    private Long size;
    private Long total;

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

    public ResponseResult(Object data) {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.data = data;
    }

    public ResponseResult(Long currentPage, Long size, Long total, Object data) {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
        this.data = data;
    }

    /**
     * 操作成功，无数据。
     *
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult okResult() {
        return new ResponseResult();
    }

    /**
     * 操作成功，有数据。
     * 
     * @param data 数据
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult okResult(Object data) {
        return new ResponseResult(data);
    }

    /**
     * 操作失败
     *
     * @param code 状态码
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult errorResult(AppHttpCodeEnum code) {
        return new ResponseResult(code);
    }

    /**
     * 操作失败，自定义错误消息。
     *
     * @param code 状态码
     * @param errorMessage 错误消息
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult errorResult(AppHttpCodeEnum code, String errorMessage) {
        return new ResponseResult(code, errorMessage);
    }

    /**
     * 操作成功，返回分页数据。
     *
     * @param currentPage 当前页
     * @param size 当前页数据量
     * @param total 总数据量
     * @param data 数据
     * @return ResponseResult
     * @author Jeong Geol
     */
    public static ResponseResult pageResult(Long currentPage, Long size, Long total, Object data) {
        return new ResponseResult(currentPage, size, total, data);
    }

}
