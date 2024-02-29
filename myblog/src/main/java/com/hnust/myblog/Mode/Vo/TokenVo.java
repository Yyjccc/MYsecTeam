package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
	private String token;
	private String avatar;
	private Long id;
	private String email;
	private String githubAddress;
	private Long qq;
	private int age;
	private Date createTime;
	private String sex;
	private String nickName;
	private Long phonenumber;
}
