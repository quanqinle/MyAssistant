package com.quanqinle.myassistant.entity.vo;

import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.ResultCode;

/**
 * datatables.css return data
 *
 * @author quanql
 * @version 2021/7/20
 */
public class DatatablesResult<T> extends Result<T> {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    public DatatablesResult() {
    }

    public DatatablesResult(int code, String message, T data, int draw, long recordsTotal, long recordsFiltered) {
        super(code, message, data);
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

  /**
   *
   * @param data -
   * @param draw -
   * @param recordsTotal -
   * @param recordsFiltered -
   * @return
   */
    public static <T> DatatablesResult<T>  success(T data, int draw, long recordsTotal, long recordsFiltered) {
        DatatablesResult datatablesResult = new DatatablesResult();
        datatablesResult.setCode(ResultCode.SUCCESS.getCode());
        datatablesResult.setMessage(ResultCode.SUCCESS.getMessage());
        datatablesResult.setData(data);
        datatablesResult.setDraw(draw);
        datatablesResult.setRecordsTotal(recordsTotal);
        datatablesResult.setRecordsFiltered(recordsFiltered);

        return datatablesResult;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
