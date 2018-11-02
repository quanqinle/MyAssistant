package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandHouse;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 二手房房屋信息
 * @author quanqinle
 */
public interface SecondHandHouseRepository  extends JpaRepository<EstateSecondHandHouse, Integer> {

	EstateSecondHandHouse findByHouseUniqueId(String houseUniqueId);
}
