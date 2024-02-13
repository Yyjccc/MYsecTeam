package com.hnust.myblog.Mode;

import com.hnust.myblog.Mode.enums.HttpCode;
import lombok.Data;

import java.io.Serializable;


@Data
public class ResponseResult<T> implements Serializable {
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
}
