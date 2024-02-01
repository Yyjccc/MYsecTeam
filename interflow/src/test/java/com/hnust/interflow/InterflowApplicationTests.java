package com.hnust.interflow;

import com.alibaba.fastjson.JSON;
import com.hnust.interflow.Configure.AIConfig;
import com.hnust.interflow.Data.ai.OpenAIRequest;
import com.hnust.interflow.Service.AI.OpenAI;
import com.hnust.interflow.Util.Http;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class InterflowApplicationTests {

	@Autowired
	OpenAI openAI;
	@Test
	void contextLoads() {
	}
	@Test
	public void test(){
		OpenAIRequest request = new OpenAIRequest();
		//request.addMessage("user","hello");
		request.setTemperature(0.7F);
		String result=openAI.http_post(request);
		System.out.println(result);
	}

	@Test
	public void http() throws IOException {
		OpenAI openAI1 = new OpenAI(new AIConfig());
		System.out.println(Http.doPost("http://127.0.0.1:8088","json", openAI1,null));

	}



}
