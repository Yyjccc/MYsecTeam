package com.hnust.myctf.Utils;

import com.hnust.myctf.Mode.Message;

public final class ResposeUtil implements AppLogger {




	public static Message error(String s){
		Message message1=new Message();
		message1.setCode(400);
		message1.setOk(false);
		message1.setMsg(s);
		return message1;
	}

	public static Message error(Exception e,String s){
		Message message=new Message();
		message.setFail();
		logger.error(s+" : "+e.getMessage());
		message.setMsg(s);
		return message;
	}

	public static Message error(Exception e){
		Message message=new Message();
		message.setFail();
		logger.error(e.getMessage());
		e.printStackTrace();
		message.setMsg(e.getMessage());
		return message;
	}




	public static Message response(Object obj){
		Message message=new Message();
		message.setOK();
		message.setInfo(obj);
		return message;
	}

	public static Message response(){
		Message message=new Message();
		message.setOK();
		return message;
	}

}
