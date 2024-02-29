package com.hnust.myblog.Configure;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI SwaggerOpenAPI(){
		return new OpenAPI().info(new Info().title("MYsec平台blog微服务")
				.description("博客模块后端服务")
				.version("v3.0.0"))
				.externalDocs(new ExternalDocumentation().description("设计文档"));

	}
}
