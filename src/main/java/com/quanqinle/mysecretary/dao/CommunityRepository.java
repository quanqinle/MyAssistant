package com.quanqinle.mysecretary.dao;

import com.quanqinle.mysecretary.entity.po.EstateCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 小区信息
 * @author quanqinle
 */
public interface CommunityRepository extends JpaRepository<EstateCommunity, Integer> {

}
