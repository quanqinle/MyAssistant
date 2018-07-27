package com.quanqinle.myworld.controller;

import com.quanqinle.myworld.entity.po.TaxRate;
import com.quanqinle.myworld.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tax")
public class TaxRateController {

	@Autowired
	TaxRateService taxRateService;

	@GetMapping("/list.json")
	@ResponseBody
	public List<TaxRate> allRate() {
		return taxRateService.getAllTaxRate();
	}

	@GetMapping("/list.html")
	public String allRate(Model model) {
		model.addAttribute("ratelist", taxRateService.getAllTaxRate());
		return "/ratelist"; //properties中设置了.ftl，所以跳转ratelist.ftl
	}

	@GetMapping("/income/{income}")
	@ResponseBody
	public TaxRate getRateByIncome(@PathVariable float income) {
		return taxRateService.getTaxRateByRange(income);
	}

	@GetMapping("/env")
	@ResponseBody
	public String getEnv(@Value("${user.dir:false}") String userDir) {
		return "user.dir = " + userDir;
	}
}
