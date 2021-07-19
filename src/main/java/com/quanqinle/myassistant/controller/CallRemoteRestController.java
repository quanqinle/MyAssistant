package com.quanqinle.myassistant.controller;

import com.quanqinle.myassistant.entity.po.TaxRate;
import com.quanqinle.myassistant.entity.po.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 调用其他应用提供的远程api
 *
 * @author quanql
 */
@RestController
@RequestMapping("/remote")
@Api(value = "CallRemoteRestController", tags = {"调用远程API的示例"})
public class CallRemoteRestController {

	@Value(value = "${api.remote}")
	String base;

	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	@GetMapping("/getone/{income}")
	public TaxRate testGetTaxRate(@PathVariable float income) {
		RestTemplate client = restTemplateBuilder.build();
		String uri = base + "/income/{income}";
		TaxRate rate = client.getForObject(uri, TaxRate.class, income);
//		ResponseEntity<TaxRate> entity = client.getForEntity(uri,TaxRate.class, income);
		return rate;
	}

	@PostMapping("/getall?offset={offset}")
	public List<TaxRate> testGetTaxRates(@PathVariable long offset) {
		RestTemplate client = restTemplateBuilder.build();
		String uri = base + "/list?offset={offset}";

		HttpEntity body = null;
		ParameterizedTypeReference<List<TaxRate>> typeRef = new ParameterizedTypeReference<List<TaxRate>>() {};
		ResponseEntity<List<TaxRate>> rs = client.exchange(uri, HttpMethod.GET, body, typeRef, offset);
		List<TaxRate> rates = rs.getBody();
		return rates;
	}

	@GetMapping("/test")
	public String testPost() {
		RestTemplate client = restTemplateBuilder.build();
		String uri = "http://xxx.cn/user/login/password";
		User user = new User();
		user.setName("qlquan");
		user.setPassword("123456789");
		HttpEntity<User> body = new HttpEntity<>(user);
		String ret = client.postForObject(uri, body, String.class);
		return ret;
	}

}
