package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<EstateCommunity, Integer> {

}
