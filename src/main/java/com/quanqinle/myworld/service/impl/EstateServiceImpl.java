package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.dao.CommunityRepository;
import com.quanqinle.myworld.dao.SecondHandHouseRepository;
import com.quanqinle.myworld.dao.SecondHandPriceRepository;
import com.quanqinle.myworld.dao.SecondHandListingRepository;
import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandHouse;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import com.quanqinle.myworld.entity.po.EstateSecondHandPrice;
import com.quanqinle.myworld.service.EstateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	Log log = LogFactory.getLog(EstateService.class);

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
	public List<EstateSecondHandListing> getSecondHandListing(String fwtybh) {
		return secondHandListingRepository.findByFwtybh(fwtybh);
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
	public boolean syncListingToOtherTables(@NotNull EstateSecondHandListing one) {

		String houseUniqueId = one.getFwtybh();
		String listingId = one.getGpid();

		EstateSecondHandHouse house = new EstateSecondHandHouse();
		EstateSecondHandPrice price = new EstateSecondHandPrice();

		if (secondHandHouseRepository.findByHouseUniqueId(houseUniqueId) != null) {
			log.info("house [" + houseUniqueId + "] is existed.");
		} else {
			house.setHouseUniqueId(one.getFwtybh());
			house.setCoveredArea(one.getJzmj());
			house.setDistrict(one.getCqmc());
			house.setHousePropertyOwnershipCertificate(one.getFczsh());
			house.setCommunityId(one.getXqid());
			house.setCommunityName(one.getXqmc());
			house.setCityCode(one.getXzqh());
			house.setCityName(one.getXzqhname());
			log.info("save house:" + house);
			secondHandHouseRepository.saveAndFlush(house);
		}
		if (secondHandPriceRepository.findByHouseUniqueIdAndListingId(houseUniqueId,listingId) != null) {
			log.info("price [" + listingId + "] is existed.");
		} else {
			price.setHouseUniqueId(one.getFwtybh());
			price.setListingId(one.getGpid());
			price.setSalePrice(one.getWtcsjg());
			price.setEntrustTime(one.getCjsj());
			price.setListingHouseId(one.getGpfyid());
			price.setListingContactName(one.getGplxrxm());
			price.setListingStatus(one.getGpzt());
			price.setListingStatusValue(one.getGpztValue());
			price.setRealEstateAgency(one.getMdmc());
			price.setListingTime(one.getScgpshsj());
			price.setListingUniqueId(one.getTygpbh());
			price.setEntrustAgreementId(one.getWtxybh());
			log.info("save price:" + price);
			secondHandPriceRepository.saveAndFlush(price);
		}

		return false;
	}
}
