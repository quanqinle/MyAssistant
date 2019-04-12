package com.quanqinle.myworld.controller;

import com.quanqinle.myworld.entity.po.VideoInfo;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.entity.po.VideoUpload;
import com.quanqinle.myworld.entity.vo.ResultVo;
import com.quanqinle.myworld.service.VideoService;
import com.quanqinle.myworld.biz.videoporter.VideoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author quanql
 */
@Controller
@RequestMapping("/video")
@Api(value = "VideoController", description = "视频搬运")
public class VideoController {
	Log log = LogFactory.getLog(VideoController.class);

	private final VideoService videoService;

	@Autowired
	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping("/sites.json")
	@ResponseBody
	@ApiOperation(value = "获取所有网站信息")
	public ResultVo<List<VideoSite>> allSites() {
		List<VideoSite> sites = videoService.getVideoSites();
		return new ResultVo(200, "ok", sites);
	}

	@GetMapping("/site/{siteId}")
	@ResponseBody
	@ApiOperation(value = "获取某个网站信息")
	public ResultVo<VideoSite> getSite(@PathVariable int siteId) {
		VideoSite site = videoService.getVideoSite(siteId);
		return new ResultVo(200, "ok", site);
	}

	@PostMapping("/site/save")
	@ResponseBody
	@ApiOperation(value = "更新网站信息")
	public ResultVo<VideoSite> updateCookie(int siteId, String cookie) {
		VideoSite site = videoService.getVideoSite(siteId);
		site.setCookie(cookie);
		return new ResultVo<>(200, "ok", videoService.updateVidetSite(site));
	}

	@GetMapping("/published/{siteId}")
	@ResponseBody
	@ApiOperation(value = "获取已上传到某网站的视频信息")
	public ResultVo<VideoInfo> getPublishedVideo(@PathVariable int siteId) {
		// TODO
		List<VideoInfo> videos = videoService.getVideosUnpublished(siteId);
		return new ResultVo(200, "ok", videos);
	}
	@GetMapping("/unpublished/{siteId}")
	@ResponseBody
	@ApiOperation(value = "获取未上传到某网站的视频信息")
	public ResultVo<VideoInfo> getUnpublishedVideo(@PathVariable int siteId) {
		List<VideoInfo> videos = videoService.getVideosUnpublished(siteId);
		return new ResultVo(200, "ok", videos);
	}

	@PostMapping("/download")
	@ResponseBody
	@ApiOperation(value = "下载视频")
	public ResultVo<VideoInfo> downloadVideo(String videoName) {
		if (videoName.isEmpty() || null == videoName) {
			return new ResultVo(400, "para is null", null);
		}

		if (null != videoService.getVideo(videoName)) {
			return new ResultVo(200, "video is existed", null);
		}
		try {
			VideoInfo videoInfo = new VideoInfo();
			videoInfo.setVideoName(videoName);
			videoInfo.setVideoSn(VideoUtils.parseVideoSN(videoName));
			videoInfo.setSourceSiteId(VideoUtils.YOUTUBE);
			LocalDateTime localDateTime = LocalDateTime.now();
			videoInfo.setCreateTime(localDateTime);
			// LocalDateTime.parse("2019-04-06T18:18:18")
			videoInfo.setUpdateTime(localDateTime);
			return new ResultVo(200, "ok", videoService.addVideo(videoInfo));
		} catch (Exception e) {
			return new ResultVo(400, e.toString(), null);
		}
	}

	@PostMapping("/upload")
	@ResponseBody
	@ApiOperation(value = "上传视频")
	public ResultVo<VideoUpload> uploadVideo(String videoName, int siteId) {
		if (videoName.isEmpty() || null == videoName) {
			return new ResultVo(400, "para is null", null);
		}

		if (null != videoService.getUploadInfo(videoName, siteId)) {
			return new ResultVo(200, "video is existed", null);
		}

		try {
			return new ResultVo(200, "ok", videoService.addUploadInfo(videoName, siteId));
		} catch (Exception e) {
			return new ResultVo(400, e.toString(), null);
		}
	}


}
