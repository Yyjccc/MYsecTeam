package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_menu")
public class Menu {
	@TableId
	private Long id;

	private String menuName;

	private Long parentId;

	private Integer orderNum;

	private String path;

	private String component;

	private boolean isHidden;

	private boolean isDisable;

	private String menuType;

	private String perms;

	private String icon;

	private Date createTime;

	private Date updateTime;
}
