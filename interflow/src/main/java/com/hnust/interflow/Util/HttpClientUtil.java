package com.hnust.interflow.Util;


import com.hnust.interflow.Service.AI.AIProxy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

	public static String doGet(String url) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public static String doPost(String url, Map<String, String> formData) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<>();
		for (Map.Entry<String, String> entry : formData.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public static String doGet(String url, Map<String, String> headers, AIProxy proxy) throws IOException {
		HttpClient httpClient = createHttpClient(proxy);
		HttpGet httpGet = new HttpGet(url);

		// 设置请求头
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}
	public static String doPost(String url, String jsonBody, Map<String, String> headers, AIProxy proxy) throws IOException {
		HttpClient httpClient = createHttpClient(proxy);
		HttpPost httpPost = new HttpPost(url);
		// 设置请求头
		httpPost.setHeader("Accept","*/*");
        httpPost.setHeader("Accept-Encoding","gzip, deflate");
        httpPost.setHeader("Cache-Control","no-cache");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		httpPost.setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// 设置请求体
		StringEntity entity = new StringEntity(jsonBody);
		httpPost.setEntity(entity);
		Header[] allHeaders = httpPost.getAllHeaders();
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		System.out.println(response.getStatusLine().getStatusCode());
		return EntityUtils.toString(responseEntity);
	}
	public static String doPost(String url, Map<String, String> formData, Map<String,String> headers,AIProxy proxy ) throws IOException {
		HttpClient httpClient = createHttpClient(proxy);
		HttpPost httpPost = new HttpPost(url);

		// 设置请求头
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}

		List<NameValuePair> params = new ArrayList<>();
		for (Map.Entry<String, String> entry : formData.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		httpPost.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	private static HttpClient createHttpClient(AIProxy proxy) {
		RequestConfig config;
		if (proxy!=null) {
			config = RequestConfig.custom()
					.setProxy(new org.apache.http.HttpHost(proxy.getHost(), proxy.getPort()))
					.build();
		} else {
			config = RequestConfig.DEFAULT;
		}

		return HttpClients.custom()
				.setDefaultRequestConfig(config)
				.build();
	}


}

