package com.quanqinle.myworld.controller;

import com.quanqinle.myworld.entity.TaxRate;
import com.quanqinle.myworld.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/list")
    @ResponseBody
    public List<TaxRate> allRate() {
        return taxRateService.getAllTaxRate();
    }

    @GetMapping("/income/{income}")
    @ResponseBody
    public TaxRate getRateByIncome(@PathVariable float income) {
        return taxRateService.getTaxRateByRange(income);
    }
}
