package com.quanqinle.mysecretary.readexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;

import java.util.Map;

/**
 * 行数据基类
 *
 * @author quanql
 */
public class BaseExcelRow {

    /**
     * 表头所在行
     * 行号从1开始
     */
    @ExcelIgnore
    private final static int HEAD_ROW_NUMBER = 1;

    /**
     * 列数
     * 注意：index从0开始，last_index==列数-1
     */
    @ExcelIgnore
    private final static int COLUMN_LAST_NUMBER = -1;

    /**
     * 期望的表头
     * <p>主要用于表格合法性校验。这里可以只校验必要的字段，即，配置实际excel的表头字段的子集。</p>
     * <p>当为null时，不校验表头</p>
     * <p>key是表头排序，即columnIndex，从0开始；</p>
     * <p>value是表头名，可以忽略前后空格，但必须包含中间空格和换行</p>
     */
    @ExcelIgnore
    private final static Map<Integer, String> HEAD_CHECK_MAP = null;

    public static int getHeadRowNumber() {
        return HEAD_ROW_NUMBER;
    }

    public static int getColumnLastNumber() {
        return COLUMN_LAST_NUMBER;
    }

    public static Map<Integer, String> getHeadCheckMap() {
        return HEAD_CHECK_MAP;
    }
}
