package com.quanqinle.myworld;

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
	Log log = LogFactory.getLog(VideoControllerTest.class);

	@Autowired
	VideoController videoController;
	@Autowired
	VideoService videoService;

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

//	@Test
	public void testPostVideo() {
		List<VideoInfo> videoInfos = videoService.getVideos();
		for (VideoInfo info: videoInfos) {
			for (int i = 2; i < 5; i++) {
				ResultVo<VideoUpload> resultVo = videoController.uploadVideo(info.getVideoName(), i);
				log.info(resultVo);
			}
		}
	}

	@Test
	public void testUpdateSite() {
		VideoSite site = videoService.getVideoSite(4);
		site.setCookie("123");
		videoService.updateVidetSite(site);
	}

}
