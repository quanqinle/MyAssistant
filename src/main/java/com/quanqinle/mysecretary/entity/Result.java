package com.quanqinle.mysecretary.entity;

import java.io.Serializable;

/**
 * @author quanql
 * @version 2021/3/15
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应业务状态
     * 200：成功
     * 400：客户端输入参数有误导致响应失败
     * 500：服务器错误
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应中的数据
     */
    private T data;
    /**
     * PageInfo info
     */
    private PageInfo pageInfo;

    public Result() {
    }

    public Result(Integer code, T data, String message, PageInfo pageInfo) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(data, ResultCode.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data, String msg) {
        return success(data, msg, null);
    }

    public static <T> Result<T> success(T data, String msg, PageInfo pageInfo) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS.getCode())
                .setData(data)
                .setMessage(msg)
                .setPage(pageInfo);
    }

    public static <T> Result<T> fail() {
        return fail(null);
    }

    public static <T> Result<T> fail(T data) {
        return fail(data, ResultCode.FAIL.getMessage(), null);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return fail(data, msg, null);
    }

    public static <T> Result<T> fail(T data, String msg, PageInfo pageInfo) {
        return fail(ResultCode.FAIL.getCode(), data, msg, pageInfo);
    }

    public static <T> Result<T> fail(Integer code, T data, String msg, PageInfo pageInfo) {
        return new Result<T>()
                .setCode(code)
                .setData(data)
                .setMessage(msg)
                .setPage(pageInfo);
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public PageInfo getPage() {
        return pageInfo;
    }

    public Result<T> setPage(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
}
