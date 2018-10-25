package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.TaxRate;

import java.util.List;

/**
 * @author quanqinle
 */
public interface TaxRateService {
    /**
     * 获取当前有效的个税税率表
     * @return
     */
    List<TaxRate> getTaxRateTable();

    /**
     * 根据状态，获取个税税率表
     * @param status
     * @return
     */
    List<TaxRate> getTaxRateTable(int status);

    /**
     * 根据生效年份，获取个税税率表
     * @param effectiveDate
     * @return
     */
    List<TaxRate> getTaxRateTable(String effectiveDate);

    /**
     * 根据id获取税率
     * @param id
     * @return
     */
    TaxRate getTaxRateById(int id);

    /**
     * 根据income所在区间获取税率
     * @param income 金额
     * @return
     */
    TaxRate getTaxRateByRange(double income);

    /**
     * 获取所有个税税率表
     * @return
     */
    List<TaxRate> getAllTaxRateTable();
}
