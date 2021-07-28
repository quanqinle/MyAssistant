package com.quanqinle.myassistant.biz.readexcel.exception;

/**
 * 表头异常
 * <p>如，用于表格格式与预期不符</p>
 *
 * @author quanqinle
 */
public class ExcelHeadException extends RuntimeException {
    public ExcelHeadException(String message) {
        super(message);
    }
}