package com.hnust.interflow.Service.WS;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//ws服务器
@Component
@ServerEndpoint("/chat")
public class ChatEndpoint {
	
	private static final Map<String,Session> onlineUser=new ConcurrentHashMap<>();
	
	//建立连接的时候调用
	@OnOpen
	public void onOpen(Session session, EndpointConfig config){
		config.getUserProperties().get(HttpSession.class.getName());
		onlineUser.put("用户名",session);
		//广播信息
	}
	
	
	
	private void broadcast(String message) throws IOException {
		Set<Map.Entry<String,Session>> entries=onlineUser.entrySet();
		for (Map.Entry<String, Session> entry : entries) {
			Session session=entry.getValue();
			//发送同步消息
			session.getBasicRemote().sendText(message);
		}
	}
	
	//接收到消息后调用
	@OnMessage
	public void onMessage(String message){
		JSON.parseObject(message);
	}
	
	//关闭连接时候调用
	@OnClose
	public void onClose(Session session){
		
		onlineUser.remove("用户");

		try {
			broadcast("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
