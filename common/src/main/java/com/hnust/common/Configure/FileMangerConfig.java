package com.hnust.common.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMangerConfig {
	//文件存储模式
	@Value("${file.manager.mode}")
	private String mode;
	//文件管理根目录
	@Value("${file.root}")
	private String rootDir;

	@Value("${file.manager.allow}")
	private String allowType;


	public List getAllowType(){
		return Arrays.asList(allowType.split(","));
	}


}
