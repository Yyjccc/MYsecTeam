package com.hnust.interflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hnust.interflow.Data.Mapper")
public class InterflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(InterflowApplication.class, args);
	}
}
