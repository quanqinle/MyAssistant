package com.quanqinle.mysecretary.biz.videoporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Math.min;

/**
 * @author quanql
 */
public class VideoUtils {
	public static Log log = LogFactory.getLog(VideoUtils.class);

	/**
	 * site id
	 */
	public static final int YOUTUBE = 1;
	public static final int XIGUA = 2;
	public static final int YIDIAN = 3;
	public static final int DAYU = 4;

	/**
	 * state 视频发布状态
	 */
	public static int STATE_DONE = 0;
	public static int STATE_DOING = 1;
	public static int STATE_FAIL = 2;
	public static int STATE_DELETE = 3;

	/**
	 * strings in video name
	 */
	private static String KEY_SLOGAN = "Super Simple Songs";
	private static String KEY_SEPARATOR = "-";
	private static String VIDEO_SUFFIX = ".mp4";

	/**
	 * get unique code from video name
	 *
	 * @param videoName
	 * @return
	 */
	public static String parseVideoSN(String videoName) {
		String sn = "";
		if (null == videoName || videoName.isEmpty()) {
			return null;
		} else if (videoName.contains(KEY_SLOGAN)) {
			// Brush Your Teeth _ Kids Songs _  Super Simple Songs-wCio_xVlgQ0.mp4
			sn = videoName.substring(videoName.lastIndexOf(KEY_SLOGAN), videoName.indexOf(VIDEO_SUFFIX));
			sn = sn.replace(KEY_SLOGAN + "!" + KEY_SEPARATOR, "");
			sn = sn.replace(KEY_SLOGAN + KEY_SEPARATOR, "");
			sn = sn.replace(KEY_SLOGAN, "");
			sn = sn.trim();
		} else if (videoName.contains(KEY_SEPARATOR)) {
			// Days of the Week - Read It!-Po32PQoPcMI.mp4
			String[] arr = videoName.replace(VIDEO_SUFFIX, "").split(KEY_SEPARATOR);
			int arrLength = arr.length;
			for (int i = arrLength - 1; i >= 0; i--) {
				if (sn.length() == 0) {
					sn = arr[i];
				} else if (sn.length() < 8) {
					sn = arr[i] + KEY_SEPARATOR + sn;
				} else {
					break;
				}
			}
			sn = sn.trim();
		}
		return sn;
	}

	/**
	 * 解析视频名字，它不包含文件名中的无效信息、明确表示视频内容
	 * @param videoFileName
	 * @return
	 */
	public static String getVideoPureName(String videoFileName) {
		String title = videoFileName.replace(VIDEO_SUFFIX, "");
		title = title.replace(parseVideoSN(videoFileName), "");
		title = title.substring(0, title.length()-1);

		String separator = "_";
		if (title.contains(separator)) {
			String []arr = title.split(separator);
			title = arr[0];
		}
		return title.trim();
	}
	/**
	 * 获取视频发布的标题
	 * @param videoPureName
	 * @return
	 */
	public static String getPostTitle(String videoPureName, int siteId) {
		String postTitle;
		int endIndex;
		switch (siteId) {
			case XIGUA:
				endIndex = min(videoPureName.length(), 50);
				postTitle = videoPureName.substring(0, endIndex).replace("'", "");
				break;
			case YIDIAN:
				endIndex = min(videoPureName.length(), 50);
				postTitle = videoPureName.substring(0, endIndex).replace("#", "-");
				break;
			case DAYU:
				endIndex = min(videoPureName.length(), 90);
				postTitle = videoPureName.substring(0, endIndex);
				break;
			default:
				postTitle = videoPureName;
		}
		postTitle = "英文儿歌｜" + postTitle.trim();
		log.info(postTitle);
		return postTitle;
	}

	/**
	 * 下载指定资源到目标文件
	 *
	 * @param url
	 * @param destFile
	 * @return
	 * @throws Exception
	 */
	public static boolean downloadFile(String url, String destFile) {
		try {
			InputStream in = new URL(url).openStream();
			Files.copy(in, Paths.get(destFile));
		} catch (FileAlreadyExistsException e) {
			log.info("file existed: " + destFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String videoName;
		videoName = "BINGO Song Sing-along _ Nursery Rhyme _ #readalong with Super Simple Songs-yNnfd0wMEso.mp4";
		System.out.println(parseVideoSN(videoName));
		System.out.println(getVideoPureName(videoName));
		videoName = "Welcome to Super Simple Songs!-FMj4fyy9xrc.mp4";
		System.out.println(getVideoPureName(videoName));
		System.out.println(parseVideoSN(videoName));
		videoName = "Cartoon _ Hello Baby Sparrows _ Treetop Family Ep.1 _ Cartoon for kids-e6Vz_x-y-IA.mp4";
		System.out.println(parseVideoSN(videoName));
		System.out.println(getVideoPureName(videoName));
		videoName = "Decorate The Christmas Tree (to the tune of Deck The Halls) _ Super Simple Songs-ZpJCgTx_auc.mp4";
		System.out.println(getVideoPureName(videoName).length());
		System.out.println(getVideoPureName(videoName).substring(0, 30));
	}

}
