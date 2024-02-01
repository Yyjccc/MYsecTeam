package com.hnust.interflow.Data.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("ai_chat_logs")
@Data
public class AiChatLogs {
	@TableId
	private Long id;

	private String time;

	private String question;

	private String mode;

	private String model;

	private String answer;

	private Long userId;

	private boolean success;

	private String args;


}
