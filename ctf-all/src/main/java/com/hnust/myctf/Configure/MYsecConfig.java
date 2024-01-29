package com.hnust.myctf.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:MYsecConfig.properties")
//自定义的配置类
@Data
@AllArgsConstructor
public class MYsecConfig {

	@Value("${ctf.mode.flag.random}")
	public static boolean random;


}
