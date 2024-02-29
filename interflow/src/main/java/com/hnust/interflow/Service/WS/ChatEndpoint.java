package com.hnust.interflow.Service.WS;

import com.alibaba.fastjson.JSON;
import com.hnust.interflow.Data.Mapper.ChatLogMapper;
import com.hnust.interflow.Data.Mapper.GroupMapper;
import com.hnust.interflow.Data.Mode.Message;
import com.hnust.interflow.Data.chat.ChatLogs;
import com.hnust.interflow.Data.chat.Group;
import com.hnust.interflow.Util.MessageUtil;
import com.hnust.myctf.Mode.Base.CTFUser;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//ws服务器
@Service
@ServerEndpoint("/chat")
public class ChatEndpoint {
	@Autowired
	private ChatLogMapper chatLogMapper;

	@Autowired
	private GroupMapper groupMapper;
	
	private static final Map<CTFUser,Session> onlineUser=new ConcurrentHashMap<>();
	private static List<Long> usersID=new ArrayList<>();
	//建立连接的时候调用
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		CTFUser user = (CTFUser) httpSession.getAttribute("user");
		onlineUser.put(user,session);
		usersID.add(user.getId());
				//广播信息
		broadcast(MessageUtil.build(usersID));

	}
	
	
	
	private void broadcast(Message message) {
		Set<Map.Entry<CTFUser,Session>> entries=onlineUser.entrySet();
		for (Map.Entry<CTFUser, Session> entry : entries) {
			Session session=entry.getValue();
			//发送同步消息
			try {
				String msg=JSON.toJSONString(message);
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	//接收到消息后调用
	@OnMessage
	public void onMessage(String message){
		Message msg = JSON.parseObject(message, Message.class);
		ChatLogs content=msg.getContent();
		chatLogMapper.insert(content);
		Group group=groupMapper.selectById(content.getGroupId());
		sendMsgToGroup(group,msg);
	}
	
	//关闭连接时候调用
	@OnClose
	public void onClose(Session session){
		
		onlineUser.remove("用户");
			//broadcast("");

	}
	
	private void sendMsgToGroup(Group group,Message message){
		//向群内在线成员发送更新消息
		for (Map.Entry<CTFUser, Session> ctfUserSessionEntry : onlineUser.entrySet()) {
			if(group.getMembers().contains(ctfUserSessionEntry.getKey().getId())){
				String msg=JSON.toJSONString(message);
				try {
					ctfUserSessionEntry.getValue().getBasicRemote().sendText(msg);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
