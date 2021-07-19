package com.quanqinle.myassistant.readexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.fastjson.JSON;
import com.quanqinle.myassistant.readexcel.ReadExcelConstant;
import com.quanqinle.myassistant.readexcel.ReadExcelUtil;
import com.quanqinle.myassistant.readexcel.entity.ReadExcelResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 示例：一个实际业务的监听
 *
 * @author quanqinle
 */
public class MyBizDemoListener<T> extends ReadRowDataListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBizDemoListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;


    public MyBizDemoListener() {
        this(new ReadExcelResult<>(), null);
    }

    /**
     * @param readExcelResult 读取excel后，存入该对象
     * @param headCheckMap    表格头校验map。A map contains columnIndex<->表头名. If null, do not check head
     */
    public MyBizDemoListener(ReadExcelResult<T> readExcelResult, Map<Integer, String> headCheckMap) {
        super(readExcelResult, headCheckMap);
    }

    /**
     * When analysis one row trigger invoke function.
     * 自动跳过空行，即，空行不会进入这个函数
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context -
     */
    @Override
    public void invoke(T data, AnalysisContext context) {

        currentRowErrorColumnIndexSet = new TreeSet<>();

        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex = readRowHolder.getRowIndex();
        RowTypeEnum rowType = readRowHolder.getRowType();
        Map<Integer, Cell> cellMap = readRowHolder.getCellMap();

        LOGGER.debug("Data: 行号={}, RowIndex={}, Type={}, map={}", rowIndex+1, rowIndex, rowType, JSON.toJSONString(cellMap));

        /**
         * 另一种非空校验方法：ContactUtil.checkRequiredFields(context, currentRowErrorColumnIndexSet)
         */
        ReadExcelUtil.checkNotEmpty(data, context, currentRowErrorColumnIndexSet);

        /**
         * 值有效性校验
         */
        ReadExcelUtil.checkFieldValueInStringMap(data, "sendType", ReadExcelConstant.SEND_TYPE_MAP, context, currentRowErrorColumnIndexSet);
        ReadExcelUtil.checkFieldValueInStringMap(data, "sampleFeature", ReadExcelConstant.SAMPLE_FEATURE_MAP, context, currentRowErrorColumnIndexSet);
        ReadExcelUtil.checkFieldValueInStringMap(data, "dealType", ReadExcelConstant.DEAL_TYPE_MAP, context, currentRowErrorColumnIndexSet);
        ReadExcelUtil.checkFieldValueInStringMap(data, "subject", ReadExcelConstant.CONTACT_SUBJECT_MAP, context, currentRowErrorColumnIndexSet);

        if (currentRowErrorColumnIndexSet.isEmpty()) {
            rowIdx2RowDataMap.put(rowIndex, data);
        } else {
            Set<Integer> errColIdxMap = rowIdx2ErrColIdxMap.get(rowIndex);
            if (errColIdxMap != null) {
                currentRowErrorColumnIndexSet.addAll(errColIdxMap);
            }
            rowIdx2ErrColIdxMap.put(rowIndex, currentRowErrorColumnIndexSet);
        }

        /**
         * 一种优化性能的方法：
         * 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
         */
        if (rowIdx2RowDataMap.size() >= BATCH_COUNT) {
//            saveData();

            // 存储完成，清理list
//            list.clear();
        }
    }


}

