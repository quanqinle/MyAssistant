package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.biz.videoporter.VideoUtils;
import com.quanqinle.mysecretary.biz.videoporter.upload.Post2DaYuByWebDriver;
import com.quanqinle.mysecretary.biz.videoporter.upload.Post2XiGuaByWebDriver;
import com.quanqinle.mysecretary.biz.videoporter.upload.Post2YiDianHaoByWebDriver;
import com.quanqinle.mysecretary.entity.po.VideoInfo;
import com.quanqinle.mysecretary.entity.po.VideoSite;
import com.quanqinle.mysecretary.entity.po.VideoUpload;
import com.quanqinle.mysecretary.entity.vo.ResultVo;
import com.quanqinle.mysecretary.service.VideoService;
import com.quanqinle.mysecretary.util.SystemCommandUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Math.min;

/**
 * 视频搬运
 *
 * @author quanql
 */
@Controller
@RequestMapping("/video")
@Api(value = "VideoController", tags = {"视频搬运"})
public class VideoController {
    private Logger log = LoggerFactory.getLogger(VideoController.class);

    private final VideoService videoService;
    private final Post2XiGuaByWebDriver post2XiGuaByWebDriver;
    private final Post2YiDianHaoByWebDriver post2YiDianHaoByWebDriver;
    private final Post2DaYuByWebDriver post2DaYuByWebDriver;

    @Autowired
    public VideoController(VideoService videoService,
                           Post2XiGuaByWebDriver post2XiGuaByWebDriver,
                           Post2YiDianHaoByWebDriver post2YiDianHaoByWebDriver,
                           Post2DaYuByWebDriver post2DaYuByWebDriver) {
        this.videoService = videoService;
        this.post2XiGuaByWebDriver = post2XiGuaByWebDriver;
        this.post2YiDianHaoByWebDriver = post2YiDianHaoByWebDriver;
        this.post2DaYuByWebDriver = post2DaYuByWebDriver;
    }

    @GetMapping("/site/list")
    @ResponseBody
    @ApiOperation(value = "获取所有网站信息")
    public ResultVo<List<VideoSite>> allSites() {
        List<VideoSite> sites = videoService.getVideoSites();
        return new ResultVo<>(200, "ok", sites);
    }

    @GetMapping("/site/{siteId}")
    @ResponseBody
    @ApiOperation(value = "获取某个网站信息")
    public ResultVo<VideoSite> getSite(@PathVariable int siteId) {
        VideoSite site = videoService.getVideoSite(siteId);
        return new ResultVo<>(200, "ok", site);
    }

    @PostMapping("/site/save")
    @ResponseBody
    @ApiOperation(value = "更新网站信息")
    public ResultVo<VideoSite> updateCookie(int siteId, String cookie) {
        VideoSite site = videoService.getVideoSite(siteId);
        site.setCookie(cookie);
        return new ResultVo<>(200, "ok", videoService.addVideoSite(site));
    }

    @GetMapping("/unpublished/{siteId}")
    @ResponseBody
    @ApiOperation(value = "获取尚未上传到某网站的视频信息")
    public ResultVo<List<VideoInfo>> getUnpublishedVideo(@PathVariable int siteId) {
        List<VideoInfo> videos = videoService.getVideosUnpublished(siteId);
        return new ResultVo<>(200, "ok", videos);
    }

    @GetMapping("/published/{siteId}")
    @ResponseBody
    @ApiOperation(value = "获取已上传到某网站的视频信息")
    public ResultVo<List<VideoUpload>> getPublishedVideo(@PathVariable int siteId) {
        List<VideoUpload> videos = videoService.getUploadInfos(siteId, 0);
        return new ResultVo<>(200, "ok", videos);
    }

    @PostMapping("/download")
    @ResponseBody
    @ApiOperation(value = "下载视频")
    public ResultVo<VideoInfo> downloadVideo(@NonNull String videoName) {
        if (videoName.isEmpty()) {
            return new ResultVo<>(400, "parameter shouldn't be null", null);
        }

        if (null != videoService.getVideo(videoName)) {
            return new ResultVo<>(200, "video is existed", null);
        }

        // TODO
        try {
            return new ResultVo<>(200, "ok", videoService.addVideo(videoName, VideoUtils.YOUTUBE));
        } catch (Exception e) {
            return new ResultVo<>(400, e.toString(), null);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "上传视频")
    public ResultVo<VideoUpload> uploadVideo(String videoName, int siteId) {
        if (videoName.isEmpty()) {
            return new ResultVo<>(400, "para is null", null);
        }

        if (null != videoService.getUploadInfo(videoName, siteId)) {
            return new ResultVo<>(200, "video is existed", null);
        }

        try {
            return new ResultVo<>(200, "ok", videoService.saveUploadInfo(videoName, siteId));
        } catch (Exception e) {
            return new ResultVo<>(400, e.toString(), null);
        }
    }


    /**
     * 向网站{siteId}发布{number}篇视频
     *
     * @param siteId 网站id
     * @param number 发布视频篇数
     * @return
     */
    @GetMapping("/post/{siteId}/{number}")
    @ResponseBody
    @ApiOperation(value = "发布视频")
    public ResultVo<String> postVideo(@PathVariable int siteId, @PathVariable int number) {
        List<VideoInfo> videoList = videoService.getVideosUnpublished(siteId);
        if (videoList == null) {
            return new ResultVo<>(200, "ok", "no video need to be posted!");
        }

        String msg = "post to ";
        int loop = min(number, videoList.size());
        String videoName;
        VideoInfo videoInfo;

        switch (siteId) {
            case VideoUtils.XIGUA: {
                msg += "xigua, plan=" + loop;
                post2XiGuaByWebDriver.startDriver();
                for (int i = 0; i < loop; i++) {
                    videoInfo = videoList.get(i);
                    videoName = videoInfo.getVideoName();
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DOING);
                    post2XiGuaByWebDriver.postToXiGua(videoName);
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DONE);
                }

                post2XiGuaByWebDriver.closeDriver();
                break;
            }
            case VideoUtils.YIDIAN: {
                post2YiDianHaoByWebDriver.startDriver();
                msg += "yidianhao, plan=" + loop;
                for (int i = 0; i < loop; i++) {
                    videoInfo = videoList.get(i);
                    videoName = videoInfo.getVideoName();
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DOING);
                    post2YiDianHaoByWebDriver.postToYiDianHao(videoName);
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DONE);
                }
                post2YiDianHaoByWebDriver.closeDriver();
                break;
            }
            case VideoUtils.DAYU: {
                post2DaYuByWebDriver.startDriver();
                msg += "dayu, plan=" + loop;
                for (int i = 0; i < loop; i++) {
                    videoInfo = videoList.get(i);
                    videoName = videoInfo.getVideoName();
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DOING);
                    post2DaYuByWebDriver.postToDaYu(videoName);
                    videoService.saveUploadInfo(videoName, siteId, VideoUtils.STATE_DONE);
                }
                post2DaYuByWebDriver.closeDriver();
                break;
            }
            default:
                msg = "unknown siteId!";
                log.error("unknown siteId!");
        }

        return new ResultVo<>(200, "ok", msg);
    }

    @GetMapping("/killdriver")
    @ResponseBody
    @ApiOperation(value = "杀掉webdriver系统进程")
    public ResultVo<String> tearDownDriver() {
        SystemCommandUtils.runCmd("TASKKILL", "/F", "/IM", "chromedriver.exe", "/T");
        return new ResultVo<>(200, "ok", null);
    }
}
