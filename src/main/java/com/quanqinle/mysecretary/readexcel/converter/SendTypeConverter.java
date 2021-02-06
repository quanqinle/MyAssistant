package com.quanqinle.mysecretary.readexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.quanqinle.mysecretary.readexcel.ReadExcelConstant;

import java.text.ParseException;

/**
 * 发函方式转换。如，"邮寄"-->”1“
 * @author quanqinle
 */
public class SendTypeConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * Convert excel objects to Java objects
     *
     * @param cellData            Excel cell data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Java object
     * @throws Exception Exception.
     */
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String key = cellData.getStringValue();
        Integer val = ReadExcelConstant.SEND_TYPE_MAP.get(key);
        if (val == null) {
            throw new ParseException("fail to convert " + key + " -> 发函方式", -1);
        }
        return val.toString();
    }

    /**
     * Convert Java objects to excel objects
     *
     * @param value               Java Data.NotNull.
     * @param contentProperty     Content property.Nullable.
     * @param globalConfiguration Global configuration.NotNull.
     * @return Data to put into a Excel
     * @throws Exception Exception.
     */
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

}
