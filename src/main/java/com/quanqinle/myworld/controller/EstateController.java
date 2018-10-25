package com.quanqinle.myworld.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanqinle.myworld.entity.po.EstateCommunity;
import com.quanqinle.myworld.entity.po.EstateSecondHandListing;
import com.quanqinle.myworld.entity.po.EstateSecondHandResp;
import com.quanqinle.myworld.entity.vo.ResultVo;
import com.quanqinle.myworld.service.EstateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author quanql
 */
@RestController
@RequestMapping("/estate")
public class EstateController {
	Log log = LogFactory.getLog(EstateController.class);

	@Value(value = "http://jjhygl.hzfc.gov.cn")
	String remoteBase;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@Autowired
	EstateService estateService;

	@GetMapping("/community/saveall3")
	public ResultVo<String> saveAll3() {
		RestTemplate restClient = restTemplateBuilder.build();
		String uri = remoteBase + "/upload/webty/index_search_communitylist.js";

		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptCharset(new ArrayList(Arrays.asList("gzip, deflate")));
		headers.setHost(new InetSocketAddress(remoteBase, 80));
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
		String requestBody = null;
		HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, headers);

		ResponseEntity<String> respEntity = restClient.exchange(remoteBase, HttpMethod.GET, requestEntity, String.class);
		log.info(respEntity.getStatusCodeValue());
		log.info(respEntity.getBody());
		return new ResultVo(200, "end");
	}

	@GetMapping("/community/saveall")
	public ResultVo<List<EstateCommunity>> saveAll() throws Exception{
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

	@GetMapping("/secondhand/saveall")
	public ResultVo<String> saveAllSecondHandInfo() throws Exception{
		RestTemplate restClient = restTemplateBuilder.build();
//		String uri = remoteBase + "/webty/WebFyAction_getGpxxSelectList.jspx";
		String uri = remoteBase + "/webty/WxAction_getGpxxSelectList.jspx?page=";
		ResponseEntity<String> respEntity = restClient.getForEntity(uri, String.class);

		log.info(respEntity.getStatusCodeValue());
		log.info(respEntity.toString());

		String jsonStr = respEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();

		EstateSecondHandResp resp = mapper.readValue(jsonStr, EstateSecondHandResp.class);
		boolean isOver = resp.isIsover();
		List<EstateSecondHandListing> nodeList = resp.getList();
		log.info("quanql --> " + nodeList);
//		JavaType type = mapper.getTypeFactory().constructParametricType(List.class, EstateSecondHandListing.class);
		for (EstateSecondHandListing node: nodeList) {
//			EstateSecondHandListing listings = mapper.readValue(node, EstateSecondHandListing.class);
			estateService.saveSecondHandListing(node);
		}

		return new ResultVo(200, "success");
	}

//	private void writeDataToFile(String jsonStr) throws IOException {
//		//文件目录
//		Path rootLocation = Paths.get("folder")
//		if(Files.notExists(rootLocation)){
//			Files.createDirectories(rootLocation);
//		}
//		//data.js是文件
//		Path path = rootLocation.resolve("data.js");
//		byte[] strToBytes = jsonStr.getBytes();
//		Files.write(path, strToBytes);
//	}
}
