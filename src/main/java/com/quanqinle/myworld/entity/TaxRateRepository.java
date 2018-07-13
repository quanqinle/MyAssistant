package com.quanqinle.myworld.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxRateRepository extends JpaRepository<TaxRate, Integer> {
    List<TaxRate> findByRangeLowestLessThanOrderByRangeLowestDesc(float income);
}
