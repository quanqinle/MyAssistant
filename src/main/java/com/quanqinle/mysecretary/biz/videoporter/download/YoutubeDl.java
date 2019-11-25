package com.quanqinle.mysecretary.biz.videoporter.download;

import java.util.StringJoiner;

/**
 * 从youtube下载视频
 * @author quanql
 */
public class YoutubeDl {

	public YoutubeDl() {
	}

	public void download(String url) {
		String delimiter = " ";
		String prefix = "youtube-dl";
		String suffix = "\"" + url + "\"";

		StringJoiner cmdline = new StringJoiner(delimiter, prefix, suffix);
		cmdline.add("--proxy \"socks5://127.0.0.1:1080\"");
		cmdline.add("--download-archive archive.txt");
		cmdline.add("--embed-subs --write-sub --sub-format \"ass/srt/best\" --convert-subs \"srt\" ");
//		cmdline.add("--playlist-start 20");

		// https://www.cnblogs.com/bencakes/p/6139477.html
	}
}
