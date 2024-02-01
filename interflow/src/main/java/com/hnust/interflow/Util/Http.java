package com.hnust.interflow.Util;

import com.alibaba.fastjson.JSON;
import com.hnust.interflow.Data.Exception.HTTPException;
import com.hnust.interflow.Service.AI.AIProxy;
import com.hnust.myctf.Mode.Base.Exception.ArgsError;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public    class  Http {

	public static String doGET(String url,AIProxy proxy) throws IOException {
		OkHttpClient client=createClient(proxy);
		Request.Builder builder=defaultBuild();
		Request request=builder.url(url).build();
		return response(client,request);
	}

	public static String doGET(String url, Map<String,String> headers, AIProxy proxy) throws IOException {
		OkHttpClient client=createClient(proxy);
		Request.Builder builder=defaultBuild();
		builder=setHeaders(builder,headers);
		Request request=builder.url(url).build();
		return  response(client,request);
	}


	public static String doPost(String url,String type,Object data,AIProxy proxy) throws IOException {
		OkHttpClient client=createClient(proxy);
		Request.Builder builder=setReqBody(defaultBuild(),type,data);
		Request request=builder.url(url).build();
		return response(client,request);
	}

	public static String doPost(String url,String type,Object data,Map<String,String> headers,AIProxy proxy) throws IOException {
		OkHttpClient client=createClient(proxy);
		Request.Builder builder=setReqBody(setHeaders(defaultBuild(),headers),type,data);
		Request request=builder.url(url).build();
		return response(client,request);
	}


	private static   Request.Builder setHeaders(Request.Builder builder,Map<String,String> headers){
		for (Map.Entry<String,String> header:headers.entrySet()) {
			builder=builder.addHeader(header.getKey(),header.getValue());
		}
		return builder;
	}

	private static OkHttpClient createClient(AIProxy proxy){
		if(proxy!=null) {
			return new OkHttpClient.Builder().callTimeout(10,TimeUnit.SECONDS).proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxy.getHost(), proxy.getPort()))).build();
		}
		return new OkHttpClient();
	}

	private static Request.Builder defaultBuild(){
		Request.Builder builder= new Request.Builder();
		builder=builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
		return builder.addHeader("Referer","https://www.google.com/");
	}

	private static String response(OkHttpClient client,Request request) throws IOException {
		Response response= null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			close(client);
		}
		if(!response.isSuccessful()){
			throw new HTTPException("请求错误，url: "+response.request().url()+";状态码： "+response.code()+";响应内容 ："+response.body().string());
		}

		return response.body() != null ? response.body().string() : null;
	}


	private static Request.Builder setReqBody(Request.Builder builder,String type, Object body){
		RequestBody requestBody=null;
		if(type.equals("json")){
			 requestBody = RequestBody.create(
					MediaType.parse("application/json"), JSON.toJSONString(body));
		} else if (type.equals("form")) {
			//pass
			 requestBody = RequestBody.create(
					MediaType.parse("application/json"), JSON.toJSONString(body));
		} else if (type.equals("file")) {

		}
	if (requestBody==null){
		throw new ArgsError("设置请求体参数错误");
	}
	builder=builder.post(requestBody);
		return builder;
	}


	private static void close(OkHttpClient client){
		client.dispatcher().executorService().shutdown();
	}
}
