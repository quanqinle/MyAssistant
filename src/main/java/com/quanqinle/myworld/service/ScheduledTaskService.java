package com.quanqinle.myworld.service;

/**
 * 定时任务
 * @author quanqinle
 */
public interface ScheduledTaskService {
	/**
	 * 从ZF官网抓取最新二手房信息
	 */
	long crawlNewSecondHandRespToDB();

	/**
	 * 将最新的二手房挂牌信息表中的内容同步到其他表
	 */
	void syncLatestListingsToOtherTables();
}
