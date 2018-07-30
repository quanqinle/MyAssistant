package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.entity.po.TaxRate;
import com.quanqinle.myworld.dao.TaxRateRepository;
import com.quanqinle.myworld.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//@CacheConfig("tax") // 统一配置缓存名，免去每个方法注解都要写cacheNames
public class TaxRateServiceImpl implements TaxRateService {

	@Autowired
	TaxRateRepository taxRateRepository;

	@Override
	@Cacheable("tax")
	public List<TaxRate> getAllTaxRate() {
		return taxRateRepository.findAll(Sort.by(Sort.Direction.ASC, "rangeLowest"));
	}

	@Override
	@Cacheable(cacheNames = "tax",key = "#id",condition = "#id<10")
	public TaxRate getTaxRateById(int id) {
		return (TaxRate) taxRateRepository.getOne(id);
	}

	@Override
	@Cacheable(cacheNames = {"tax", "taxrate"})
	public TaxRate getTaxRateByRange(double income) {
//        return null;
		if (income <= 0) {
			return null;
		}
		List<TaxRate> rates = taxRateRepository.findByRangeLowestLessThanOrderByRangeLowestDesc(income);
		if (rates.isEmpty() || rates == null) {
			return null;
		}
		return rates.get(0);
	}
}
