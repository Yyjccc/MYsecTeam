package org.hnust.MYSec.Mode;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Message<T> {
	private int code=400;
	private boolean ok=false;
	//成功携带的数据
	private T info;
	//错误信息
	private String msg;


	public void setOK(){
		this.code=200;
		this.ok=true;
	}

	public void setFail(){
		this.code=400;
		this.ok=false;
	}
}

