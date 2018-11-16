package com.quanqinle.myworld.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import com.quanqinle.myworld.entity.vo.EstateSecondHandResp;
import com.quanqinle.myworld.service.EstateService;
import com.quanqinle.myworld.service.ScheduledTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 * @author quanqinle
 */
@Component
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

	private Log log = LogFactory.getLog(ScheduledTaskService.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Value(value = "http://jjhygl.hzfc.gov.cn")
	String remoteBase;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	EstateService estateService;

	/**
	 * 每日凌晨自动从ZF官网获取最新二手房信息
	 * @return 更新记录条数
	 */
	@Scheduled(cron = "0 0 0 * * * ")
	@Override
	public long crawlNewSecondHandRespToDB() {
		log.info("定时任务crawlNewSecondHandRespToDB()启动：" + dateFormat.format(new Date()));
		long time = System.currentTimeMillis();
		long count = 0;

		//设置极值，避免while无限循环
		int idxEnd = 10000;
		boolean bEnd = false;

		EstateSecondHandListing latestListing = estateService.getLatestOne();
		String listingTime = latestListing.getScgpshsj();
		String uniqueHouseId = latestListing.getFwtybh();

		RestTemplate restClient = restTemplateBuilder.build();
		String uri = remoteBase + "/webty/WxAction_getGpxxSelectList.jspx?page=";

		for (int i = 0; i < idxEnd ; i++) {
			try {
				log.info(uri + i);
				ResponseEntity<String> respEntity = restClient.getForEntity(uri + i, String.class);
				log.info(respEntity.getStatusCodeValue());

				String jsonStr = respEntity.getBody();
				log.info(jsonStr);

				ObjectMapper mapper = new ObjectMapper();
				EstateSecondHandResp resp = mapper.readValue(jsonStr, EstateSecondHandResp.class);
				boolean isOver = resp.isIsover();
				List<EstateSecondHandListing> nodeList = resp.getList();
				log.info("isOver = " + isOver);

				// 如果一条失败，整个saveall都会丢失。还是一条条存更保险
				// estateService.saveSecondHandListings(nodeList);

				for (EstateSecondHandListing node: nodeList) {
					if (uniqueHouseId.equals(node.getFwtybh()) || listingTime.compareTo(node.getScgpshsj()) > 0) {
						bEnd = true;
					}
					EstateSecondHandListing listing = estateService.getSecondHandListingByGpid(node.getGpid());
					if (listing != null) {
						node.setId(listing.getId());
						if (node.equals(listing)) {
							continue;
						}
						log.info("update listing:" + node);
					} else {
						log.info("save listing:" + node);
					}
					estateService.saveSecondHandListing(node);
					count ++;
				}

				if (isOver || bEnd) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end for

		log.info("定时任务crawlNewSecondHandRespToDB()结束：" + dateFormat.format(new Date()));
		log.info("定时任务crawlNewSecondHandRespToDB()耗时：" + (System.currentTimeMillis() - time));
		log.info("更新条数：" + count);
		return count;
	}

	@Override
	public void syncLatestListingsToOtherTables() {
		log.info("定时任务syncLatestListingsToOtherTables()启动：" + dateFormat.format(new Date()));
		long time = System.currentTimeMillis();

		List<EstateSecondHandListing> all1 = estateService.getAllNotInHouseTable();
		log.info("待同步house表数据条数：" + all1.size());
		estateService.insertHouseTblFromListing();

		List<EstateSecondHandListing> all2 = estateService.getAllNotInPriceTable();
		log.info("待同步price表数据条数：" + all2.size());
		estateService.insertPriceTblFromListing();

		log.info("定时任务syncLatestListingsToOtherTables()结束：" + dateFormat.format(new Date()));
		log.info("定时任务syncLatestListingsToOtherTables()耗时：" + (System.currentTimeMillis() - time));
	}

	@Override
	public void syncLatestListingsToOtherTables_2() {
		log.info("定时任务syncLatestListingsToOtherTables()启动：" + dateFormat.format(new Date()));
		long time = System.currentTimeMillis();

		List<EstateSecondHandListing> all1 = estateService.getAllNotInHouseTable();
		log.info("待同步house表数据条数：" + all1.size());
		for (EstateSecondHandListing one: all1) {
			estateService.syncListingToHouseTable(one);
		}

		List<EstateSecondHandListing> all2 = estateService.getAllNotInPriceTable();
		log.info("待同步price表数据条数：" + all2.size());
		for (EstateSecondHandListing one: all2) {
			estateService.syncListingToPriceTable(one);
		}

		log.info("定时任务syncLatestListingsToOtherTables()结束：" + dateFormat.format(new Date()));
		log.info("定时任务syncLatestListingsToOtherTables()耗时：" + (System.currentTimeMillis() - time));
	}
}
