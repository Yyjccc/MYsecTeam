package com.hnust.interflow.Service.AI;


import com.alibaba.fastjson.JSON;
import com.hnust.interflow.Configure.AIConfig;


import com.hnust.interflow.Data.ai.OpenAIRequest;


import com.hnust.interflow.Util.Http;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Data
public class OpenAI implements AIMode{

	private static Logger logger = LoggerFactory.getLogger(OpenAI.class);

	public String modeType="gpt-3.5-turbo";

	@Autowired
	public RestTemplate restTemplate;

	public String[] hosts=new String[]{"https://api.openai.com/v1/completions"};

	public String key;

	private AIConfig config;

	public HashMap<String,String> headers;

	private AIProxy proxy=new AIProxy();

	@Autowired
	public OpenAI(AIConfig config){
		this.config=config;
		key=config.openAI_key;
		//设定http认证头
		headers=new HashMap<>();
		headers.put("Authorization","Bearer "+key);
		//headers.put("Content-Type",MediaType.APPLICATION_JSON_VALUE);
		proxy.setHost(config.proxyHost);
		proxy.setPort(config.proxyPort);

	}


	public String common(String content) {
		String url=hosts[0];
		//String data="{\"model\": \""+modeType+"\", \"prompt\": \""+content+"\",\"max_tokens\": 7, \"temperature\": 0 }";
		OpenAIRequest request=new OpenAIRequest(modeType,content,7,0F);

		try {
			String res= Http.doPost(url,"json",request,headers,proxy);

			return res;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}



	public  String http_post(OpenAIRequest request){
		//后期优化选取主机
		String host=hosts[0];
		request.setModel(modeType);
//		try {
//			HttpUtils.doPost(host,JSON.toJSONString(request),headers,proxy);
//		//return 	HttpUtils.doPostJsonData(host, JSON.toJSONString(request),headers,proxy);
//		} catch (IOException e) {
//			logger.error("请求错误,请检查配置-- "+e.getMessage());
//			e.printStackTrace();
//		}
		return null;
	}



}
