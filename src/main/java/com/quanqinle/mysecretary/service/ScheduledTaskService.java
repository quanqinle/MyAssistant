package com.quanqinle.mysecretary.service;

/**
 * 定时任务
 * @author quanqinle
 */
public interface ScheduledTaskService {

	/**
	 * 从ZF官网抓取最新二手房信息
	 * @return -
	 */
	long crawlNewSecondHandRespToDb();

	/**
	 * 将最新的二手房挂牌信息表中的内容同步到其他表。
	 * 实现方式：依赖DB原生的insert+select。效率比2高几个数量级
	 */
	void syncLatestListingsToOtherTables();
	/**
	 * 将最新的二手房挂牌信息表中的内容同步到其他表
	 * 实现方式：使用jpa的save()逐条保存记录
	 */
	void syncLatestListingsToOtherTables2();
}
