package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.EstateCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 小区信息
 * @author quanqinle
 */
public interface CommunityRepository extends JpaRepository<EstateCommunity, Integer> {

}
