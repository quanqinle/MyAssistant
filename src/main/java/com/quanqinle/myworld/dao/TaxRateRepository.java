package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.TaxRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxRateRepository extends JpaRepository<TaxRate, Integer> {
	List<TaxRate> findByRangeLowestLessThanOrderByRangeLowestDesc(double income);

	Page<TaxRate> findById(int id, Pageable pageable);
}
