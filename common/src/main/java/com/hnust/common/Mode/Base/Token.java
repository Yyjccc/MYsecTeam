package com.hnust.common.Mode.Base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	private String token;
	private String module;
	private Long expire;
	private Long id;


	public Token(Long expire,Long id){
		this.expire=expire;
		this.id=id;
	}
}
