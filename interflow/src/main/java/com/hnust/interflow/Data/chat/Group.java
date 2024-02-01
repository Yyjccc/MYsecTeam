package com.hnust.interflow.Data.chat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("groups")
@Data
public class Group {
	@TableId
	private Long id;
	private String name;
	private String createTime;
	private int mount;
	private boolean tmp;
	private String dieTime;
	private List<Long> members;
	private List<Long> admin;
	private String description;
	private List<String> labels;
	private Long creator;
}
