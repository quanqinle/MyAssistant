package com.quanqinle.myworld.controller;

import com.quanqinle.myworld.conf.AuthorConfigs;
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
	public String index() {
		/* 已在 MvcConfigurer 中配置*/
		return String.format("{\"author\":%s, \"email\":%s}", authorConfigs.getName(), authorConfigs.getEmail());
	}
}
