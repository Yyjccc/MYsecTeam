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
@TableName("friend_link")
public class FriendLink {
	@TableId
	private Long id;

	private String name;

	private String logo;

	private String description;

	private String address;

	private int status;

	private Long createBy;

	private Date createTime;

	private Long updateBy;

	private Date updateTime;

}
