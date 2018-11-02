package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandPrice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 二手房价格信息
 * @author quanqinle
 */
public interface SecondHandPriceRepository  extends JpaRepository<EstateSecondHandPrice, Integer> {
	EstateSecondHandPrice findByHouseUniqueIdAndListingId(String houseUniqueId, String listingId);
}
