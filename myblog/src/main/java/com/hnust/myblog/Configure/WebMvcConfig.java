package com.hnust.myblog.Configure;



import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {







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
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_JSON_UTF8));
		SerializeConfig.globalInstance.put(Long.class, ToStringSerializer.instance);
		fastJsonConfig.setSerializeConfig(SerializeConfig.globalInstance);
		fastJsonConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter=fastJsonConverter;
		return converter;

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.clear();
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		converters.add(new StringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverters());

	}
}

