package com.quanqinle.mysecretary.readexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.StringUtils;
import org.apache.poi.hssf.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel读取基础工具集
 *
 * @author quanql
 */
public class ReadExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadExcelUtil.class);


    private ReadExcelUtil() {
    }

    /**
     *
     * @param obj
     * @param fieldName
     * @param map
     * @param context
     * @param errorColumnIndexSet
     */
    public static void checkFieldValueInStringMap(Object obj, String fieldName, Map map, AnalysisContext context, Set<Integer> errorColumnIndexSet) {

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            boolean bSetAccess = false;

            String fieldValue = null;
            try {
                fieldValue = (String) field.get(obj);
            } catch (IllegalAccessException e) {
                bSetAccess = true;
                field.setAccessible(true);
            }

            if (!bSetAccess) {
                field.setAccessible(false);
            }

            if (!StringUtils.isEmpty(fieldValue)) {
                if (!map.containsKey(fieldValue.trim())) {

                    int columnIndex = getColumnIndexByFieldName(context, fieldName);

                    errorColumnIndexSet.add(columnIndex);
                }
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("无此属性：{}", fieldName);
            e.printStackTrace();
        }

    }

    /**
     * 根据列在类中的属性名，获取列的index
     *
     * @param context
     * @param fieldName 字段在类class中的属性名
     * @return -1表示未查到
     */
    public static int getColumnIndexByFieldName(AnalysisContext context, String fieldName) {

        Map<Integer, Head> headMap = context.currentReadHolder().excelReadHeadProperty().getHeadMap();
        for (Integer colIdx: headMap.keySet()) {
            Head head = headMap.get(colIdx);
            if (fieldName.equalsIgnoreCase(head.getFieldName())) {
                return colIdx;
            }
        }

        return -1;
    }

    /**
     * 判断字段是否非空，根据属性（行对象的）的注解
     *
     * @param rowData 行对象的实例
     * @param columnIndexSet 如校验不通过，则将列index加入集合
     * @return 如非空，通过校验，则 true
     */
    public static boolean checkNotEmpty(Object rowData, AnalysisContext context, Set<Integer> columnIndexSet) {
        boolean flag = true;
        try {
            //使用反射获取所有属性， 判断是否有注解null可以为null，如果非法为空则加入错误messageMap
            for (Field field : rowData.getClass().getDeclaredFields()) {
                boolean isOk = true;
                field.setAccessible(true);
                Object fieldValue = field.get(rowData);

                //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                Null hasNullAnnotation = field.getAnnotation(Null.class);
                NotBlank hasNotBlankAnnotation = field.getAnnotation(NotBlank.class);

                if (hasNullAnnotation != null) {
                    /**
                     * 处理 @Null 注解。
                     */
                    if (fieldValue != null) {
                        isOk = false;
                    }
                } else if (hasNotBlankAnnotation != null) {
                    /**
                     * 处理 @NotBlank 注解。
                     * 注意：@NotBlank只用于String，且忽略 whitespaces。
                     * 对比，@NotEmpty 用于String类、Collection、Map、数组，不忽略String的 whitespaces
                     */
                    if (fieldValue != null) {
                        String stringValue = (String) fieldValue;
                        if (stringValue.trim().length() <= 0) {
                            isOk = false;
                        }
                    } else {
                        isOk = false;
                    }
                }

                if (!isOk) {
                    flag = false;

                    Map<Integer, Head> headMap = context.currentReadHolder().excelReadHeadProperty().getHeadMap();
                    headMap.forEach((colIdx, head) -> {
                        if (field.getName().equalsIgnoreCase(head.getFieldName())) {
                            columnIndexSet.add(colIdx);
                            LOGGER.error("必填项不应为空：第{}行，列[{}]={}",
                                    1 + context.readRowHolder().getRowIndex(),
                                    colIdx,
                                    field.getAnnotation(ExcelProperty.class).value());
                        }
                    });
                }
            } // end for

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断字段是否非空，根据列名
     * <p>比如，检查index=n的列不能为空</p>
     *
     * @param columnIndex
     * @param context
     * @param columnIndexSet 如校验不通过，则将列index加入集合
     * @return 如非空，通过校验，则 true
     */
    public static boolean checkNotEmpty(Integer columnIndex, AnalysisContext context, Set<Integer> columnIndexSet) {
        Cell cell = context.readRowHolder().getCellMap().get(columnIndex);

        if (cell != null) {
            if (CellDataTypeEnum.EMPTY != ((CellData<?>) cell).getType()) {
                return true;
            }
        }

        columnIndexSet.add(columnIndex);
        LOGGER.debug("必填项不应为空：columnIndex={}", columnIndex);

        return false;
    }
    /**
     * 判断字段是否非空，根据列名
     * <p>比如，检查“姓名”列不能为空</p>
     *
     * @param columnHeadName 列名，如，”姓名“、”年龄“
     * @param context
     * @param columnIndexSet 如校验不通过，则将列index加入集合
     * @return 如非空，通过校验，则 true
     */
    public static boolean checkNotEmpty(String columnHeadName, AnalysisContext context, Set<Integer> columnIndexSet) {

        boolean isOK = false;

        ReadHolder readHolder = context.currentReadHolder();
        Map<Integer, Head> headMap = readHolder.excelReadHeadProperty().getHeadMap();

        ReadRowHolder readRowHolder = context.readRowHolder();
        int rowIndex = readRowHolder.getRowIndex();

        for (Integer colIdx: headMap.keySet()) {

            if (headMap.get(colIdx).getHeadNameList().contains(columnHeadName)) {
                if (checkNotEmpty(colIdx, context, columnIndexSet)) {
                    isOK = true;
                } else {
                    columnIndexSet.add(colIdx);
                    LOGGER.error("必填项不应为空：第{}行，列名[{}]", rowIndex+1, columnHeadName);
                }
            }
        }

        //fixme
        return isOK;
    }

    /**
     * for debug
     * @param args
     */
    public static void main(String[] args) {
        String sample = "异常";
        String[] sampleFeatures = new String[]{"大额","异常","账龄长","零账户","随机","有纠纷","重要客户"};
        boolean contains = Arrays.stream(sampleFeatures).anyMatch(sample.trim()::equals);
        LOGGER.info("是否包含{}", contains);

        int columnNumber = 1;

        int columnIndex = columnNumber - 1;
        String columnLetter = CellReference.convertNumToColString(columnIndex);
        LOGGER.info("idx={}, num={}, letter={}", columnIndex, columnNumber, columnLetter);
    }

}

