package com.hnust.myctf.Mode.Base.Exception;


public class AuthenError extends RuntimeException {
	static final long serialVersionUID = -720737282339446932L;
	public final static String type="认证错误";
	public AuthenError() {
	}

	public AuthenError(String message) {
		super(message);
	}

	public AuthenError(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenError(Throwable cause) {
		super(cause);
	}

	public AuthenError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
