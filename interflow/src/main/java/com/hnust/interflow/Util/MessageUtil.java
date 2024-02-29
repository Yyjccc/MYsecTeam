package com.hnust.interflow.Util;

import com.hnust.interflow.Data.Mode.Message;
import com.hnust.interflow.Data.chat.ChatLogs;
import com.hnust.interflow.Data.chat.Group;
import com.hnust.myctf.Mode.Base.CTFUser;

import java.util.List;

public class MessageUtil {
	public static Message build(){
		return new Message();
	}

	public static Message build(List<Long> list){
		return new Message(true,null,list);
	}

	public static Message build(CTFUser ctfUser, Group group,String message,List<Long> onlineList){
		ChatLogs chat = new ChatLogs();
		chat.setGroupId(group.getId());
		chat.setTime(System.currentTimeMillis());
		chat.setType("text");
		chat.setData(message);
		chat.setUserId(ctfUser.getId());
		return new Message(false,chat,onlineList);
	}
}
