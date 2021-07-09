package com.quanqinle.mysecretary.entity;

/**
 * Result code
 * {@link https://developer.mozilla.org/en-US/docs/Web/HTTP/Status}
 *
 * @author quanql
 * @version 2021/3/15
 */
public enum ResultCode {

    /**
     * Successful responses (200–299)
     */
    SUCCESS(200, "OK"),
    /**
     * Client errors (400–499)
     */
    FAIL(400, "Client Error"),
    /**
     * Server errors (500–599)
     */
    ERROR(500, "Server Error");

    /**
     * result code
     */
    public Integer code;
    /**
     * result message
     */
    public String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
