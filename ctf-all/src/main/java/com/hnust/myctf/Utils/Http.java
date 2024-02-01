package com.hnust.myctf.Utils;

import com.alibaba.fastjson.JSONObject;
import io.micrometer.common.lang.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Http {

    private static final HashMap<String,String> postHeaderMap=new HashMap<>();

    static {
        postHeaderMap.put("connection","Keep-Alive");
        postHeaderMap.put("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        postHeaderMap.put("accept","*/*");
        postHeaderMap.put("Content-Type","application/json;charset=utf-8");
    }




    //发起hhtp get请求
    public static String doGet(String httpUrl){
        //链接
        HttpURLConnection connection = null;
        try {
          connection=buildHttpConnect(httpUrl,new HashMap<>(),"GET");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getResponse(connection);
    }

    //发起http post请求
    public static String doPost(String httpUrl, @Nullable String param) {
        StringBuffer result = new StringBuffer();
        //连接
        OutputStream os = null;
        HttpURLConnection connection = null;
        try {
            connection=buildHttpConnect(httpUrl,postHeaderMap,"POST");

             if (null != param && param.equals("")) {
                //设置参数
                os = connection.getOutputStream();
                //拼装参数
                os.write(param.getBytes("UTF-8"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return getResponse(connection);
    }


    public static String doPost(String httpUrl, HashMap<String,String> head, JSONObject body){
        HttpURLConnection connection = null;
        OutputStream os = null;
        try {
            head.putAll(postHeaderMap);
            connection=buildHttpConnect(httpUrl,head,"POST");
            //拼装参数
            String param=body.toString();
            if (null != param && param.equals("")) {
                //设置参数
                os = connection.getOutputStream();
                //拼装参数
                os.write(param.getBytes("UTF-8"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return getResponse(connection);
    }

    private static HttpURLConnection buildHttpConnect(String httpUrl,HashMap<String,String> headers,String method) throws IOException {
        HttpURLConnection connection = null;
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方法
            connection.setRequestMethod(method);
            //设置连接超时时间
            connection.setConnectTimeout(15000);
            //设置读取超时时间
            connection.setReadTimeout(15000);
            //设置是否可读取
            connection.setDoOutput(true);
            connection.setDoInput(true);
            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(),header.getValue());
            }

    return connection;
    }


    private static String getResponse(HttpURLConnection connection){
        StringBuffer result = new StringBuffer();

        InputStream is = null;
        BufferedReader br = null;
        try {
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                        result.append("\r\n");
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result.toString();
    }
}
