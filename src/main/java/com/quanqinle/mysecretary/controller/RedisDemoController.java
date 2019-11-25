package com.quanqinle.mysecretary.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author quanqinle
 */
@Controller
@RequestMapping("/redis")
@Api(value = "RedisDemoController", description = "Redis的示例")
public class RedisDemoController {

	@Autowired
	private StringRedisTemplate redisClient;

	@RequestMapping("/setget/{value}")
	public @ResponseBody String env(@PathVariable(name = "value") String para) {
		redisClient.opsForValue().set("testenv", para);
		String str = redisClient.opsForValue().get("testenv");
		return str;
	}

	@RequestMapping("/list/{para}")
	public @ResponseBody String list(@PathVariable String para) {
		BoundListOperations operations = redisClient.boundListOps("listkey");
		operations.leftPush(para);
		Long len = operations.size();
		String str = (String) operations.rightPop();
		return "size: " + len + " value: " + str;
	}

	/**
	 * 订阅频道，发送消息
	 * @param msg
	 * @return
	 */
	@RequestMapping("/pub/{msg}")
	public @ResponseBody String pubChannel(@PathVariable String msg) {
		redisClient.convertAndSend("news.test", msg);
		return "success";
	}
}
