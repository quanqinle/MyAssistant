package com.quanqinle.myassistant.service.impl;

import com.quanqinle.myassistant.biz.videoporter.VideoUtils;
import com.quanqinle.myassistant.dao.VideoInfoRepository;
import com.quanqinle.myassistant.dao.VideoUploadRepository;
import com.quanqinle.myassistant.dao.VideoSiteRepository;
import com.quanqinle.myassistant.entity.po.VideoInfo;
import com.quanqinle.myassistant.entity.po.VideoSite;
import com.quanqinle.myassistant.entity.po.VideoUpload;
import com.quanqinle.myassistant.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author quanqinle
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImpl implements VideoService {

    private Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

	@Autowired
	VideoSiteRepository videoSiteRepository;
	@Autowired
	VideoInfoRepository videoInfoRepository;
	@Autowired
	VideoUploadRepository videoUploadRepository;

	@Override
	public List<VideoSite> getVideoSites() {
		return videoSiteRepository.findAll();
	}

	@Override
	public VideoSite getVideoSite(int siteId) {
		return videoSiteRepository.findBySiteId(siteId);
	}

	@Override
	public VideoSite addVideoSite(VideoSite videoSite) {
		return videoSiteRepository.save(videoSite);
	}

	@Override
	public VideoInfo addVideo(VideoInfo videoInfo) {
		return videoInfoRepository.save(videoInfo);
	}

	@Override
	public VideoInfo addVideo(String videoName, int siteId) {
		VideoInfo videoInfo = getVideo(videoName);
		if (videoInfo == null) {
			log.info("db|add a videoInfo");
			videoInfo = new VideoInfo();
			videoInfo.setVideoName(videoName);
			videoInfo.setVideoSn(VideoUtils.parseVideoSn(videoName));
			videoInfo.setSourceSiteId(siteId);
			LocalDateTime localDateTime = LocalDateTime.now();
			videoInfo.setCreateTime(localDateTime);
			// LocalDateTime.parse("2019-04-06T18:18:18")
			videoInfo.setUpdateTime(localDateTime);
		} else {
			log.info("db|update a videoInfo");
			videoInfo.setVideoSn(VideoUtils.parseVideoSn(videoName));
			videoInfo.setSourceSiteId(siteId);
			LocalDateTime localDateTime = LocalDateTime.now();
			videoInfo.setUpdateTime(localDateTime);
		}

		return this.addVideo(videoInfo);
	}

	@Override
	public VideoInfo getVideo(String videoName) {
		return videoInfoRepository.findByVideoName(videoName);
	}

	@Override
	public List<VideoInfo> getVideos() {
		return videoInfoRepository.findAll();
	}

	@Override
	public List<VideoUpload> getUploadInfos(int siteId, int state) {
		return videoUploadRepository.findAllBySiteIdAndState(siteId, state);
	}

	@Override
	public List<VideoInfo> getVideosUnpublished(int siteId) {
		return videoInfoRepository.findAllNotPublished(siteId);
	}

	@Override
	public VideoUpload getUploadInfo(String videoName, int siteId) {
		VideoInfo videoInfo = this.getVideo(videoName);
		if (null == videoInfo) {
			log.info("video not existed");
			return null;
		} else {
			return this.getUploadInfo(videoInfo.getVideoId(), siteId);
		}
	}

	@Override
	public VideoUpload getUploadInfo(int videoId, int siteId, int state) {
		return videoUploadRepository.findByVideoIdAndSiteIdAndState(videoId, siteId, state);
	}

	@Override
	public VideoUpload getUploadInfo(int videoId, int siteId) {
		return videoUploadRepository.findByVideoIdAndSiteId(videoId, siteId);
	}

	@Override
	public VideoUpload saveUploadInfo(VideoUpload videoUpload) {
		return videoUploadRepository.save(videoUpload);
	}

	@Override
	public VideoUpload saveUploadInfo(String videoName, int siteId) {
		return this.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DONE);
	}

	@Override
	public VideoUpload saveUploadInfo(String videoName, int siteId, int state) {
		int videoId = this.getVideo(videoName).getVideoId();
		VideoUpload videoUpload = this.getUploadInfo(videoId, siteId);

		LocalDateTime localDateTime = LocalDateTime.now();
		if (videoUpload == null) {
			log.info("db|add a videoUpload");
			videoUpload = new VideoUpload();
			videoUpload.setCreateTime(localDateTime);
		} else {
			log.info("db|update a videoUpload");
		}

		videoUpload.setVideoId(videoId);
		videoUpload.setSiteId(siteId);
		videoUpload.setState(state);
		String postName = VideoUtils.getPostTitle(VideoUtils.getVideoPureName(videoName), siteId);
		videoUpload.setPostName(postName);
		videoUpload.setUpdateTime(localDateTime);

		return this.saveUploadInfo(videoUpload);
	}
}
