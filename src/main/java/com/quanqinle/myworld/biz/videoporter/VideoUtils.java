package com.quanqinle.myworld.biz.videoporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author quanql
 */
public class VideoUtils {
	static Log log = LogFactory.getLog(VideoUtils.class);
	public static String pathStr = "D:\\tmp\\video-youtube\\changed\\";

	/**
	 * site id
	 */
	public static int YOUTUBE = 1;
	public static int XIGUA = 2;
	public static int YIDIAN = 3;
	public static int DAYU = 4;

	/**
	 * val in video name
	 */
	static String KEY_SLOGAN = "Super Simple Songs";
	static String KEY_SEPARATOR = "-";
	static String VIDEO_SUFFIX = ".mp4";

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
	 * 解析视频名字，它不包含文件名中的无效内容、明确表示视频内容
	 * @param videoFileName
	 * @return
	 */
	public static String getVideoPureName(String videoFileName) {
		String KEY = "_";
		// "英文儿歌｜"。移出拼接逻辑，方便该函数复用
		String title = "";
		if (videoFileName.contains(KEY)) {
			String []arr = videoFileName.split(KEY);
			title = title + arr[0];
		} else {
			log.error("视频名称不含有分隔符_ ：" + videoFileName);
			title += videoFileName.replace(VIDEO_SUFFIX, "");
		}
		return title.trim();
	}

	/**
	 * 获取视频发布的标题
	 * @param videoPureName
	 * @return
	 */
	public static String getPostTitle(String videoPureName) {
		return "英文儿歌｜" + videoPureName;
	}
	/**
	 * 获取视频发布的描述
	 * @param videoPureName 视频名称
	 * @return
	 */
	public static String getPostContent(String videoPureName) {
		String s = String.format("# %s \n%s \n%s \n%s \n%s \n%s \n%s"
				, videoPureName
				, "# Kids Songs"
				, "# Super Simple Songs"
				, "# 来源：http://油管/user/SuperSimpleSongs 感谢原创，请subscribe她"
				, "# 听儿歌，学英语"
				, "# 节奏轻快，孩子爱听"
				, "# 喜欢就关注我哦~");
		return s;
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
		String videoName = "";
		videoName = "BINGO Song Sing-along _ Nursery Rhyme _ #readalong with Super Simple Songs-yNnfd0wMEso.mp4";
		System.out.println(parseVideoSN(videoName));
		videoName = "Welcome to Super Simple Songs!-FMj4fyy9xrc.mp4";
		System.out.println(parseVideoSN(videoName));
		videoName = "Cartoon _ Hello Baby Sparrows _ Treetop Family Ep.1 _ Cartoon for kids-e6Vz_x-y-IA.mp4";
		System.out.println(parseVideoSN(videoName));
	}

}
