package com.quanqinle.mysecretary.biz.videoporter.upload;

import com.quanqinle.mysecretary.biz.videoporter.VideoUtils;
import com.quanqinle.mysecretary.entity.po.VideoSite;
import com.quanqinle.mysecretary.service.VideoService;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * 发布视频到一点号视频
 * @author quanql
 */
@Component
public class Post2YiDianHaoByWebDriver extends BaseWebDriver {

    private Logger log = LoggerFactory.getLogger(Post2YiDianHaoByWebDriver.class);

	private String video;
	private String title;
	private VideoSite site;

	public Post2YiDianHaoByWebDriver(VideoService videoService) {
		super(videoService);
	}

	@Override
	public void startDriver() {
		super.startDriver();

		site = videoService.getVideoSite(VideoUtils.YIDIAN);
		driver.get(site.getUploadUrl());

		super.addCookies(site.getCookie());
	}

	/**
	 * 向一点号发布视频
	 */
	public void postToYiDianHao(String videoName) {

		video = videoName;

		log.info("打开一点号");
		// FIXME 2次打开时失效
		driver.get(site.getUploadUrl());
		wait(1000);
		driver.navigate().refresh();
		wait(1000);

		uploadVideo();
		isUploadFinished();
		inputTitle();
		selectCategory();
		setCover();
		inputTags();
		inputContent();
		setCopyRight();
//		selectActivity();
		publishVideo();
	}
	/**
	 * 上传视频
	 */
	private void uploadVideo() {
		log.info("add video: " + video);
		By byAddFile = By.xpath("//input[@type='file' and ../..//button[text()='视频上传']]");
		WebElement elBtnAddFile = wait60s.until(ExpectedConditions.presenceOfElementLocated(byAddFile));
		assertNotNull(elBtnAddFile, "fail to locate add video button");
		elBtnAddFile.sendKeys(videoPath + video);
	}
	/**
	 * 是否视频上传完毕
	 */
	private void isUploadFinished() {
		String uploadStatus = "//span[@class='mp-success']";
		WebElement elUploadStatus = wait120s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to check uploaded status!");
		log.info("uploading video is accomplished");
	}
	/**
	 * 标题
	 */
	private void inputTitle() {
		title = VideoUtils.getVideoPureName(video);
		log.info("title: " + title);
		String xTitle = "//div[text()='标题']/..//input";
		WebElement elTitle = driver.findElement(By.xpath(xTitle));
		assertNotNull(elTitle, "fail to locate title!");
		elTitle.clear();
		elTitle.sendKeys(getPostTitle(title, VideoUtils.YIDIAN));
	}
	/**
	 * 介绍
	 */
	private void inputContent() {
		String content = super.getPostContent(title);
		log.info("description: \n" + content);
		WebElement elContent = driver.findElement(By.xpath("//div[text()='简介']/..//textarea[contains(@placeholder, '简介')]"));
		assertNotNull(elContent, "fail to locate description!");
		elContent.clear();
		elContent.sendKeys(content);
	}
	/**
	 * 设置封面
	 */
	private void setCoverUsingLocalPic() {
		log.info("setting cover...");
		driver.findElement(By.xpath("//div[@class='cover-container']")).click();
		By byTab = By.xpath("//div[@class='upload-container']//div[contains(text(),'本地上传')]");
		wait10s.until(ExpectedConditions.elementToBeClickable(byTab)).click();
		By byInput = By.xpath("//div[@class='upload-btn' and contains(text(), '上传封面')]//input");
		driver.findElement(byInput).sendKeys(coverPath + title + ".png");
		By byOk = By.xpath("//div[@class='upload-video-pic']//div[text()='确定']");
		wait60s.until(ExpectedConditions.elementToBeClickable(byOk)).click();
	}
	private void setCover() {
		log.info("setting cover...");
		driver.findElement(By.xpath("//div[@class='cover-container']")).click();
		By byImg = By.xpath("//div[@class='video-screen-shot']//div[@class='img-item']");
		WebElement elBgStatus = wait60s.until(ExpectedConditions.elementToBeClickable(byImg));
		assertNotNull(elBgStatus, "fail to check cover generation status!");
		log.info("cover generation is accomplished");

		List<WebElement> elImgs = driver.findElements(byImg);
		// 选图片作为封面
		elImgs.get(elImgs.size() / 2 > 4 ? 4 : elImgs.size() / 2).click();
		By byOk = By.xpath("//div[@class='screenshot-box']//div[text()='确定']");
		wait10s.until(ExpectedConditions.elementToBeClickable(byOk));
		//.click()总失败，改成js
		super.clickByJS(driver.findElement(byOk));
		wait10s.until(ExpectedConditions.invisibilityOfElementLocated(byOk));
	}
	/**
	 * 标签
	 */
	private void inputTags() {
		String[] tags = super.getPostTags();
		log.info("tags");
		WebElement elTag;
		for (String tag : tags) {
			elTag = driver.findElement(By.xpath("//input[contains(@placeholder,'标签')]"));
			assertNotNull(elTag, "fail to local tag: " + tag);
			elTag.sendKeys(tag);
			// ENTER made the tag complete
			elTag.sendKeys(Keys.ENTER);
			wait(100);
		}
	}
	/**
	 * 原创
	 */
	private void setCopyRight() {

	}
	/**
	 * 选择分类
	 * 通过js选中分类
	 */
	private void selectCategory() {
		log.info("select category");
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String js;
		js = "document.querySelectorAll('.select-menu')[0].querySelector('.select-option:nth-child(24)').click();";
		jse.executeScript(js);
		js = "document.querySelectorAll('.select-menu')[1].querySelector('div.select-option:nth-child(6)').click();";
		jse.executeScript(js);
	}
	/**
	 * 选择第一个活动
	 */
	private void selectActivity() {
		log.info("click the 1st activity");
		List<WebElement> elActs = driver.findElements(By.xpath("//div[@class='activity-list']//i"));
		elActs.get(0).click();
	}
	/**
	 * 发布
	 */
	private void publishVideo() {
		log.info("click publish");
		driver.findElement(By.xpath("//button[text()='发布']")).click();
		wait10s.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dialog']//button[text()='确定']"))).click();

		/*
		 * when publish successfully, menu will be automated switched to 视频主页
		 */
		By byLeftMenu = By.xpath("//a[text()='内容管理' and @class='menu-item current']");
		wait60s.until(ExpectedConditions.visibilityOfElementLocated(byLeftMenu));
		log.info("complete posting video: " + video);
	}

}
