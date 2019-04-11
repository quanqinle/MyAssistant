package com.quanqinle.myworld.service;

import com.quanqinle.myworld.entity.po.VideoInfo;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.entity.po.VideoUpload;

import java.util.List;

/**
 * @author quanql
 */
public interface VideoService {

	/**
	 * 获取所有视频网站
	 * @return
	 */
	List<VideoSite> getVideoSites();

	/**
	 * 获取视频网站信息
	 * @param siteId
	 * @return
	 */
	VideoSite getVideoSite(int siteId);

	/**
	 * 新增或修改网站信息
	 * @param videoSite
	 * @return
	 */
	VideoSite updateVidetSite(VideoSite videoSite);


	/**
	 * DB中新增一条视频信息
	 * @param videoInfo
	 * @return
	 */
	VideoInfo addVideo(VideoInfo videoInfo);

	/**
	 * 获取视频下载信息
	 * @param videoName
	 * @return
	 */
	VideoInfo getVideo(String videoName);

	/**
	 * 获取所有视频下载信息
	 * @return
	 */
	List<VideoInfo> getVideos();

	/**
	 * 获取视频发布信息
	 * @param videoName
	 * @param siteId
	 * @return
	 */
	VideoUpload getUploadInfo(String videoName, int siteId);

	/**
	 * 获取视频发布信息
	 * @param videoId
	 * @param siteId
	 * @param state
	 * @return
	 */
	VideoUpload getUploadInfo(int videoId, int siteId, int state);
	/**
	 * DB中新增一条发布视频记录
	 * @param videoUpload
	 * @return
	 */
	VideoUpload addUploadInfo(VideoUpload videoUpload);
}
