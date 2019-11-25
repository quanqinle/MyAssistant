package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.conf.AuthorConfigs;
import com.quanqinle.mysecretary.entity.vo.ResultVo;
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
	public ResultVo<String> index() {
		/* 已在 MvcConfigurer 中配置*/
		String data = String.format("{\"author\":%s, \"email\":%s}", authorConfigs.getName(), authorConfigs.getEmail());
		return ResultVo.success(null, data, String.class);
	}
}
