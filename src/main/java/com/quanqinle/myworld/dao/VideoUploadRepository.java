package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.VideoUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 视频上传、发布信息
 * @author quanql
 */
public interface VideoUploadRepository extends JpaRepository<VideoUpload, Integer> {

	/**
	 * 根据获取发布信息
	 * @param videoId 视频id
	 * @param siteId 网站id
	 * @param state 发布状态
	 * @return 发布信息
	 */
	VideoUpload findByVideoIdAndSiteIdAndState(int videoId, int siteId, int state);

	/**
	 * 根据获取发布信息
	 * @param videoId 视频id
	 * @param siteId 网站id
	 * @return 发布信息
	 */
	VideoUpload findByVideoIdAndSiteId(int videoId, int siteId);

	/**
	 * 获取已发到某站点的特定状态视频
	 * @param siteId 视频id
	 * @param state 发布状态
	 * @return 发布信息
	 */
	List<VideoUpload> findAllBySiteIdAndState(int siteId, int state);
}
