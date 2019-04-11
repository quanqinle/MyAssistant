package com.quanqinle.myworld.service.impl;

import com.quanqinle.myworld.dao.VideoInfoRepository;
import com.quanqinle.myworld.dao.VideoUploadRepository;
import com.quanqinle.myworld.dao.VideoSiteRepository;
import com.quanqinle.myworld.entity.po.VideoInfo;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.entity.po.VideoUpload;
import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author quanqinle
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImpl implements VideoService {

	Log log = LogFactory.getLog(VideoServiceImpl.class);

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
	public VideoSite updateVidetSite(VideoSite videoSite) {
		return videoSiteRepository.save(videoSite);
	}

	@Override
	public VideoInfo addVideo(VideoInfo videoInfo) {
		return videoInfoRepository.save(videoInfo);
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
	public VideoUpload getUploadInfo(String videoName, int siteId) {
		VideoInfo videoInfo = this.getVideo(videoName);
		if (null == videoInfo) {
			return null;
		} else {
			return this.getUploadInfo(videoInfo.getVideoId(), siteId, 0);
		}
	}

	@Override
	public VideoUpload getUploadInfo(int videoId, int siteId, int state) {
		return videoUploadRepository.findByVideoIdAndSiteIdAndState(videoId, siteId, state);
	}

	@Override
	public VideoUpload addUploadInfo(VideoUpload videoUpload) {
		return videoUploadRepository.save(videoUpload);
	}
}
