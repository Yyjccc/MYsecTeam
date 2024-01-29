package com.hnust.myctf.Configure;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// If you need to customize the RestTemplate, you can do so in the same configuration class
	// For example, setting a timeout for requests
	@Bean
	public RestTemplate customRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(5000); // 5 seconds connect timeout
		requestFactory.setReadTimeout(5000);    // 5 seconds read timeout

		return new RestTemplate(requestFactory);
	}
}