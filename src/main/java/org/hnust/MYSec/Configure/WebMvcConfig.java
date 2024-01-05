package org.hnust.MYSec.Configure;

import org.hnust.MYSec.Service.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${spring.mvc.interceptor.exclude-paths}")
	private String[] excludePaths;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册拦截器，并指定拦截的路径
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/api/**")
				.excludePathPatterns(excludePaths);


		// 可以根据实际情况指定拦截的路径
	}
}

