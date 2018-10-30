package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 二手房
 * @author quanql
 */
public interface SecondHandListingRepository extends JpaRepository<EstateSecondHandListing, Integer> {

}
