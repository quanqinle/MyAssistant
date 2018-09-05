package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.TaxRate;

import java.util.List;

/**
 * @author quanqinle
 */
public interface TaxRateService {
    /**
     * 获取所有税率
     * @return
     */
    List<TaxRate> getAllTaxRate();

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
}
