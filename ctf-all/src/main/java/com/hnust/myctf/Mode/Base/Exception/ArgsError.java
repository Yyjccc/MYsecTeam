package com.hnust.myctf.Mode.Base.Exception;


public class ArgsError extends RuntimeException {

	static final long serialVersionUID= -6213747537215454829L;

	public final static String type="参数错误";

	public ArgsError() {
		super();
	}


	public ArgsError(String message) {
		super(message);
	}


	public ArgsError(String message, Throwable cause) {
		super(message, cause);
	}


	public ArgsError(Throwable cause) {
		super(cause);
	}


	protected ArgsError(String message, Throwable cause,
	                           boolean enableSuppression,
	                           boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
