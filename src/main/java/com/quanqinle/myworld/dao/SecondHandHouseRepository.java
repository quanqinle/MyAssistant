package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandHouse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 二手房房屋信息
 * @author quanqinle
 */
public interface SecondHandHouseRepository  extends JpaRepository<EstateSecondHandHouse, Integer> {

	/**
	 * 根据房屋唯一编码获取房屋信息
	 * @param houseUniqueId
	 * @return
	 */
	EstateSecondHandHouse findByHouseUniqueId(String houseUniqueId);
}
