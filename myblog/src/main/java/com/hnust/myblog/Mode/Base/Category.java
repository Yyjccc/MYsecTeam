package com.hnust.myblog.Mode.Base;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.PhantomReference;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class Category {
	@TableId
	private Long id;

	private String name;

	//父类id
	private Long pid;

	private String description;

	//是否启用
	private int status;

	private Long userId;

	private Date createTime;

	private Date updateTime;

	private Long updateBy;

	private boolean delFlag;


}
