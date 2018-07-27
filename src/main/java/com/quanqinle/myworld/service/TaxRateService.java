package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.TaxRate;

import java.util.List;

public interface TaxRateService {
    public List<TaxRate> getAllTaxRate();
    public TaxRate getTaxRateById(int id);
    public TaxRate getTaxRateByRange(float income);
}
