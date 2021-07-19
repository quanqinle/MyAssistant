package com.quanqinle.myassistant.controller;

import com.quanqinle.myassistant.conf.AuthorConfigs;
import com.quanqinle.myassistant.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author quanqinle
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private AuthorConfigs authorConfigs;

	@GetMapping({"/about.html", "/about.json"})
	@ResponseBody
	public Result<String> index() {
		/* 已在 MvcConfigurer 中配置*/
		String data = String.format("{\"author\":%s, \"email\":%s}", authorConfigs.getName(), authorConfigs.getEmail());
		return Result.success(data);
	}
}
