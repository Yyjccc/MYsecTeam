package com.hnust.myblog.Mode;

import com.hnust.myblog.Mode.enums.HttpCode;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


@Data
public class ResponseResult<T> implements Serializable {
	private boolean flag=true;
	private Integer code;
	private String msg;
	private  T data;


	public ResponseResult(){
		this.code= HttpCode.SUCCESS.getCode();
		this.msg=HttpCode.SUCCESS.getMsg();
	}

	public static ResponseResult  response(Object data){
		ResponseResult<Object> result = new ResponseResult<>();
		result.setData(data);
		return result;
	}


	public static ResponseResult  response(){
		return new ResponseResult();
	}

	public static ResponseResult error(HttpCode httpCode){
		ResponseResult<Object> result = new ResponseResult<>();
		result.setFlag(false);
		result.setMsg(httpCode.getMsg());
		result.setCode(httpCode.getCode());
		return result;

	}

	public static ResponseResult error(Exception e,Class clazz){
		Logger logger = LoggerFactory.getLogger(clazz);
		String exceptionName = e.getClass().getSimpleName();
		logger.error(exceptionName);
		logger.error(e.getMessage(),e.fillInStackTrace());
		ResponseResult<Object> result = new ResponseResult<>();
		result.setFlag(false);
		result.setMsg(e.getMessage());
		result.setCode(500);
		return result;

	}
}
