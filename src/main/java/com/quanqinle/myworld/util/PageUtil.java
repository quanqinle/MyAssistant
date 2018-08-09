package com.quanqinle.myworld.util;

public class PageUtil {
	/**
	 * 获取分页起始位置
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	public static int getOffset(int page, int size) {
		int offset = (page - 1) * size;
		if (offset < 0) {
			offset = 0;
		}
		return offset;
	}
}
