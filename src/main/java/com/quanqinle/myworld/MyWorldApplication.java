package com.quanqinle.myworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author quanqinle
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class MyWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWorldApplication.class, args);
	}
}
