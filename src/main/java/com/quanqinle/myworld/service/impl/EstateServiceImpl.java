package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.dao.CommunityRepository;
import com.quanqinle.myworld.dao.SecondHandListingRepository;
import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import com.quanqinle.myworld.service.EstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author quanqinle
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EstateServiceImpl implements EstateService {

	@Autowired
	CommunityRepository communityRepository;
	@Autowired
	SecondHandListingRepository secondHandListingRepository;

	@Override
	public List<EstateCommunity> saveCommunities(List<EstateCommunity> list) {
		return communityRepository.saveAll(list);
	}

	@Override
	public List<EstateSecondHandListing> saveSecondHandListings(List<EstateSecondHandListing> list) {
		return secondHandListingRepository.saveAll(list);
	}

	@Override
	public EstateSecondHandListing saveSecondHandListing(EstateSecondHandListing item) {
		return secondHandListingRepository.save(item);
	}
}
