package com.hnust.myblog.Mode.Base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private Integer code;
	private String msg;
	private Object data;
	private Date time;

	public String getData() {
		if(data==null){
			throw new RuntimeException(msg);
		}
		return data.toString();
	}
}
