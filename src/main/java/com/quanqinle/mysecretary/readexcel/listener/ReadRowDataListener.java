package com.quanqinle.mysecretary.readexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.quanqinle.mysecretary.readexcel.ReadExcelUtil;
import com.quanqinle.mysecretary.readexcel.entity.ReadExcelResult;
import com.quanqinle.mysecretary.readexcel.exception.ExcelHeadException;
import org.apache.poi.hssf.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 解析行
 *
 * <p>所有自定义解析行的类应继承自这个函数</p>
 * <p>注意！继承时，需覆盖方法invoke()</p>
 *
 * @author quanql
 */
public class ReadRowDataListener<T> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadRowDataListener.class);

    /**
     * 期望的表头
     * <p>用于表格合法性校验。这里可以只校验必要的字段，即，配置实际excel的表头字段的子集。</p>
     * <p>当为null时，不校验表头</p>
     * <p>key是表头排序，即columnIndex，从0开始；</p>
     * <p>value是表头名，可以忽略前后空格，但必须包含中间空格和换行</p>
     */
    protected Map<Integer, String> headCheckMap;
    /**
     * 读取excel后，存入该对象
     */
    protected ReadExcelResult<T> readExcelResult;

    /**
     * 表头行/列
     */
    protected Map<Integer, Map<Integer, CellData>> rowIdx2HeadMap = new TreeMap<>();
    /**
     * 有效行/列信息
     */
    protected Map<Integer, T> rowIdx2RowDataMap = new TreeMap<>();
    /**
     * 错误行/列信息
     */
    protected Map<Integer, Set<Integer>> rowIdx2ErrColIdxMap = new TreeMap<>();
    /**
     * 当前行的错误列index集合
     */
    protected Set<Integer> currentRowErrorColumnIndexSet;

    public ReadRowDataListener() {
        this(new ReadExcelResult<>(), null);
    }

    /**
     *
     * @param readExcelResult 读取excel后，存入该对象
     * @param headCheckMap A map contains columnIndex<->表头名. If null, do not check head
     */
    public ReadRowDataListener(ReadExcelResult<T> readExcelResult, Map<Integer, String> headCheckMap) {
        this.readExcelResult = readExcelResult;
        this.headCheckMap = headCheckMap;
    }

    /**
     * <p>NOTE!</p>
     * <p>MUST OVERRIDE ME, IF YOU EXTEND THIS CLASS</p>
     *
     * When analysis one row trigger invoke function.
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context -
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        currentRowErrorColumnIndexSet = new TreeSet<>();

        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex = readRowHolder.getRowIndex();

        /*
         * 校验excel的必填列是否有值
         */
        ReadExcelUtil.checkNotEmpty(data, context, currentRowErrorColumnIndexSet);

        if (currentRowErrorColumnIndexSet.isEmpty()) {
            rowIdx2RowDataMap.put(rowIndex, data);
        } else {
            Set<Integer> errColIdxMap = rowIdx2ErrColIdxMap.get(rowIndex);
            if (errColIdxMap != null) {
                currentRowErrorColumnIndexSet.addAll(errColIdxMap);
            }
            rowIdx2ErrColIdxMap.put(rowIndex, currentRowErrorColumnIndexSet);
        }
    }

    /**
     * 处理表头
     * 一行一行调用该函数
     * @param headMap -
     * @param context -
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);

        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex = readRowHolder.getRowIndex();

        LOGGER.debug("Head: 行号={}, RowIndex={}, Type={}, map={}", rowIndex+1, rowIndex, readRowHolder.getRowType(), JSON.toJSONString(headMap));

        rowIdx2HeadMap.put(rowIndex, headMap);

        /*
         * 表头合法性校验
         */
        if (headCheckMap != null && !headCheckMap.isEmpty()) {
            int headRowNumber = context.readSheetHolder().getHeadRowNumber();
            // 只表头最后一行才被校验
            if (headRowNumber == rowIndex + 1) {
                for (Integer key : headCheckMap.keySet()) {
                    String expect = headCheckMap.get(key).trim();
                    CellData cell = headMap.get(key);
                    if (null == cell) {
                        //模板不符！退出
                        throw new ExcelHeadException("表头与预期不符。未找到表头：" + expect);
                    }

                    String real = cell.getStringValue();
                    real = (real==null? null : real.trim());
                    if (!expect.equalsIgnoreCase(real)) {
                        //模板不符！退出
                        throw new ExcelHeadException("表头与预期不符。期望：" + expect + " <--> 实际：" + real);
                    }
                }
            }
        }

    }

    /**
     * if have something to do after all analysis
     * 所有数据解析完成了！（都会来调用）
     *
     * @param context -
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        readExcelResult.setRowIdx2HeadMap(rowIdx2HeadMap);
        readExcelResult.setRowIdx2ErrColIdxMap(rowIdx2ErrColIdxMap);
        readExcelResult.setRowIdx2RowDataMap(rowIdx2RowDataMap);
    }


    /**
     * The current method is called when extra information is returned
     *
     * @param extra   extra information
     * @param context -
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        LOGGER.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                LOGGER.info("额外信息是批注,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(), extra.getColumnIndex(),
                        extra.getText());
                break;
            case HYPERLINK:
                /*
                 * demo codes for hyperlink, not be really used
                 */
                if ("Sheet1!A1".equals(extra.getText())) {
                    LOGGER.info("额外信息是超链接,在rowIndex:{},columnIndex;{},内容是:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else {
                    LOGGER.error("Unknown hyperlink!");
                }
                break;
            case MERGE:
                LOGGER.info(
                        "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                        extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                        extra.getLastColumnIndex());
                break;
            default:
        }
    }

    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * entire read will terminate.
     * 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception -
     * @param context -
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            Integer cellRowIndex = excelDataConvertException.getRowIndex();
            Integer cellColumnIndex = excelDataConvertException.getColumnIndex();

            String cellColumnString = CellReference.convertNumToColString(cellColumnIndex);
            LOGGER.error("第{}行{}列，数值转换异常：{}", cellRowIndex+1, cellColumnString, exception.getMessage());

            Set<Integer> errColIdxMap = rowIdx2ErrColIdxMap.get(cellRowIndex);
            if (errColIdxMap == null) {
                errColIdxMap = new TreeSet<>();
            }
            errColIdxMap.add(cellColumnIndex);
            rowIdx2ErrColIdxMap.put(cellRowIndex, errColIdxMap);
        } else if (exception instanceof ExcelHeadException) {

            LOGGER.error(exception.getMessage());

            // 表格不符合规范，抛出异常，触发终止解析
            throw exception;
        } else {
            LOGGER.error("第{}行解析失败，但是继续解析下一行。exception: \n{}",
                    context.readRowHolder().getRowIndex() + 1,
                    Arrays.toString(exception.getStackTrace()).replaceAll(",", "\n"));
        }
    }

}

