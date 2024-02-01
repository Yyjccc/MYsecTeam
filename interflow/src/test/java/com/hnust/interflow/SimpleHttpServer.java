package com.hnust.interflow;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

	public static void main(String[] args) throws IOException {
		// 创建一个 HTTP 服务器，监听端口 8080
		HttpServer server = HttpServer.create(new InetSocketAddress(8088), 0);

		// 创建一个上下文，用于处理请求
		server.createContext("/", new MyHandler());

		// 设置线程池，这里使用默认的线程池
		server.setExecutor(null);

		// 启动服务器
		server.start();
		System.out.println("Server is running on port 8088");
	}

	// 自定义处理器，实现 HttpHandler 接口
	static class MyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// 获取请求方法
			String requestMethod = exchange.getRequestMethod();
			// 获取请求路径
			String requestURI = exchange.getRequestURI().toString();
			System.out.println(requestMethod+" "+requestURI);
			// 获取请求头
			exchange.getRequestHeaders().forEach((key, values) ->
					System.out.println(key + ": " + values));

			// 获取请求体
			InputStream requestBody = exchange.getRequestBody();
			byte[] requestBodyBytes = requestBody.readAllBytes();
			String requestBodyString = new String(requestBodyBytes);
			System.out.println();
			System.out.println(requestBodyString);

			// 构造响应体
			String response = "Hello, this is the response!";
			exchange.sendResponseHeaders(200, response.length());
			// 发送响应体
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.write(response.getBytes());
			System.out.println();
			// 关闭连接
			responseBody.close();
		}
	}
}

