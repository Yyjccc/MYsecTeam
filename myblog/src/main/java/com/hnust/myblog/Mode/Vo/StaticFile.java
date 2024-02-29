package com.hnust.myblog.Mode.Vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticFile {
	private Long id;
	private String name;
	private String mode;
	private boolean local;
	private String type;
	private String src;
	private Date createTime;
	private Date updateTime;
}
