package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {
	@TableId
	private Long id;

	private String userName;

	//昵称
	private String nickName;

	private String password;

	private Long ctfUserId;

	private String email;

	private String githubAddress;

	private Long qq;

	private int age;

	private Date createTime;

	private String sex;

	private String avatar;

	private Long phonenumber;

}
