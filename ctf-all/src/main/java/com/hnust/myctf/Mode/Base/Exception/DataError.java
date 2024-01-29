package com.hnust.myctf.Mode.Base.Exception;

public class DataError extends RuntimeException{
	static final long serialVersionUID= -7778670281724964667L;
	public final static String type="数据错误";
	public DataError() {
	}

	public DataError(String message) {
		super(message);
	}

	public DataError(String message, Throwable cause) {
		super(message, cause);
	}

	public DataError(Throwable cause) {
		super(cause);
	}

	public DataError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
