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
	/**
	 * 根据唯一挂牌编号获取二手挂牌信息
	 * @param fwtybh
	 * @return
	 */
	List<EstateSecondHandListing> findByFwtybh(String fwtybh);

	/**
	 * 获取上次爬取的最新一条记录
	 * @return
	 */
	EstateSecondHandListing findFirstByOrderByScgpshsjDescIdAsc();

	/**
	 * 查找未同步到 数据库表{house} 中的数据
	 * @return
	 */
	@Query(value="select t.* from estate_secondhand_listing t where t.fwtybh not in (select h.house_unique_id from estate_secondhand_house h)", nativeQuery = true)
	List<EstateSecondHandListing> findAllNotInHouseTable();
	/**
	 * 查找未同步到 数据库表{price} 中的数据
	 * 注：实测下面两个sql功能一样，not in的性能好
	 * @return
	 */
//	@Query(value="select t.* from estate_secondhand_listing t where not EXISTS (select 1 from estate_secondhand_price p where p.house_unique_id=t.fwtybh and p.listing_id=t.gpid)", nativeQuery = true)
	@Query(value="select t.* from estate_secondhand_listing t where t.gpid not in (select p.listing_id from estate_secondhand_price p)", nativeQuery = true)
	List<EstateSecondHandListing> findAllNotInPriceTable();

}
