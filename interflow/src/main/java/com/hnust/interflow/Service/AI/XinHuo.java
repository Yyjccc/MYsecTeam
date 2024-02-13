package com.hnust.interflow.Service.AI;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
@Data
@AllArgsConstructor
public class XinHuo extends WebSocketListener implements AIMode{

	public static final String hostUrl = "https://spark-api.xf-yun.com/v3.5/chat";
	@Value("${ai.key.xinhuo.app_id}")
	public static  String appid;
	@Value("${ai.key.xinhuo.api_secret}")
	public static  String apiSecret;

	@Value("${ai.key.xinhuo.api_key}")
	public static  String apiKey;
	public String authUrl;


	public static List<RequestPayload.DialogEntry> historyList=new ArrayList<>(); // 对话历史存储集合

	public static String totalAnswer=""; // 大模型的答案汇总

	private boolean init=false;

	public XinHuo() {

	}

	public void init(){
		init=true;
		try {
			authUrl=getAuthUrl(hostUrl,apiKey,apiSecret);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String common(String content) {
		return null;
	}


	public String chat(String content){

		return null;
	}









	public static boolean canAddHistory(){  // 由于历史记录最大上线1.2W左右，需要判断是能能加入历史
		int history_length=0;
		for(RequestPayload.DialogEntry temp:historyList){
			history_length=history_length+temp.content.length();
		}
		if(history_length>12000){
			historyList.remove(0);
			historyList.remove(1);
			historyList.remove(2);
			historyList.remove(3);
			historyList.remove(4);
			return false;
		}else{
			return true;
		}
	}



	public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
		URL url = new URL(hostUrl);
		// 时间
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = format.format(new Date());
		// 拼接
		String preStr = "host: " + url.getHost() + "\n" +
				"date: " + date + "\n" +
				"GET " + url.getPath() + " HTTP/1.1";
		// SHA256加密
		Mac mac = Mac.getInstance("hmacsha256");
		SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
		mac.init(spec);
		byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
		String sha = Base64.getEncoder().encodeToString(hexDigits);
		// 拼接
		String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
		// 拼接地址
		HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
				addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
				addQueryParameter("date", date).//
				addQueryParameter("host", url.getHost()).//
				build();
		return httpUrl.toString();
	}




	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		super.onOpen(webSocket, response);
		MyThread myThread = new MyThread(webSocket);
		myThread.start();
	}

	@Override
	public void onMessage(WebSocket webSocket, String text) {
		AIResponse myJsonParse = JSON.parseObject(text, AIResponse.class);
		if (myJsonParse.header.code != 0) {
			System.out.println("发生错误，错误码为：" + myJsonParse.header.code);
			System.out.println("本次请求的sid为：" + myJsonParse.header.sid);
			webSocket.close(1000, "");
		}
		List<Text> textList = myJsonParse.payload.choices.text;
		for (Text temp : textList) {
			System.out.print(temp.content);
			totalAnswer=totalAnswer+temp.content;
		}
		if (myJsonParse.header.status == 2) {
			// 可以关闭连接，释放资源
			if(canAddHistory()){
				RequestPayload.DialogEntry DialogEntry= new RequestPayload.DialogEntry();
				DialogEntry.setRole("assistant");
				DialogEntry.setContent(totalAnswer);
				historyList.add(DialogEntry);
			}else{
				historyList.remove(0);
				RequestPayload.DialogEntry DialogEntry=new RequestPayload.DialogEntry();
				DialogEntry.setRole("assistant");
				DialogEntry.setContent(totalAnswer);
				historyList.add(DialogEntry);
			}

		}
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		super.onFailure(webSocket, t, response);
		try {
			if (null != response) {
				int code = response.code();
				System.out.println("onFailure code:" + code);
				System.out.println("onFailure body:" + response.body().string());
				if (101 != code) {
					System.out.println("connection failed");
					System.exit(0);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class MyThread extends Thread {
		private WebSocket webSocket;

		public MyThread(WebSocket webSocket) {
			this.webSocket = webSocket;
		}

		public void run() {
			try {
			
				webSocket.close(1000, "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Data
	public static class RequestPayload {
		private Header header;
		private Parameter parameter;
		private Payload payload;

		@Data
		public static class Header {
			private String app_id;
			private String uid;

		}
		@Data
		public static class Parameter {
			private Chat chat;

			@Data
			public static class Chat {
				private String domain;
				private double temperature;
				private int max_tokens;

			}
		}
		@Data
		public static class Payload {
			private Message message;

			@Data
			public static class Message {
				private List<DialogEntry> text;

			}
		}
		@Data
		public static class DialogEntry {
			private String role;
			private String content;

		}
	}
	class AIResponse {
		Header header;
		Payload payload;
	}

	class Header {
		int code;
		int status;
		String sid;
	}

	class Payload { 
		Choices choices;
	}

	class Choices {
		List<Text> text;
	}

	class Text {
		String role;
		String content;
	}
	@Data
	class RoleContent{
		String role;
		String content;
	}

}

