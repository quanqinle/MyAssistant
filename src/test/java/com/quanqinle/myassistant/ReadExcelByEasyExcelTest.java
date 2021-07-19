package com.quanqinle.myassistant;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.quanqinle.myassistant.readexcel.entity.DemoBizExcelRow;
import com.quanqinle.myassistant.readexcel.entity.ReadExcelResult;
import com.quanqinle.myassistant.readexcel.exception.ExcelHeadException;
import com.quanqinle.myassistant.readexcel.listener.MyBizDemoListener;
import com.quanqinle.myassistant.readexcel.listener.ReadAllCellDataThrowExceptionLastListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 试用ali easyexcel的例子
 *
 * @author quanqinle
 * @date
 */
public class ReadExcelByEasyExcelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadExcelByEasyExcelTest.class);

    public static void main(String[] args) {
        String file;
        file = "D:\\Demo1.xlsx";

        read_1(file);

        read_3(file);
    }


    /**
     * 不使用`ReadAllCellDataThrowExceptionLastListener()`，数据解析失败时，则不再解析该行后续单元格，跳到下一行
     *
     * @param file
     */
    public static void read_1(String file) {
        LOGGER.info("-- read_1 --");

        ReadExcelResult<DemoBizExcelRow> readExcelResult = new ReadExcelResult<>();
        ExcelReader excelReader = null;
        try {
            MyBizDemoListener<DemoBizExcelRow> myBizDemoListener = new MyBizDemoListener<>(readExcelResult, null);
            excelReader = EasyExcel.read(file, DemoBizExcelRow.class, myBizDemoListener).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(DemoBizExcelRow.getHeadRowNumber()).build();

            excelReader.read(readSheet);
        } catch (Exception e) {
            if (e instanceof ExcelHeadException) {
                LOGGER.error("Excel模板错误！");
            } else {
                LOGGER.error("异常");
            }
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        LOGGER.info("-- reading excel complete! --");

        readExcelResult.printFields();
    }


    /**
     * 使用自定义单元格解析监听，遇到错误的单元格不中止当前行解析
     *
     * @param file
     */
    public static void read_3(String file) {
        LOGGER.info("-- read_3 --");

        ReadExcelResult<DemoBizExcelRow> readExcelResult = new ReadExcelResult<>();
        ExcelReader excelReader = null;
        try {
            MyBizDemoListener<DemoBizExcelRow> myBizDemoListener = new MyBizDemoListener<>(readExcelResult, DemoBizExcelRow.getHeadCheckMap());
            excelReader = EasyExcel.read(file, DemoBizExcelRow.class, null)
                    .useDefaultListener(false)
                    .registerReadListener(new ReadAllCellDataThrowExceptionLastListener())
                    .registerReadListener(myBizDemoListener)
                    .build();
            ReadSheet readSheet = EasyExcel.readSheet(0).headRowNumber(DemoBizExcelRow.getHeadRowNumber()).build();

            excelReader.read(readSheet);
        } catch (Exception e) {
            if (e instanceof ExcelHeadException) {
                LOGGER.error("Excel模板错误！");
            } else {
                LOGGER.error("异常");
            }
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }

        LOGGER.info("-- reading excel complete! --");
        readExcelResult.printFields();
    }

}
