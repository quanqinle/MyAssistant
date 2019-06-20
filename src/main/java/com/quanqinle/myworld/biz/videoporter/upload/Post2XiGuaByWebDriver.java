package com.quanqinle.myworld.biz.videoporter.upload;

import com.quanqinle.myworld.biz.videoporter.VideoUtils;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * 发布视频到（今日头条的）西瓜视频
 * @author quanql
 */
@Component
public class Post2XiGuaByWebDriver extends BaseWebDriver {

	private Log log = LogFactory.getLog(Post2XiGuaByWebDriver.class);

	private String video;
	private String title;
	private VideoSite site;

	public Post2XiGuaByWebDriver(VideoService videoService) {
		super(videoService);
	}

	@Override
	public void startDriver() {
		super.startDriver();

		site = videoService.getVideoSite(VideoUtils.XIGUA);
		driver.get(site.getUploadUrl());

		super.addCookies(site.getCookie());
	}

	/**
	 * 向西瓜发布视频
	 */
	public void postToXiGua(String videoName) {

		video = videoName;

		log.info("打开西瓜视频");
		driver.get(site.getUploadUrl());

		uploadVideo();
		isUploadFinished();
		inputTitle();
		setCover();
		inputContent();
		setBenefit();
		selectActivity();
		inputTags();
//		selectCategory();

		publishVideo();
		downloadCover();
	}

	/**
	 * 上传视频
	 */
	private void uploadVideo() {
		log.info("add video: " + video);
		By byAddFile = By.xpath("//div[text()='上传视频']/..//input[@type='file']");
		WebElement elBtnAddFile = wait60s.until(ExpectedConditions.presenceOfElementLocated(byAddFile));
		assertNotNull(elBtnAddFile, "fail to locate add video button");
		elBtnAddFile.sendKeys(videoPath + video);
	}
	/**
	 * 是否视频上传完毕
	 */
	private void isUploadFinished() {
		String uploadStatus = "//div[@class='upload-btn']//span[contains(text(),'上传中')]";
		WebElement elUploadStatus = wait60s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to check uploading status!");
		uploadStatus = "//div[@class='upload-btn']//span[text()='上传成功']";
		elUploadStatus = wait120s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to check uploaded status!");
		log.info("uploading video is accomplished");
	}
	/**
	 * 标题
	 */
	private void inputTitle() {
		title = VideoUtils.getVideoPureName(video);
		log.info("title: " + title);
		String xTitle = "//input[contains(@placeholder, '标题') and @type='text']";
		WebElement elTitle = driver.findElement(By.xpath(xTitle));
		assertNotNull(elTitle, "fail to locate title!");
		elTitle.clear();
		/**
		 * failed the followed code:
		 * VideoUtils.SetElementValue(driver, elTitle, "英文儿歌｜" + title)
		 */
		elTitle.sendKeys(getPostTitle(title, VideoUtils.XIGUA));
	}
	/**
	 * 介绍
	 */
	private void inputContent() {
		String content = super.getPostContent(title);
		log.info("description: \n" + content);
		WebElement elContent = driver.findElement(By.xpath("//textarea[contains(@placeholder, '视频简介')]"));
		assertNotNull(elContent, "fail to locate description!");
		elContent.clear();
		elContent.sendKeys(content);
	}
	/**
	 * 设置封面
	 */
	private void setCover() {
		log.info("setting cover...");

		String bgGenerate = "//div[@class='m-loading' and contains(text(),'视频封面获取中')]";
		WebElement elBgStatus = wait60s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(bgGenerate)));
		assertNotNull(elBgStatus, "fail to start cover generation!");

		By byImg = By.xpath("//div[@class='m-server-bg-list']//img[contains(@class,'system-item-bg')]");
		elBgStatus = wait60s.until(ExpectedConditions.elementToBeClickable(byImg));
		assertNotNull(elBgStatus, "fail to check cover generation status!");
		log.info("cover generation is accomplished");

		List<WebElement> elImgs = driver.findElements(byImg);
		// 选图片作为封面
		elImgs.get(elImgs.size() / 2 > 4 ? 4 : elImgs.size() / 2).click();
	}
	/**
	 * 标签
	 */
	private void inputTags() {
		String[] tags = super.getPostTags();
		log.info("tags: " + Arrays.toString(tags));
		WebElement elTag;
		for (String tag : tags) {
			elTag = driver.findElement(By.cssSelector(".input-tag input[role='combobox']"));
			assertNotNull(elTag, "fail to local tag: " + tag);
			elTag.sendKeys(tag);
			// ENTER made the tag complete
			elTag.sendKeys(Keys.ENTER);
			BaseWebDriver.wait(100);
		}
	}
	/**
	 * 收益
	 */
	private void setBenefit() {
		log.info("click benefit");
		// default status is ok!
//		String benefit = "//span[text()='授权投放广告有收益']/..//input[@type='checkbox']";
//		WebElement elBenefit = driver.findElement(By.xpath(benefit));
//		elBenefit.click();

		String award = "//div[contains(text(),'金秒奖')]/..//div[@class='switch-content']";
		WebElement elAward = driver.findElement(By.xpath(award));
		super.scrollTo(elAward);
		elAward.click();
	}

	/**
	 * 选择分类
	 */
	private void selectCategory() {
		// TODO
	}

	/**
	 * 选择第一个活动
	 */
	private void selectActivity() {
		log.info("click the 1st activity");
		String xActivity = "//div[text()='参与活动']/..//div[@class='tui2-radio-group']//input[@type='checkbox']";
		driver.findElement(By.xpath(xActivity)).click();
	}
	/**
	 * 发布
	 */
	private void publishVideo() {
		log.info("click publish");
		driver.findElement(By.xpath("//div[contains(@class, 'submit') and text()='发表']")).click();

		/*
		 * when publish successfully, menu will be automated switched to another
		 */
		By byLeftMenu = By.xpath("//a[text()='发表视频' and @aria-current='page']");
		boolean isInvisibility = wait60s.until(ExpectedConditions.invisibilityOfElementLocated(byLeftMenu));
		assertTrue(isInvisibility, "fail to publish video");
		log.info("complete posting video: " + video);
	}
	/**
	 * 下载封面图
	 */
	private void downloadCover() {
		// 内容管理页面
		driver.get("https://mp.toutiao.com/profile_v3/xigua/content-manage");
		By byCover = By.xpath("//div[@class='article-card']//img[@class='hover-img']");

		WebElement elCover = wait60s.until(ExpectedConditions.presenceOfElementLocated(byCover));
		String imgSrcUrl = elCover.getAttribute("src");
		VideoUtils.downloadFile(imgSrcUrl, coverPath + title + ".png");
		log.info("download video cover: " + title + ".png");
	}
}
