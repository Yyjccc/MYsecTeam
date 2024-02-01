package com.hnust.interflow.Data.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("chat_logs")
@Data
public class ChatLogs {
	@TableId
	private Long id;

	private String time;

	private Long groupId;

	private String type;

	private Object data;

	private Long userId;

}
