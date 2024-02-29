package com.hnust.common.Mode.Base;

import com.hnust.common.Mode.emnus.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private Integer code;
	private String msg;
	private Object data;
	private Date time;

	public static Response response(){
		Response response = new Response();
		response.setTime(new Date());
		response.setCode(ServiceStatus.SUCCESS.getCode());
		response.setMsg(ServiceStatus.SUCCESS.getMsg());
		return response;
	}

	public static Response response(Object o){
		Response response=response();
		response.setData(o);
		return response;
	}


	public static Response  error(Exception e,Class clazz){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.error(e.getMessage(),e.fillInStackTrace());
		Response response = new Response();
		response.setCode(500);
		response.setMsg(e.getMessage());
		return response;
	}

	public static Response error(ServiceStatus status){
		Response response = new Response();
		response.setMsg(status.getMsg());
		response.setCode(status.getCode());
		return response;
	}

}
