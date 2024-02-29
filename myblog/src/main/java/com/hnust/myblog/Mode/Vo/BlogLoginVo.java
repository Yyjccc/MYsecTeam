package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginVo {
	private String token;
	private TokenVo userInfo;
	private boolean isAdmin;
}
