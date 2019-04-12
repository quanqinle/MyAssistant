package com.quanqinle.myworld.biz.videoporter.upload;

import com.quanqinle.myworld.service.VideoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
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

	final VideoService videoService;

	static WebDriver driver;
	static WebDriverWait wait120s;
	static WebDriverWait wait60s;
	static WebDriverWait wait10s;

	String video;
	String title;

	@Autowired
	public BaseWebDriver(VideoService videoService) {
		this.videoService = videoService;
	}

	public void startDriver() {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
//		options.addArguments("headless");

		driver = new ChromeDriver(options);
		wait120s = new WebDriverWait(driver, 120, 200);
		wait60s = new WebDriverWait(driver, 60, 200);
		wait10s = new WebDriverWait(driver, 10, 200);
	}

	public void closeDriver() {
		driver.quit();
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

	public static List<Cookie> parseRawCookie(String rawCookie) {
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
}
