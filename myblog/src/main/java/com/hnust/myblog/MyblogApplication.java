package com.hnust.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.hnust.myblog.Mode.Mapper")
//feign客户端
@EnableFeignClients
//nacos注入
@EnableDiscoveryClient
//开启定时任务
@EnableScheduling
//swagger接口文档（路径：/swagger-ui.html）

public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}
