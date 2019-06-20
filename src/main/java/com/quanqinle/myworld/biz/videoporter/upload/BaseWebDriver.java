package com.quanqinle.myworld.biz.videoporter.upload;

import com.quanqinle.myworld.biz.videoporter.VideoUtils;
import com.quanqinle.myworld.service.SysDictService;
import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * web driver 基类
 * @author quanql
 */
@Component
public class BaseWebDriver {

	private static Log log = LogFactory.getLog(BaseWebDriver.class);

	@Autowired
	SysDictService sysDictService;
	final VideoService videoService;

	static WebDriver driver;
	static WebDriverWait wait120s;
	static WebDriverWait wait60s;
	static WebDriverWait wait10s;

	String videoPath;
	String coverPath;

	@Autowired
	public BaseWebDriver(VideoService videoService) {
		this.videoService = videoService;
	}

	/**
	 * 启动web driver
	 */
	public void startDriver() {

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");

		String keyHeadless = "webdriver.headless";
		if (sysDictService.isValueTrue(keyHeadless)) {
			options.addArguments("headless");
		}

		driver = new ChromeDriver(options);
		wait120s = new WebDriverWait(driver, 120, 200);
		wait60s = new WebDriverWait(driver, 60, 200);
		wait10s = new WebDriverWait(driver, 10, 200);

		this.videoPath = sysDictService.getValue("video.path", "D:\\tmp\\video-youtube\\changed\\");
		this.coverPath = sysDictService.getValue("cover.path", "D:\\tmp\\video-youtube\\changed-cover\\");
	}

	/**
	 * 给driver增加cookie
	 * @param rawCookie
	 */
	public void addCookies(String rawCookie) {
		List<Cookie> cookies = parseRawCookie(rawCookie);
		log.info(cookies);
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
	}

	/**
	 * 关闭web driver
	 */
	public void closeDriver() {
		driver.quit();
		// TASKKILL /F /IM chromedriver.exe /T
	}

	/**
	 * 线程休眠/等待<br>
	 *
	 * @param millis
	 *          毫秒。<=0时，无效
	 */
	public static void wait(int millis) {
		if (0 >= millis) {
			return;
		}
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.debug("sleep被中断");
		}
	}

	/**
	 * 将cookie字符串拆解到list中
	 * @param rawCookie
	 * @return
	 */
	public List<Cookie> parseRawCookie(String rawCookie) {
		List<Cookie> cookies = new ArrayList<Cookie>();

		String[] rawCookieParams = rawCookie.split(";");

		for (String cookieParam : rawCookieParams) {
			String[] rawCookieNameAndValue = cookieParam.split("=");
			if (rawCookieNameAndValue.length != 2) {
				log.error("Invalid cookie: missing name and value.");
				continue;
			}
			String cookieName = rawCookieNameAndValue[0].trim();
			String cookieValue = rawCookieNameAndValue[1].trim();
			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookies.add(cookie);
		}
		return cookies;
	}

	/**
	 * 获取视频发布的标题
	 * @param videoPureName
	 * @param siteId
	 * @return
	 */
	public String getPostTitle(String videoPureName, int siteId) {
		return VideoUtils.getPostTitle(videoPureName, siteId);
	}
	/**
	 * 获取视频发布的描述
	 * @param videoPureName 视频名称
	 * @return
	 */
	public String getPostContent(String videoPureName) {
		return String.format("# %s \n%s \n%s \n%s \n%s \n%s"
				, videoPureName
				, "# Super Simple Songs"
				, "# 来源：http://油管/user/SuperSimpleSongs 感谢原创，请subscribe她"
				, "# 听儿歌，学英语"
				, "# 节奏轻快，孩子爱听"
				, "# 喜欢就关注我哦~");
	}
	/**
	 * 获取视频发布的描述
	 * @return
	 */
	public String[] getPostTags() {
		String[] tags = { "英语儿歌", "童谣", "歌曲", "听力", "教育" };
		return tags;
	}

	/**
	 * 通过JavaScript实现滚动到某元素。
	 * tips：适用于元素不可见或被遮挡
	 *
	 * @param element
	 */
	public void scrollTo(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
	}
}
