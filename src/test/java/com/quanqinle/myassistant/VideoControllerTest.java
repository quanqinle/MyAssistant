package com.quanqinle.myassistant;

import com.quanqinle.myassistant.biz.videoporter.upload.BaseWebDriver;
import com.quanqinle.myassistant.biz.videoporter.upload.Post2XiGuaByWebDriver;
import com.quanqinle.myassistant.controller.VideoController;
import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.po.VideoInfo;
import com.quanqinle.myassistant.entity.po.VideoSite;
import com.quanqinle.myassistant.entity.po.VideoUpload;
import com.quanqinle.myassistant.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VideoControllerTest {
    private Logger log = LoggerFactory.getLogger(VideoControllerTest.class);

	@Autowired
	VideoController videoController;
	@Autowired
	VideoService videoService;
	@Autowired
	Post2XiGuaByWebDriver post2XiGuaByWebDriver;
	@Autowired
	BaseWebDriver baseWebDriver;

//	@Test
	public void testDownloadVideo() {
		String dirPath = "D:/tmp/video-youtube/changed";
		File[] files = new File(dirPath).listFiles();
		for (File file : files) {
			if (file.getName().contains(".mp4")) {
				Result<VideoInfo> Result = videoController.downloadVideo(file.getName());
				log.info(Result.toString());
			}
		}
	}

	/**
	 * 测试更新upload表信息
	 */
//	@Test
	public void testUpgradeUploadInfo() {
		String dirPath = "D:/tmp/video-youtube/changed/已上传2";
		File[] files = new File(dirPath).listFiles();
		for (File file : files) {
			if (file.getName().contains(".mp4")) {
				for (int i = 2; i < 5; i++) {
					Result<VideoUpload> Result = videoController.uploadVideo(file.getName(), i);
					log.info(Result.toString());
				}
			}
		}
	}

//	@Test
	public void testUploadVideoInfo() {
		List<VideoInfo> videoInfos = videoService.getVideos();
		for (VideoInfo info: videoInfos) {
			for (int i = 2; i < 5; i++) {
				Result<VideoUpload> Result = videoController.uploadVideo(info.getVideoName(), i);
				log.info(Result.toString());
			}
		}
	}

//	@Test
	public void testUpdateSite() {
		VideoSite site = videoService.getVideoSite(4);
		site.setCookie("123");
		videoService.addVideoSite(site);
	}

//	@Test
	public void testGetVideosUnpublished() {
		int siteId = 2;
		List<VideoInfo> videos = videoService.getVideosUnpublished(siteId);
		log.info(videos.toString());
	}

//	@Test
	public void testPost2XiGua() {
		String video = "Count & Move from Super Simple Songs-g9EgE_JtEAw.mp4";

		post2XiGuaByWebDriver.startDriver();
		post2XiGuaByWebDriver.postToXiGua(video);
		post2XiGuaByWebDriver.closeDriver();
	}

}
