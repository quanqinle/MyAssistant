package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandHouse;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import com.quanqinle.myworld.entity.po.EstateSecondHandPrice;

import java.util.List;

/**
 * 房产
 * @author quanqinle
 */
public interface EstateService {

	/**
	 * 保存社区信息
	 * @param list
	 * @return
	 */
	List<EstateCommunity> saveCommunities(List<EstateCommunity> list);

	/**
	 * 保存二手房挂牌信息
	 * @param item
	 * @return
	 */
	EstateSecondHandListing saveSecondHandListing(EstateSecondHandListing item);

	/**
	 * 获取所有二手挂牌信息
	 * @return
	 */
	List<EstateSecondHandListing> getAllSecondHandListing();

	/**
	 * 根据唯一挂牌编号获取二手挂牌信息
	 * @param fwtybh
	 * @return
	 */
	List<EstateSecondHandListing> getSecondHandListing(String fwtybh);

	/**
	 * 查找存在于挂牌信息表，但不存在于二手房屋表中的数据
	 * @return
	 */
	List<EstateSecondHandListing> getAllNotInHouseTable();

	/**
	 * 查找存在于挂牌信息表，但不存在于二手房屋价格表中的数据
	 * @return
	 */
	List<EstateSecondHandListing> getAllNotInPriceTable();

	/**
	 * 获取上次抓取的最新一条挂牌信息
	 * @return
	 */
	EstateSecondHandListing getLatestOne();

	/**
	 * 获取二手房屋信息
	 * @param houseUniqueId
	 * @return
	 */
	EstateSecondHandHouse getSecondHandHouse(String houseUniqueId);

	/**
	 * 获取二手房屋价格信息
	 * @param houseUniqueId
	 * @return
	 */
	List<EstateSecondHandPrice> getSecondHandPrice(String houseUniqueId);

	/**
	 * 将二手房挂牌信息表中的内容同步到其他表
	 * @param one
	 * @return
	 */
	boolean syncListingToOtherTables(EstateSecondHandListing one);

}
