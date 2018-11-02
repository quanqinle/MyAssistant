package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.EstateCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 小区信息
 * @author quanqinle
 */
public interface CommunityRepository extends JpaRepository<EstateCommunity, Integer> {

}
