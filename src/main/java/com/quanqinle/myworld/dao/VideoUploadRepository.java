package com.quanqinle.myworld.dao;

import com.quanqinle.myworld.entity.po.VideoUpload;
import org.springframework.data.jpa.repository.JpaRepository;

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
	 * @return
	 */
	VideoUpload findByVideoIdAndSiteIdAndState(int videoId, int siteId, int state);

	/**
	 * 保存视频发布信息
	 * @param videoUpload
	 * @return
	 */
//	@Override
//	VideoUpload save(VideoUpload videoUpload);
}
