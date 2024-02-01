package com.hnust.interflow;


import com.hnust.interflow.Service.AI.OpenAI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.net.Proxy;

@SpringBootTest
public class PyTest {

	@Autowired
	public OpenAI openAI;


	@Test
	public void test1(){
		System.out.println(openAI.common("say hello"));
	}
}
