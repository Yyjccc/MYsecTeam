package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
	private Long id;

	private String avatar;

	private List<String> permissionList;

	private List<String> roleList;



}
