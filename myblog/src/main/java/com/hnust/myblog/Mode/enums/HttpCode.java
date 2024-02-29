package com.hnust.myblog.Mode.enums;

public enum HttpCode {



	SUCCESS(200,"操作成功"),

	NEED_LOGIN(401,"需登录后操作"),

	NO_AUTH(403,"无权限操作"),
	SYSTEM_ERROR(500,"系统出错"),

	USERNAME_EXIST(501,"用户名已存在"),

	PHONENUMBER_EXIST(502,"手机号已存在"),

	EMAIL_EXIST(503,"邮箱已存在"),

	REQUIRE_USERNAME(504,"必须填写用户名"),

	LOGIN_ERROR(505,"用户名或者密码错误"),

	INVALID_TOKEN(506,"无效token")
	;

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	HttpCode(Integer code, String msg){
		this.code=code;
		this.msg=msg;
	}
}
