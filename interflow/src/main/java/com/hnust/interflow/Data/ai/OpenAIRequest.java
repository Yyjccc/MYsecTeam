package com.hnust.interflow.Data.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIRequest {

	private String model;

	private String prompt;

	private int max_tokens;

	private Float temperature;

//
//	public OpenAIRequest(){
//		this.message=new ArrayList<>();
//	}
//
//	public void addMessage(String role,String content){
//		Message message=new Message(role,content);
//		this.message.add(message);
//	}
//
//}
//
//@Data
//@AllArgsConstructor
//class Message{
//	private String role;
//
//	private String content;
//
}