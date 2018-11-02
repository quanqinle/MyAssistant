package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;

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
	 * @param list
	 * @return
	 */
	List<EstateSecondHandListing> saveSecondHandListings(List<EstateSecondHandListing> list);

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
	 * 将二手房挂牌信息表中的内容同步到其他表
	 * @param one
	 * @return
	 */
	boolean syncListingToOtherTables(EstateSecondHandListing one);
}
