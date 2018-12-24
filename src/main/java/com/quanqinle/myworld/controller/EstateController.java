package com.quanqinle.myworld.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandHouse;
import com.quanqinle.myworld.entity.po.EstateSecondHandPrice;
import com.quanqinle.myworld.entity.vo.ResultVo;
import com.quanqinle.myworld.service.EstateService;
import com.quanqinle.myworld.service.ScheduledTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 房地产
 * @author quanql
 */
@Controller
@RequestMapping("/estate")
@Api(value = "EstateController", description = "房地产")
public class EstateController {
	Log log = LogFactory.getLog(EstateController.class);

	@Value(value = "http://jjhygl.hzfc.gov.cn")
	String remoteBase;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	EstateService estateService;

	@Autowired
	ScheduledTaskService scheduledTaskService;

	/**
	 * just demo for the usage of restClient
	 * @return
	 */
//	@GetMapping("/community/saveall1")
	private ResultVo<String> saveAll1() {
		RestTemplate restClient = restTemplateBuilder.build();
		String uri = remoteBase + "/upload/webty/index_search_communitylist.js";

		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptCharset(new ArrayList(Arrays.asList("gzip, deflate")));
		headers.setHost(new InetSocketAddress(remoteBase, 80));
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		String requestBody = null;
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> respEntity = restClient.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		log.info(respEntity.getStatusCodeValue());
		log.info(respEntity.getBody());
		return new ResultVo(200, "end");
	}

	/**
	 * 从ZF网站抓取所有杭州社区信息，存入DB
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/community/saveall")
	@ApiOperation(value = "抓取社区信息to DB", notes = "从ZF网站抓取所有杭州社区信息，存入DB（注：这里可以写更详细的api信息）")
	public ResultVo<List<EstateCommunity>> saveCommunitiesToDB() throws Exception{
		RestTemplate restClient = restTemplateBuilder.build();
		String uri = remoteBase + "/upload/webty/index_search_communitylist.js";
		ResponseEntity<String> respEntity = restClient.getForEntity(uri, String.class);

		log.debug(respEntity.getStatusCodeValue());
		log.debug(respEntity.toString());

		String body = respEntity.getBody();
		String pre = "var data_communitylist = ";
		String suf = ";";
		String jsonStr = body.substring((body.indexOf(pre)+pre.length()), body.indexOf(suf));

		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructParametricType(List.class, EstateCommunity.class);
		List<EstateCommunity> communities = mapper.readValue(jsonStr, type);
		return new ResultVo(200, estateService.saveCommunities(communities));
	}

	/**
	 * 从ZF官网抓取最新二手房信息，存入DB
	 * @return
	 */
	@GetMapping("/secondhand/savenew")
	@ApiOperation(value = "抓取最新二手房信息to DB")
	public ResultVo<String> saveAllListingsInfoToDB(){
		long count = scheduledTaskService.crawlNewSecondHandRespToDB();
		return new ResultVo(200, "success", "更新 " + count + " 条");
	}

	@GetMapping("/secondhand/house/{houseUniqueId}")
	@ApiOperation(value = "获取二手房基本信息")
	public ResultVo<EstateSecondHandHouse> getSecondHandHouseInfo(@PathVariable String houseUniqueId) {
		return new ResultVo(200, estateService.getSecondHandHouse(houseUniqueId));
	}

	@GetMapping("/secondhand/price/{houseUniqueId}")
	@ApiOperation(value = "获取二手房价格信息")
	public ResultVo<EstateSecondHandPrice> getSecondHandPriceInfo(@PathVariable String houseUniqueId) {
		return new ResultVo(200, estateService.getSecondHandPrice(houseUniqueId));
	}
	/**
	 * 分析抓取的数据，同步到对应table
	 * @return
	 */
	@GetMapping("/secondhand/sync")
	@ApiOperation(value = "同步二手房信息到其他表")
	public WebAsyncTask<ResultVo> syncListingsToOtherTables(){

		// tip: lambda expression
		Callable<ResultVo> callable = () -> {
			scheduledTaskService.syncLatestListingsToOtherTables();
			return new ResultVo(200, "success");
		};

		// 开启一个异步任务
		WebAsyncTask<ResultVo> asyncTask = new WebAsyncTask<>(8*60*60*1000, callable);
		log.info("asyncTask timeout:" + asyncTask.getTimeout());
		// 任务执行完成时调用该方法
		asyncTask.onCompletion(() -> log.info("任务执行完成"));
		asyncTask.onError(() -> {
			log.info("任务执行异常");
			return new ResultVo(400, "failed");
		});
		asyncTask.onTimeout(() -> new ResultVo(400, "async task timeout!"));

		return asyncTask;
	}

}
