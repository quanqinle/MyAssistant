package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.entity.TaxRate;
import com.quanqinle.myworld.entity.TaxRateRepository;
import com.quanqinle.myworld.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaxRateServiceImpl implements TaxRateService {

    @Autowired
    TaxRateRepository taxRateRepository;

    @Override
    public List<TaxRate> getAllTaxRate() {
        return taxRateRepository.findAll(Sort.by(Sort.Direction.ASC, "rangeLowest"));
    }

    @Override
    public TaxRate getTaxRateById(int id) {
        return (TaxRate)taxRateRepository.getOne(id);
    }

    @Override
    public TaxRate getTaxRateByRange(float income) {
//        return null;
        if (income <= 0) {
            return null;
        }
        List<TaxRate> rates = taxRateRepository.findByRangeLowestLessThanOrderByRangeLowestDesc(income);
        if (rates.isEmpty() || rates == null ) {
            return null;
        }
        return rates.get(0);
    }
}
