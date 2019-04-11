package com.quanqinle.myworld.biz.videoporter.upload;

import com.quanqinle.myworld.biz.videoporter.VideoUtils;
import com.quanqinle.myworld.entity.po.VideoSite;
import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * 发布视频到西瓜视频
 * @author quanql
 */
public class Post2XiguaByWebdriver extends BaseWebdriver {

	static Log log = LogFactory.getLog(Post2XiguaByWebdriver.class);

	@Autowired
	VideoService videoService;

	public Post2XiguaByWebdriver() {
	}

	@Override
	public void initAll() {
		super.initAll();

		VideoSite site = videoService.getVideoSite(VideoUtils.XIGUA);
		driver.get(site.getUploadUrl());

		List<Cookie> cookies = parseRawCookie(site.getCookie());
		log.info(cookies);
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}

		video = "Brush Your Teeth _ Kids Songs _  Super Simple Songs-wCio_xVlgQ0.mp4";
	}


	//	@Test
	public void testXigua() {

		driver.get("https://mp.toutiao.com/profile_v3/xigua/upload-video");

		uploadVideo();
		isUploadFinished();
		inputTitle();
		inputContent();
		setCover();
		inputTags();
//		setBenefit();
		selectCategory();
		selectActivity();
		publishVideo();
		downloadCover();
	}

	void uploadVideo() {
		/*
		 * 上传视频
		 */
		log.info("add video: " + video);
		By byAddFile = By.xpath("//div[text()='上传视频']/..//input[@type='file']");
		WebElement elBbtnAddFile = wait60s.until(ExpectedConditions.presenceOfElementLocated(byAddFile));
//		assertNotNull(elBbtnAddFile, "fail to locate add video button");
		elBbtnAddFile.sendKeys(VideoUtils.pathStr + video);
	}
	void isUploadFinished() {
		/*
		 * 是否视频上传完毕
		 */
		String uploadStatus = "//div[@class='item-upload-success']//span[contains(text(),'剩余时间')]";
		WebElement elUploadStatus = wait60s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to check uploading status!");
		uploadStatus = "//div[@class='item-upload-success']//span[text()='上传完毕']";
		elUploadStatus = wait120s.until(ExpectedConditions.presenceOfElementLocated(By.xpath(uploadStatus)));
		assertNotNull(elUploadStatus, "fail to check uploaded status!");
		log.info("uploading video is accomplished");
	}
	void inputTitle() {
		/*
		 * 标题
		 */
		title = VideoUtils.getVideoPureName(video);
		String xTitle = "//input[contains(@placeholder, '标题') and @type='text']";
		WebElement elTitle = driver.findElement(By.xpath(xTitle));
		assertNotNull(elTitle, "fail to locate title!");
		elTitle.clear();
		elTitle.sendKeys("英文儿歌｜" + title);
//		VideoUtils.SetElementValue(driver, elTitle, "英文儿歌｜" + title);
		log.info("title: 英文儿歌｜" +  title);
	}
	void inputContent() {
		/*
		 * 介绍
		 */
		String content = VideoUtils.getPostContent(title);
		log.info("description: \n" + content);
		WebElement elContent = driver.findElement(By.xpath("//textarea[contains(@placeholder, '视频简介')]"));
		assertNotNull(elContent, "fail to locate description!");
		elContent.clear();
		elContent.sendKeys(content);
//		VideoUtils.SetElementValue(driver, elContent, content);
	}
	void setCover() {
		/*
		 * 设置封面
		 */
		log.info("setting cover...");
		By bySetCover = By.xpath("//img[@alt='设置封面']");
		WebElement elSetCover = driver.findElement(bySetCover);
		new Actions(driver).moveToElement(elSetCover).build().perform();
		wait60s.until(ExpectedConditions.visibilityOf(elSetCover)).click();
		By byTab1OnDialog = By.xpath("//ul[@role='tabList']//li[text()='自定义封面']");
		By byTab2OnDialog = By.xpath("//ul[@role='tabList']//li[text()='系统封面']");
		WebElement elTab2 = wait10s.until(ExpectedConditions.elementToBeClickable(byTab2OnDialog));

		By byImg = By.xpath("//div[@role='tabPanel']//ul[@class='system-items']//img[@alt='封面']");
		// 视频处理中期间，不能选择系统截图。重试3次
		for (int i = 0; i < 3; i++) {
			try {
				elTab2.click();
				wait10s.until(ExpectedConditions.visibilityOfElementLocated(byImg));
			} catch (Exception e) {
				driver.findElement(byTab1OnDialog).click();
			}
		}

		List<WebElement> elImgs = driver.findElements(byImg);
		// 选图片作为封面
		elImgs.get(elImgs.size() / 2 > 6 ? 6 : elImgs.size() / 2).click();
		driver.findElement(By.xpath("//button[text()='确 定']")).click();
	}
	void inputTags() {
		/*
		 * 标签
		 */
		String[] tags = { "英语儿歌", "童谣", "歌曲", "听力", "教育" };
		log.info("tags: " + Arrays.toString(tags));
		WebElement elTag = null;
		for (String tag : tags) {
			elTag = driver.findElement(By.cssSelector(".input-tag input[role='combobox']"));
			assertNotNull(elTag, "fail to local tag: " + tag);
			elTag.sendKeys(tag);
			// ENTER made the tag complete
			elTag.sendKeys(Keys.ENTER);
			BaseWebdriver.wait(100);
		}
	}
	void setBenefit() {
		/*
		 * 收益
		 */
		log.info("click benefit");
		String benefit = "//span[text()='授权投放广告有收益']/..//input[@type='checkbox']";
		WebElement elBenefit = driver.findElement(By.xpath(benefit));
		elBenefit.click();
	}
	void selectCategory() {

	}
	void selectActivity() {
		log.info("click the 1st activity");
		String xActivity = "//div[text()='参与活动']/..//div[@class='tui2-radio-group']//input[@type='checkbox']";
		driver.findElement(By.xpath(xActivity)).click();
	}
	void publishVideo() {
		/*
		 * 发布
		 */
		log.info("click publish");
		driver.findElement(By.xpath("//div[contains(@class, 'submit') and text()='发表']")).click();

		/**
		 * when publish successfully, menu will be automated switched to another
		 */
		By byLeftMenu = By.xpath("//a[text()='发表视频' and @aria-current='page']");
		boolean isInvisibility = wait60s.until(ExpectedConditions.invisibilityOfElementLocated(byLeftMenu));
		assertTrue(isInvisibility, "fail to publish video");
		log.info("complete posting video: " + video);
	}
	void downloadCover() {
		// 下载封面图
		driver.get("https://mp.toutiao.com/profile_v3/xigua");
		By byCover = By.xpath("//div[@class='article-card-home']//img");
		WebElement elCover = wait60s.until(ExpectedConditions.presenceOfElementLocated(byCover));
		String imgSrcUrl = elCover.getAttribute("src");
		VideoUtils.downloadFile(imgSrcUrl, VideoUtils.pathStr + title + ".png");
		log.info("download video cover: " + title + ".png");
	}
}
