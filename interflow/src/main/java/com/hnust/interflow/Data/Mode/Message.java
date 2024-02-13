package com.hnust.interflow.Data.Mode;

import com.hnust.interflow.Data.chat.ChatLogs;
import com.hnust.myctf.Mode.CTFUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	private boolean system;
	private ChatLogs content;
	private List<Long> onlineUsers;
}
