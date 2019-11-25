package com.quanqinle.mysecretary.service;

import com.quanqinle.mysecretary.entity.po.VideoInfo;
import com.quanqinle.mysecretary.entity.po.VideoSite;
import com.quanqinle.mysecretary.entity.po.VideoUpload;

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
	 * 新增/修改网站信息
	 * @param videoSite
	 * @return
	 */
	VideoSite addVideoSite(VideoSite videoSite);

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
	 * DB中新增/修改一条视频信息
	 * @param videoInfo
	 * @return
	 */
	VideoInfo addVideo(VideoInfo videoInfo);

	/**
	 * DB中新增/修改一条视频信息
	 * @param videoName
	 * @param siteId
	 * @return
	 */
	VideoInfo addVideo(String videoName, int siteId);

	/**
	 * 获取所有未发表的视频
	 * @param siteId
	 * @return
	 */
	List<VideoInfo> getVideosUnpublished(int siteId);

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
	 * @return
	 */
	VideoUpload getUploadInfo(int videoId, int siteId);

	/**
	 * 获取视频发布信息
	 * @param videoId
	 * @param siteId
	 * @param state
	 * @return
	 */
	VideoUpload getUploadInfo(int videoId, int siteId, int state);
	/**
	 * 获取已发布到某网站的特定状态发布信息
	 * @param siteId
	 * @param state
	 * @return
	 */
	List<VideoUpload> getUploadInfos(int siteId, int state);
	/**
	 * DB中新增/修改一条发布视频记录，函数只负责保存，不会修改入参对象
	 * @param videoUpload
	 * @return
	 */
	VideoUpload saveUploadInfo(VideoUpload videoUpload);

	/**
	 * DB中新增/修改一条发布视频记录
	 * <p>调用this.saveUploadInfo(videoName, siteId, state)，发布状态是成功</p>
	 * @param videoName
	 * @param siteId
	 * @return
	 */
	VideoUpload saveUploadInfo(String videoName, int siteId);

	/**
	 * DB中新增/修改一条发布视频记录
	 * @param videoName
	 * @param siteId
	 * @param state
	 * @return
	 */
	VideoUpload saveUploadInfo(String videoName, int siteId, int state);
}
