package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.TaxRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author quanqinle
 */
public interface TaxRateRepository extends JpaRepository<TaxRate, Integer> {

	/**
	 *
	 * @param status
	 * @return
	 */
	List<TaxRate> findByStatusOrderByRangeLowestAsc(int status);
	/**
	 *
	 * @param effectiveDate
	 * @return
	 */
	List<TaxRate> findByEffectiveDateOrderByRangeLowestAsc(String effectiveDate);
	/**
	 * 演示通过名称定义jpa的查询：获取左区间小于income的税率，并按左区间降序排列
	 * @param income 金额
	 * @return
	 */
	List<TaxRate> findByRangeLowestLessThanAndStatusOrderByRangeLowestDesc(double income, int status);

	/**
	 * 根据ID获取税率
	 * @param id 对应db中的id
	 * @param pageable 分页对象
	 * @return
	 */
	Page<TaxRate> findById(int id, Pageable pageable);
}
