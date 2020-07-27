package com.quanqinle.mysecretary.biz.videoporter.upload;

import com.quanqinle.mysecretary.biz.videoporter.VideoUtils;
import com.quanqinle.mysecretary.entity.po.VideoSite;
import com.quanqinle.mysecretary.service.VideoService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;


/**
 * 发布视频到大鱼号视频
 * @author quanql
 */
@Component
public class Post2DaYuByWebDriver extends BaseWebDriver{
    private Logger log = LoggerFactory.getLogger(Post2DaYuByWebDriver.class);

	private String video;
	private String title;
	private VideoSite site;

	public Post2DaYuByWebDriver(VideoService videoService) {
		super(videoService);
	}

	@Override
	public void startDriver() {
		super.startDriver();

		site = videoService.getVideoSite(VideoUtils.DAYU);
		driver.get(site.getUploadUrl());

		super.addCookies(site.getCookie());
	}

	/**
	 * 向一点号发布视频
	 */
	public void postToDaYu(String videoName) {

		video = videoName;

		log.info("打开大鱼号");
		driver.get(site.getUploadUrl());

		uploadVideo();
		isUploadFinished();
		inputTitle();
		inputContent();
		new Actions(driver).sendKeys(Keys.PAGE_DOWN).build().perform();
		// 太复杂，放弃了
//		inputTags();
		inputCategory();
		setCover();
		publishVideo();
	}

	private void uploadVideo() {
		/*
		 * 上传视频
		 */
		log.info("add video: " + video);
		By byAddFile = By.xpath("//div[contains(@class,'localVideoUpload') and ..//div[text()='本地上传'] ]");
		WebElement elBbtnAddFile = wait60s.until(ExpectedConditions.presenceOfElementLocated(byAddFile));
		assertNotNull(elBbtnAddFile, "fail to locate add video button");
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(videoPath + video);
	}

	private void isUploadFinished() {
		/*
		 * 是否视频上传完毕
		 */
		String uploadStatus = "//div[contains(@class,'article-write_video-container-uploading_status') and contains(text(),'上传中')]";
		WebElement elUploadStatus = wait10s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to start uploading!");
		boolean isInvisibility = wait120s.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(uploadStatus)));
		assertTrue(isInvisibility, "fail to finish uploading!");
		/*
		 * 是否视频处理完毕
		 */
		uploadStatus = "//div[contains(@class,'article-write_video-container')]//p[contains(text(),'视频上传成功，处理中')]";
		wait60s.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(uploadStatus)));
		assertTrue(isInvisibility, "fail to start processing video!");
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
		elTitle.sendKeys(getPostTitle(title, VideoUtils.DAYU));
	}
	/**
	 * 描述
	 */
	private void inputContent() {
		String content = getPostContent(title);
		log.info("description: \n" + content);
		WebElement elContent = driver.findElement(By.xpath("//label[text()='视频简介']/..//textarea"));
		assertNotNull(elContent, "fail to locate description!");
		elContent.clear();
		elContent.sendKeys(content);
	}

	/**
	 * 标签
	 */
	private void inputTags() {
		String[] tags = getPostTags();
		log.info("tags");
		WebElement elTag;
		elTag = driver.findElement(By.xpath("//div[@class='article-write_video-tags-label']"));
		assertNotNull(elTag, "fail to local tag element");
		// 无效
		new Actions(driver).moveToElement(elTag).build().perform();

		for (String tag : tags) {
			// FIXME
			elTag = driver.findElement(By.xpath("//div[@class='article-write_video-tags-label']"));
			assertNotNull(elTag, "fail to local tag: " + tag);
			elTag.sendKeys(tag);
			log.info(driver.findElement(By.xpath("//label[text()='视频标签']/..//div[@class='w-form-field-content']")).getAttribute("innerHTML"));
			// ENTER made the tag complete
			elTag.sendKeys(Keys.ENTER);
			wait(100);
			log.info(driver.findElement(By.xpath("//label[text()='视频标签']/..//div[@class='w-form-field-content']")).getAttribute("innerHTML"));
		}
	}

	/**
	 * 分类
	 */
	private void inputCategory() {
		log.info("category");
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		String js;
		// 23-->教育
		js = "document.querySelector('.widgets-selects_select_container a:nth-child(23)').click();";
		jse.executeScript(js);
	}
	/**
	 * 设置封面
	 */
	private void setCover() {
		log.info("setting cover...");
		By bySetCover = By.xpath("//div[@id='coverImg']//span[text()='设置封面']");
		WebElement elSetCover = driver.findElement(bySetCover);
		new Actions(driver).moveToElement(elSetCover).build().perform();
		driver.findElement(By.xpath("//div[@id='coverImg']//input")).sendKeys(coverPath + title + ".png");
		By bySaveBtn = By.xpath("//div[contains(@class,'article-material-image-dialog')]//button[text()='保存']");
		wait60s.until(ExpectedConditions.elementToBeClickable(bySaveBtn)).click();
	}

	/**
	 * 发布
	 */
	private void publishVideo() {
		log.info("click publish");

		new Actions(driver).sendKeys(Keys.END).build().perform();
		wait(2000);

		wait10s.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='发表']"))).click();
		wait10s.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='确认发表']"))).click();

		/*
		 * when publish successfully, menu will be automated switched to 视频主页
		 */
		By byLeftMenu = By.xpath("//a[contains(@class,'w-menu-active') and .//*[text()='我的作品']]");
		wait60s.until(ExpectedConditions.presenceOfElementLocated(byLeftMenu));
		log.info("complete posting video: " + video);
	}

}
