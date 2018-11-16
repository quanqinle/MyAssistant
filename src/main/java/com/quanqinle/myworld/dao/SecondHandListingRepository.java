package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 二手房
 * @author quanql
 */
public interface SecondHandListingRepository extends JpaRepository<EstateSecondHandListing, Integer> {

	/**
	 * 根据挂牌id获取二手挂牌信息
	 * @param gpid
	 * @return
	 */
	EstateSecondHandListing findByGpid(String gpid);

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
	 * //@Query(value="select t.* from estate_secondhand_listing t where not EXISTS (select 1 from estate_secondhand_price p where p.house_unique_id=t.fwtybh and p.listing_id=t.gpid)", nativeQuery = true)
	 * @return
	 */
	@Query(value="select t.* from estate_secondhand_listing t where t.gpid not in (select p.listing_id from estate_secondhand_price p)", nativeQuery = true)
	List<EstateSecondHandListing> findAllNotInPriceTable();

	/**
	 * 向house表中同步数据
	 */
	@Modifying
	@Query(nativeQuery = true, value = "insert INTO estate_secondhand_house \n" +
			"(house_unique_id, covered_area, district, house_property_ownership_certificate, community_id, community_name, city_code, city_name) \n" +
			"select fwtybh, jzmj, cqmc, fczsh, xqid, xqmc, xzqh, xzqhname \n" +
			"from estate_secondhand_listing where fwtybh not in (select h.house_unique_id from estate_secondhand_house h)")
	void insertHouseTable();

	/**
	 * 向price表中同步数据
	 */
	@Modifying
	@Query(nativeQuery = true, value = "insert INTO estate_secondhand_price \n" +
			"(house_unique_id, sale_price, entrust_time, listing_house_id, listing_id, listing_contact_name, listing_status, listing_status_value, real_estate_agency, listing_time, listing_unique_id, entrust_agreement_id) \n" +
			"select fwtybh, wtcsjg, cjsj, gpfyid, gpid, gplxrxm, gpzt, gpztValue, mdmc, scgpshsj, tygpbh, wtxybh \n" +
			"from estate_secondhand_listing where gpid not in (select p.listing_id from estate_secondhand_price p)")
	void insertPriceTable();
}
