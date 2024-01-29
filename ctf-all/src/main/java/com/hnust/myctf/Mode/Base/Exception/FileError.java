package com.hnust.myctf.Mode.Base.Exception;

public class FileError extends RuntimeException{
	static final long serialVersionUID= -3642853267576905379L;
	public final static String type="文件错误";

	public FileError() {
	}

	public FileError(String message) {
		super(message);
	}

	public FileError(String message, Throwable cause) {
		super(message, cause);
	}

	public FileError(Throwable cause) {
		super(cause);
	}

	public FileError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
