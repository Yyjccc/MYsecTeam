package com.hnust.interflow.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AIConfig {
	@Value("${ai.key.openai}")
	public String openAI_key;

	@Value("${ai.key.bing}")
	public String bing_key;


	@Value("${ai.proxy.host}")
	public String proxyHost;

	@Value("${ai.proxy.port}")
	public int proxyPort;

	@Value("${ai.server}")
	public String aiPyServer;
}
