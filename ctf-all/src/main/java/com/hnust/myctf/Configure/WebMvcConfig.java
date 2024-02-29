package com.hnust.myctf.Configure;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//
//	@Value("${spring.mvc.interceptor.exclude-paths}")
//	private String[] excludePaths;

	//解决跨域请求问题


	@Value("${file.root}")
	private String uploadDirectory;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将/uploads/**路径映射到本地文件系统的上传目录
		registry.addResourceHandler("/ctf-all/static/**")
				.addResourceLocations("file:" + uploadDirectory + "/");
	}


	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setSameSite("None"); // 设置SameSite为None
		serializer.setUseSecureCookie(true); // 设置Secure标志，要求使用HTTPS协议
		return serializer;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// 允许所有域名
				.allowedOriginPatterns("*")
				// 允许所有方法
				.allowedMethods("*")
				// 允许携带认证信息
				.allowCredentials(true)
				// 允许所有请求头
				.allowedHeaders("*");
	}
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 注册拦截器，并指定拦截的路径
//		registry.addInterceptor(new LoginInterceptor())
//				.addPathPatterns("/api/**")
//				.excludePathPatterns(excludePaths);
//
//
//		// 可以根据实际情况指定拦截的路径
//	}
}

