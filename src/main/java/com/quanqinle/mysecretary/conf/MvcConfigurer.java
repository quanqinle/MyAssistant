package com.quanqinle.mysecretary.conf;

import com.quanqinle.mysecretary.entity.po.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局化定制Spring Boot的MVC特性
 * @author quanql
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	/**
	 * 拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 检查用户是否登录
		registry.addInterceptor(new SessionHandlerInterceptor()).addPathPatterns("/admin/**");

		// 打印处理用户请求的耗时

	}

	/**
	 * 跨域访问配置
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {

	}

	/**
	 * URI到视图的映射
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//url不要加后缀ftl
		registry.addViewController("/index.html").setViewName("/index-default");
		registry.addRedirectViewController("/", "/index.html");
		registry.addRedirectViewController("/index.*", "/index.html");
		registry.addRedirectViewController("/**/*.do", "/index.html");
	}

	// 还有其他接口



	/**
	 *
	 * 检查用户是否已经登录，如果未登录，重定向到登录页面
	 *
	 */
	class SessionHandlerInterceptor implements HandlerInterceptor {
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null){
				response.sendRedirect("/login.html");
				return false;
			}
			return true;
		}
	}
}
