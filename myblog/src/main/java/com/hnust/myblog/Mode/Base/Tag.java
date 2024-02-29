package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("tags")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {
	@TableId
	private Long id;

	private String tagName;

	private Date createTime;

	private Date updateTime;

	private Long createBy;


}
