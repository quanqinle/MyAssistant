package com.quanqinle.myworld.controller;

import com.quanqinle.myworld.entity.po.TaxRate;
import com.quanqinle.myworld.entity.vo.TaxPlan;
import com.quanqinle.myworld.service.TaxRateService;
import com.quanqinle.myworld.util.TaxPlanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Controller
@RequestMapping("/tax")
public class TaxRateController {
	Log log = LogFactory.getLog(TaxRateController.class);

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
		return "/pages/ratelist"; //properties中设置了缺省.ftl，所以跳转ratelist.ftl
	}

	/**
	 * 个税计算页面
	 * @return
	 */
	@GetMapping("/calc")
	public String calcTaxRate(Model model) {
		// FIXME 据说：“在渲染页面之前，我们通过model.addAttribute("helloMessage", new HelloMessage());告诉页面绑定到一个空的HelloMessage对象，这样sayHello.html页面初始时就会显示一个空白的表单。”
		// 实测无效，还是显示上次提交的结果
		model.addAttribute("taxrate", new TaxRate());
		return "/pages/ratecalc";
	}

	/**
	 * 提交个税查询
	 * @param income
	 * @param model
	 * @return
	 */
	@PostMapping("/calc")
	public String calcResult(double income, Model model) {
		double taxableSalary = TaxPlanUtils.calcTaxableSalary(income);
		double tax = TaxPlanUtils.calcTaxes(taxableSalary);
		TaxRate taxRate = TaxPlanUtils.getTaxRate(taxableSalary);
		model.addAttribute("income", income);
		model.addAttribute("taxes", tax);
		model.addAttribute("taxrate", taxRate);
		return "/page/ratecalc";
	}

	@GetMapping("/plan")
	public String planForm() {
		return "/pages/ratecalc";
	}

	@PostMapping("/plan")
	public String planSubmit(double estimatedAnnualSalary, double alreadyPaidSalary,
	                         int remainingMonths, Model model){
		TaxPlan taxplan = TaxPlanUtils.calcBestTaxPlanQuickly(estimatedAnnualSalary, alreadyPaidSalary, remainingMonths);
		model.addAttribute("taxplan", taxplan);
		return "/pages/ratecalc";
	}

	@GetMapping("/income/{income}")
	@ResponseBody
	public TaxRate getRateByIncome(@PathVariable double income) {
		double taxableSalary = TaxPlanUtils.calcTaxableSalary(income);
		return taxRateService.getTaxRateByRange(taxableSalary);
	}

	@GetMapping("/env")
	@ResponseBody
	public String getEnv(@Value("${user.dir:false}") String userDir) {
		return "user.dir = " + userDir;
	}
}
