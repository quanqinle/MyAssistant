package com.quanqinle.myworld;

import com.quanqinle.myworld.biz.videoporter.VideoUtils;
import com.quanqinle.myworld.biz.videoporter.upload.Post2XiGuaByWebDriver;
import com.quanqinle.myworld.controller.VideoController;
import com.quanqinle.myworld.entity.po.VideoInfo;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.entity.po.VideoUpload;
import com.quanqinle.myworld.entity.vo.ResultVo;
import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VideoControllerTest {
	private Log log = LogFactory.getLog(VideoControllerTest.class);

	@Autowired
	VideoController videoController;
	@Autowired
	VideoService videoService;
	@Autowired
	Post2XiGuaByWebDriver post2XiGuaByWebDriver;

//	@Test
	public void testDownloadVideo() {
		String dirPath = "D:/tmp/video-youtube/changed";
		File[] files = new File(dirPath).listFiles();
		for (File file : files) {
			if (file.getName().contains(".mp4")) {
				ResultVo<VideoInfo> resultVo = videoController.downloadVideo(file.getName());
				log.info(resultVo);
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
					ResultVo<VideoUpload> resultVo = videoController.uploadVideo(file.getName(), i);
					log.info(resultVo);
				}
			}
		}

		ResultVo<VideoUpload> resultVo = videoController.uploadVideo("Clean Up Song _ Kids Song for Tidying Up _ Super Simple Songs-SFE0mMWbA-Y.mp4", VideoUtils.XIGUA);
		log.info(resultVo);
		resultVo = videoController.uploadVideo("Count & Move from Super Simple Songs-g9EgE_JtEAw.mp4", VideoUtils.XIGUA);
		log.info(resultVo);
	}

//	@Test
	public void testUploadVideoInfo() {
		List<VideoInfo> videoInfos = videoService.getVideos();
		for (VideoInfo info: videoInfos) {
			for (int i = 2; i < 5; i++) {
				ResultVo<VideoUpload> resultVo = videoController.uploadVideo(info.getVideoName(), i);
				log.info(resultVo);
			}
		}
	}

//	@Test
	public void testUpdateSite() {
		VideoSite site = videoService.getVideoSite(4);
		site.setCookie("123");
		videoService.updateVidetSite(site);
	}

	@Test
	public void testGetVideosUnpublished() {
		int siteId = 2;
		List<VideoInfo> videos = videoService.getVideosUnpublished(siteId);
		log.info(videos);
	}

//	@Test
	public void testPost2XiGua() {
		post2XiGuaByWebDriver.startDriver();
		post2XiGuaByWebDriver.postToXiGua();
		post2XiGuaByWebDriver.closeDriver();
	}

}
