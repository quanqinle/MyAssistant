package com.quanqinle.mysecretary.service.impl;

import com.quanqinle.mysecretary.entity.po.TaxRate;
import com.quanqinle.mysecretary.dao.TaxRateRepository;
import com.quanqinle.mysecretary.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author quanqinle
 *
 * // @CacheConfig("tax") // 统一配置缓存名，免去每个方法注解都要写cacheNames
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaxRateServiceImpl implements TaxRateService {

	private static int STATUS_VALID = 1;

	@Autowired
	TaxRateRepository taxRateRepository;

	@Override
	@Cacheable("tax")
	public List<TaxRate> getTaxRateTable() {
		return this.getTaxRateTable(STATUS_VALID);
	}

	@Override
	@Cacheable("tax")
	public List<TaxRate> getTaxRateTable(int status) {
		return taxRateRepository.findByStatusOrderByRangeLowestAsc(status);
	}

	@Override
	@Cacheable("tax")
	public List<TaxRate> getTaxRateTable(String effectiveDate) {
		return taxRateRepository.findByEffectiveDateOrderByRangeLowestAsc(effectiveDate);
	}

	@Override
	@Cacheable(cacheNames = "tax",key = "#id",condition = "#id<10")
	public TaxRate getTaxRateById(int id) {
		return taxRateRepository.getById(id);
	}

	@Override
	@Cacheable(cacheNames = {"tax", "taxrate"})
	public TaxRate getTaxRateByRange(double income) {
		if (income <= 0) {
			return null;
		}
		List<TaxRate> rates = taxRateRepository.findByRangeLowestLessThanAndStatusOrderByRangeLowestDesc(income, STATUS_VALID);
		if (rates.isEmpty()) {
			return null;
		}
		return rates.get(0);
	}

	@Override
	@Cacheable("tax")
	public List<TaxRate> getAllTaxRateTable() {
		return taxRateRepository.findAll(Sort.by(Sort.Direction.ASC, "rangeLowest"));
	}
}
