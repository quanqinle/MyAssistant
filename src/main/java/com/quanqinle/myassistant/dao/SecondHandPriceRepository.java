package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.EstateSecondHandPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 二手房价格信息
 * @author quanqinle
 */
public interface SecondHandPriceRepository extends JpaRepository<EstateSecondHandPrice, Integer> {
	/**
	 * 根据房屋唯一编号获取房屋挂牌价信息
	 * @param houseUniqueId
	 * @return
	 */
	List<EstateSecondHandPrice> findByHouseUniqueId(String houseUniqueId);

	/**
	 * 根据房屋唯一编号、挂牌编号获取房屋挂牌信息
	 * @param houseUniqueId
	 * @param listingId
	 * @return
	 */
	EstateSecondHandPrice findByHouseUniqueIdAndListingId(String houseUniqueId, String listingId);

}
