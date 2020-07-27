package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.entity.po.SysDict;
import com.quanqinle.mysecretary.entity.vo.ResultVo;
import com.quanqinle.mysecretary.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统词典
 * @author quanql
 */
@Controller
@RequestMapping("/sysdict")
@Api(value = "SysDictController", description = "系统词典")
public class SysDictController {
    private Logger log = LoggerFactory.getLogger(SysDictController.class);

	private SysDictService sysDictService;

	@Autowired
	public SysDictController(SysDictService sysDictService) {
		this.sysDictService = sysDictService;
	}

	@GetMapping("/getall")
	@ResponseBody
	@ApiOperation(value = "获取所有系统字典")
	public ResultVo<List<SysDict>> getAll() {
		List<SysDict> dicts = sysDictService.getAll();
		return new ResultVo<>(200, "ok", dicts);
	}

	@GetMapping("/get/{key}")
	@ResponseBody
	@ApiOperation(value = "获取系统字典")
	public ResultVo<SysDict> getDict(@PathVariable String key) {
		SysDict dict = sysDictService.getSysDict(key.toLowerCase());
		return new ResultVo<>(200, "ok", dict);
	}

	@PostMapping("/save")
	@ResponseBody
	@ApiOperation(value = "更新字典信息")
	public ResultVo<String> addOrUpdateCookie(String key, String value) {
		if (key.isEmpty()) {
			return new ResultVo<>(200, "fail", "key is null");
		}

		return new ResultVo<>(200, "ok", sysDictService.saveSysDict(key, value).toString());
	}
}
