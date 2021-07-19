package com.quanqinle.myassistant.service.impl;

import com.quanqinle.myassistant.dao.CommunityRepository;
import com.quanqinle.myassistant.dao.SecondHandHouseRepository;
import com.quanqinle.myassistant.dao.SecondHandPriceRepository;
import com.quanqinle.myassistant.dao.SecondHandListingRepository;
import com.quanqinle.myassistant.entity.po.EstateCommunity;
import com.quanqinle.myassistant.entity.po.EstateSecondHandHouse;
import com.quanqinle.myassistant.entity.po.EstateSecondHandListing;
import com.quanqinle.myassistant.entity.po.EstateSecondHandPrice;
import com.quanqinle.myassistant.service.EstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author quanqinle
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EstateServiceImpl implements EstateService {

    private Logger log = LoggerFactory.getLogger(EstateService.class);

	@Autowired
	CommunityRepository communityRepository;
	@Autowired
	SecondHandListingRepository secondHandListingRepository;
	@Autowired
	SecondHandHouseRepository secondHandHouseRepository;
	@Autowired
	SecondHandPriceRepository secondHandPriceRepository;

	@Override
	public List<EstateCommunity> saveCommunities(List<EstateCommunity> list) {
		return communityRepository.saveAll(list);
	}

	@Override
	public EstateSecondHandListing saveSecondHandListing(EstateSecondHandListing item) {
		return secondHandListingRepository.save(item);
	}

	/**
	 * 获取所有二手挂牌信息
	 * @return
	 */
	@Override
	public List<EstateSecondHandListing> getAllSecondHandListing() {
		return secondHandListingRepository.findAll();
	}

	@Override
	public EstateSecondHandListing getSecondHandListingByGpid(String gpid) {
		return secondHandListingRepository.findByGpid(gpid);
	}

	@Override
	public List<EstateSecondHandListing> getAllNotInHouseTable() {
		return secondHandListingRepository.findAllNotInHouseTable();
	}

	@Override
	public List<EstateSecondHandListing> getAllNotInPriceTable() {
		return secondHandListingRepository.findAllNotInPriceTable();
	}

	@Override
	public EstateSecondHandListing getLatestOne() {
		return secondHandListingRepository.findFirstByOrderByScgpshsjDescIdAsc();
	}

	@Override
	public EstateSecondHandHouse getSecondHandHouse(String houseUniqueId) {
		return secondHandHouseRepository.findByHouseUniqueId(houseUniqueId);
	}

	@Override
	public List<EstateSecondHandPrice> getSecondHandPrice(String houseUniqueId) {
		return secondHandPriceRepository.findByHouseUniqueId(houseUniqueId);
	}

	@Override
	@Transactional(noRollbackFor = {Exception.class})
	public boolean syncListingToHouseTable(@NotNull EstateSecondHandListing one) {

		String houseUniqueId = one.getFwtybh();

		if (secondHandHouseRepository.findByHouseUniqueId(houseUniqueId) != null) {
			log.info("house [" + houseUniqueId + "] is existed.");
		} else {
			EstateSecondHandHouse house = new EstateSecondHandHouse(one);
			log.info("save house:" + house);
			secondHandHouseRepository.saveAndFlush(house);
		}

		return true;
	}

	@Override
	@Transactional(noRollbackFor = {Exception.class})
	public boolean syncListingToPriceTable(@NotNull EstateSecondHandListing one) {

		String houseUniqueId = one.getFwtybh();
		String listingId = one.getGpid();

		if (secondHandPriceRepository.findByHouseUniqueIdAndListingId(houseUniqueId,listingId) != null) {
			log.info("price [" + listingId + "] is existed.");
		} else {
			EstateSecondHandPrice price = new EstateSecondHandPrice(one);
			log.info("save price:" + price);
			secondHandPriceRepository.saveAndFlush(price);
		}

		return true;
	}

	/**
	 * 将Listing表中新数据同步到House表，通过SQL的方式
	 */
	@Override
	public void insertHouseTblFromListing() {
		try {
			log.info("insert into estate_secondhand_house select * from estate_secondhand_listing where fwtybh not in (select house_unique_id from estate_secondhand_house)");
			secondHandListingRepository.insertHouseTable();
		} catch (Exception e) {
			log.error(String.valueOf(e.getStackTrace()));
		}
	}

	/**
	 * 将Listing表中新数据同步到Price表，通过SQL的方式
	 */
	@Override
	public void insertPriceTblFromListing() {
		try {
			log.info("insert into estate_secondhand_price select * from estate_secondhand_listing where gpid not in (select listing_id from estate_secondhand_price)");
			secondHandListingRepository.insertPriceTable();
		} catch (Exception e) {
			log.error(String.valueOf(e.getStackTrace()));
		}
	}

}
