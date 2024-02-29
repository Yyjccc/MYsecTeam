package com.hnust.common.Mode.emnus;


public enum ServiceStatus {

	SUCCESS(200,"操作成功"),

	MYSQL_SERVICE_ERROR(1001,"数据库错误"),

	REDIS_CONNECT_TIMEOUT(2001,"redis服务器连接超时"),

	ARGS_ERROR(4001,"参数传递错误"),

	PATH_ERROR(4002,"路径错误"),

	NO_MUST_ARG(4003,"缺少必要参数"),

	INVALID_TOKEN(4004,"无效token")

	;

	int code;
	String msg;

	ServiceStatus(int code,String msg){
		this.code=code;
		this.msg=msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
