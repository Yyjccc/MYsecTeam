package com.hnust.interflow.Data.ai;

import lombok.Data;

import java.util.List;

@Data
public class OpenAIResponse {
	private String id;

	private String object;

	private long created;

	private String model;

	private Usage usage;

	//private List<Choice> choices;

}

@Data
class Usage {
	private int promptTokens;
	private int completionTokens;
	private int totalTokens;
}
//@Data
//class Choice{
//	private Message message;
//	private String finishReason;
//	private int index;
//}
