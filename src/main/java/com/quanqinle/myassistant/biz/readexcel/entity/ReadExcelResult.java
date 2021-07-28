package com.quanqinle.myassistant.biz.readexcel.entity;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Excel导入，解析后的结果
 *
 * @author quanqinle
 */
public class ReadExcelResult<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadExcelResult.class);
    /**
     * 表头map。
     * 行index->表头整行
     */
    Map<Integer, Map<Integer, CellData>> rowIdx2HeadMap;

    /**
     * 有效数据map。
     * 行index->数据整行
     */
    Map<Integer, T> rowIdx2RowDataMap;

    /**
     * 错误行列map。
     * 行index->错误列index
     */
    Map<Integer, Set<Integer>> rowIdx2ErrColIdxMap;

    /**
     * 错误行列map。
     * 行号->错误列的列名
     * 可以通过 getRowNum2ErrColNameMap()，自动获取转换后的值
     */
    Map<Integer, Set<String>> rowNum2ErrColNameMap;

    /**
     * 初始化变量，使之都不为空
     */
    public ReadExcelResult() {
        this.rowIdx2HeadMap = new TreeMap<>();
        this.rowIdx2RowDataMap = new TreeMap<>();
        this.rowIdx2ErrColIdxMap = new TreeMap<>();
        this.rowNum2ErrColNameMap = new TreeMap<>();
    }

    /**
     * tips：推荐使用空构造函数
     *
     * @param rowIdx2HeadMap      行号->表头map
     * @param rowIdx2RowDataMap   行号->有效单元格map
     * @param rowIdx2ErrColIdxMap 行号->错误列号map
     */
    public ReadExcelResult(Map<Integer, Map<Integer, CellData>> rowIdx2HeadMap, Map<Integer, T> rowIdx2RowDataMap, Map<Integer, Set<Integer>> rowIdx2ErrColIdxMap) {
        this.rowIdx2HeadMap = rowIdx2HeadMap;
        this.rowIdx2RowDataMap = rowIdx2RowDataMap;
        this.rowIdx2ErrColIdxMap = rowIdx2ErrColIdxMap;
        this.rowNum2ErrColNameMap = new TreeMap<>();
    }

    /**
     * 根据 rowIdx2ErrColIdxMap 生成 rowNum2ErrColNameMap
     * 错误列号转列名称，如，1-->A
     *
     * @return rowIdx2ErrColIdxMap空时，返回null；否则，返回转换后的值
     */
    public Map<Integer, Set<String>> genRowNum2ErrColNameMap(Map<Integer, Set<Integer>> rowIdx2ErrColIdxMap) {

        if (rowIdx2ErrColIdxMap == null || 0 == rowIdx2ErrColIdxMap.size()) {
            return null;
        }

        Map<Integer, Set<String>> rowNum2ErrColNameMap = new TreeMap<>();

        for (Integer rowIdx: rowIdx2ErrColIdxMap.keySet()) {
            Set<Integer> errColIdxMap = rowIdx2ErrColIdxMap.get(rowIdx);

            Set<String> errColNameSet = new TreeSet<>();
            for (Integer colIdx: errColIdxMap) {
                errColNameSet.add(CellReference.convertNumToColString(colIdx));
            }

            rowNum2ErrColNameMap.put(rowIdx + 1, errColNameSet);
        }

        this.rowIdx2ErrColIdxMap = rowIdx2ErrColIdxMap;

        return rowNum2ErrColNameMap;
    }

    /**
     * 打印类的变量值
     */
    public void printFields() {
        LOGGER.info("-- start to print ReadExcelResult object value --");
        if (rowIdx2HeadMap == null || rowIdx2HeadMap.isEmpty()) {
            LOGGER.info("Field [{}] is empty!", "rowIdx2HeadMap");
        } else {
            for (Integer rowIdx: rowIdx2HeadMap.keySet()) {
                Map<Integer, ?> colIdx2CellDataMap = rowIdx2HeadMap.get(rowIdx);
                LOGGER.info("表头：第{}行，{}", rowIdx+1, JSON.toJSONString(colIdx2CellDataMap));
            }
        }

        if (rowIdx2RowDataMap == null || rowIdx2RowDataMap.isEmpty()) {
            LOGGER.info("Field [{}] is empty!", "rowIdx2RowDataMap");
        } else {
            for (Integer rowIdx: rowIdx2RowDataMap.keySet()) {
                LOGGER.info("数据：第{}行，{}", rowIdx+1, JSON.toJSONString(rowIdx2RowDataMap.get(rowIdx)));
            }
        }

        rowNum2ErrColNameMap = getRowNum2ErrColNameMap();
        if (rowNum2ErrColNameMap == null || rowNum2ErrColNameMap.isEmpty()) {
            LOGGER.info("Field [{}] is empty!", "rowNum2ErrColNameMap");
        } else {
            for (Integer rowNum: rowNum2ErrColNameMap.keySet()) {
                LOGGER.info("错误：第{}行={}", rowNum, JSON.toJSONString(rowNum2ErrColNameMap.get(rowNum)));
            }
        }
    }

    public Map<Integer, Map<Integer, CellData>> getRowIdx2HeadMap() {
        return rowIdx2HeadMap;
    }

    public void setRowIdx2HeadMap(Map<Integer, Map<Integer, CellData>> rowIdx2HeadMap) {
        this.rowIdx2HeadMap = rowIdx2HeadMap;
    }

    public Map<Integer, T> getRowIdx2RowDataMap() {
        return rowIdx2RowDataMap;
    }

    public void setRowIdx2RowDataMap(Map<Integer, T> rowIdx2RowDataMap) {
        this.rowIdx2RowDataMap = rowIdx2RowDataMap;
    }

    public Map<Integer, Set<Integer>> getRowIdx2ErrColIdxMap() {
        return rowIdx2ErrColIdxMap;
    }

    public void setRowIdx2ErrColIdxMap(Map<Integer, Set<Integer>> rowIdx2ErrColIdxMap) {
        this.rowIdx2ErrColIdxMap = rowIdx2ErrColIdxMap;
    }

    /**
     * 获取 rowNum2ErrColNameMap <br>
     *
     * 如果 rowNum2ErrColNameMap 为空，则先自动调用 genRowNum2ErrColNameMap(rowIdx2ErrColIdxMap) 赋值
     *
     * @return
     */
    public Map<Integer, Set<String>> getRowNum2ErrColNameMap() {
        if (rowNum2ErrColNameMap == null || rowNum2ErrColNameMap.isEmpty()) {
            return genRowNum2ErrColNameMap(rowIdx2ErrColIdxMap);
        }
        return rowNum2ErrColNameMap;
    }

    public void setRowNum2ErrColNameMap(Map<Integer, Set<String>> rowNum2ErrColNameMap) {
        this.rowNum2ErrColNameMap = rowNum2ErrColNameMap;
    }
}
