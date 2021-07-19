package com.quanqinle.myassistant.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanqinle.myassistant.entity.Result;
import com.quanqinle.myassistant.entity.po.EstateCommunity;
import com.quanqinle.myassistant.entity.po.EstateSecondHandHouse;
import com.quanqinle.myassistant.entity.po.EstateSecondHandPrice;
import com.quanqinle.myassistant.service.EstateService;
import com.quanqinle.myassistant.service.ScheduledTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 * @author quanql
 */
@Controller
@RequestMapping("/estate")
@Api(value = "EstateController", tags = {"房地产"})
public class EstateController {
    private Logger log = LoggerFactory.getLogger(EstateController.class);

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
     *
     * @return
     */
    @GetMapping("/community/savealldemo")
    @ApiOperation(value = "demo", notes = "演示RestTemplate用法")
    public Result<String> saveDemo() {
        RestTemplate restClient = restTemplateBuilder.build();
        String uri = remoteBase + "/upload/webty/index_search_communitylist.js";

        /**
         * 如果用下面这句，有时会报 403 Forbidden
         * ResponseEntity<String> respEntity = restClient.getForEntity(uri, String.class)
         *
         * 增加"User-Agent"，可以解决问题。
         */

        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(new ArrayList(Arrays.asList("gzip, deflate")));
        headers.setHost(new InetSocketAddress(remoteBase, 80));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> respEntity = restClient.exchange(uri, HttpMethod.GET, requestEntity, String.class);

        log.info("resp code: {}", respEntity.getStatusCodeValue());
        log.info("resp body: {}", respEntity.getBody());
        return new Result(200, "end");
    }

    /**
     * 从ZF网站抓取所有杭州社区信息，存入DB
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/community/saveCommunities")
    @ApiOperation(value = "抓取社区信息to DB", notes = "从ZF网站抓取所有杭州社区信息，存入DB（注：这里可以写更详细的api信息）")
    public Result<List<EstateCommunity>> saveCommunitiesToDb() throws Exception {
        RestTemplate restClient = restTemplateBuilder.build();
        String uri = remoteBase + "/upload/webty/index_search_communitylist.js";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> respEntity = restClient.exchange(uri, HttpMethod.GET, requestEntity, String.class);

        log.info("resp code: {}", respEntity.getStatusCodeValue());
        log.info("resp body: {}", respEntity.getBody());
        log.info("resp all: {}", respEntity.toString());

        String body = respEntity.getBody();
        String pre = "var data_communitylist = ";
        String suf = ";";
        String jsonStr = body.substring((body.indexOf(pre) + pre.length()), body.indexOf(suf));

        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, EstateCommunity.class);
        List<EstateCommunity> communities = mapper.readValue(jsonStr, type);
        return Result.success(estateService.saveCommunities(communities));
    }

    /**
     * 从ZF官网抓取最新二手房信息，存入DB
     *
     * @return
     */
    @GetMapping("/secondhand/saveNewestListings")
    @ApiOperation(value = "抓取最新二手房信息to DB")
    public Result<String> saveAllListingsInfoToDb() {
        long count = scheduledTaskService.crawlNewSecondHandRespToDb();
        return new Result(200, "success", "更新 " + count + " 条");
    }

    @GetMapping("/secondhand/house/{houseUniqueId}")
    @ApiOperation(value = "获取二手房基本信息")
    public Result<EstateSecondHandHouse> getSecondHandHouseInfo(@PathVariable String houseUniqueId) {
        return Result.success(estateService.getSecondHandHouse(houseUniqueId));
    }

    @GetMapping("/secondhand/price/{houseUniqueId}")
    @ApiOperation(value = "获取二手房价格信息")
    public Result<EstateSecondHandPrice> getSecondHandPriceInfo(@PathVariable String houseUniqueId) {
        return Result.success(estateService.getSecondHandPrice(houseUniqueId).get(0));
    }

    /**
     * 分析抓取的数据，同步到对应table
     *
     * @return
     */
    @GetMapping("/secondhand/sync")
    @ApiOperation(value = "同步二手房信息到其他表")
    public WebAsyncTask<Result> syncListingsToOtherTables() {

        // tip: lambda expression
        Callable<Result> callable = () -> {
            scheduledTaskService.syncLatestListingsToOtherTables();
            return Result.success();
        };

        // 开启一个异步任务
        WebAsyncTask<Result> asyncTask = new WebAsyncTask<>(8 * 60 * 60 * 1000, callable);
        log.info("asyncTask timeout:" + asyncTask.getTimeout());
        // 任务执行完成时调用该方法
        asyncTask.onCompletion(() -> log.info("任务执行完成"));
        asyncTask.onError(() -> {
            log.info("任务执行异常");
            return new Result(400, "failed");
        });
        asyncTask.onTimeout(() -> new Result(400, "async task timeout!"));

        return asyncTask;
    }

}
