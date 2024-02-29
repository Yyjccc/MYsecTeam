package com.hnust.common.Configure;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	@Value("${file.root}")
	private String uploadDirectory;
	private List<HttpMessageConverter<?>> converters;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将/uploads/**路径映射到本地文件系统的上传目录
		registry.addResourceHandler("common/static/**")
				.addResourceLocations("file:./common/static/");
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

	@Bean
	public HttpMessageConverter fastJsonHttpMessageConverters(){
		FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);

		fastJsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);
		fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
		fastJsonConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter=fastJsonConverter;

		return converter;

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		converters.add(fastJsonHttpMessageConverters());
		//converters.add(new ByteArrayHttpMessageConverter());
//		ByteArrayHttpMessageConverter byteConverter = new ByteArrayHttpMessageConverter();
//		byteConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.IMAGE_JPEG));
//		converters.add(byteConverter);
	}
}

