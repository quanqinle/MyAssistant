package com.quanqinle.mysecretary.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对应application.properties中前缀是author的配置
 * @author quanql
 */
@Component
@ConfigurationProperties(prefix = "author")
public class AuthorConfigs {
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
