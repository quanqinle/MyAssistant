package com.quanqinle.mysecretary.controller;

import com.quanqinle.mysecretary.entity.po.TaxRate;
import com.quanqinle.mysecretary.entity.vo.ResultVo;
import com.quanqinle.mysecretary.entity.vo.TaxPlan;
import com.quanqinle.mysecretary.service.TaxRateService;
import com.quanqinle.mysecretary.util.TaxPlanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author quanqinle
 */
@Controller
@RequestMapping("/tax")
@Api(value = "TaxRateController", description = "个人所得税")
public class TaxRateController {
    private Logger log = LoggerFactory.getLogger(TaxRateController.class);

	@Autowired
	TaxRateService taxRateService;

	@GetMapping("/list.html")
	public String allRate(Model model) {
		model.addAttribute("ratelist", taxRateService.getTaxRateTable());
		//properties中设置了缺省.ftl，所以跳转ratelist.ftl
		return "/pages/ratelist";
	}

	/**
	 * 个税计算页面
	 *
	 * @return
	 */
	@GetMapping(value = {"/calc", "/plan"})
	public String showTaxCalcPage(Model model) {
		// FIXME 据说：“在渲染页面之前，我们通过model.addAttribute("helloMessage", new HelloMessage());告诉页面绑定到一个空的HelloMessage对象，这样sayHello.html页面初始时就会显示一个空白的表单。”
		// 实测无效，还是显示上次提交的结果
		model.addAttribute("taxrate", new TaxRate());
		return "/pages/ratecalc";
	}

	@GetMapping("/list.json")
	@ResponseBody
	@ApiOperation(value = "获取个税税率表")
	public ResultVo<List<TaxRate>> allRate() {
		List<TaxRate> list =  taxRateService.getTaxRateTable();
		return new ResultVo(200, list);
	}

	/**
	 * 提交个税查询
	 *
	 * @param income
	 * @return
	 */
	@GetMapping("/calc_tax")
	@ResponseBody
	public HashMap<String, Object> calcResult(@RequestParam(name = "income") double income) {
		double taxableSalary = TaxPlanUtils.calcTaxableSalary(income);
		double tax = TaxPlanUtils.calcTaxes(taxableSalary);
		TaxRate taxRate = TaxPlanUtils.getTaxRate(taxableSalary);

		HashMap<String, Object> result = new HashMap<>(16);
		result.put("taxes", tax);
		result.put("taxrate", taxRate);

		return result;
	}

	@PostMapping("/opt_plan")
	@ResponseBody
	public HashMap<String, Object> planSubmit(Double estimatedAnnualSalary, Double alreadyPaidSalary, Integer remainingMonths) {
		TaxPlan taxplan = TaxPlanUtils.calcBestTaxPlanQuickly(estimatedAnnualSalary, alreadyPaidSalary, remainingMonths);
		HashMap<String, Object> result = new HashMap<>(16);
		result.put("taxplan", taxplan);

		return result;
	}

	@GetMapping("/income/{income}")
	@ResponseBody
	@ApiOperation(value = "查询收入对应的税率")
	public TaxRate getRateByIncome(@PathVariable double income) {
		double taxableSalary = TaxPlanUtils.calcTaxableSalary(income);
		return taxRateService.getTaxRateByRange(taxableSalary);
	}

	/**
	 * just demo for the usage of environment parameter
	 * @param userDir
	 * @return
	 */
	@GetMapping("/env")
	@ResponseBody
	public String getEnv(@Value("${user.dir:false}") String userDir) {
		return "user.dir = " + userDir;
	}
}
