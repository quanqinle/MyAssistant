package com.quanqinle.myassistant.dao;

import com.quanqinle.myassistant.entity.po.VideoSite;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author quanql
 */
public interface VideoSiteRepository extends JpaRepository<VideoSite, Integer> {
	/**
	 * 根据网站id获取网站信息
	 * @param siteId
	 * @return
	 */
	VideoSite findBySiteId(int siteId);
}
