package com.hnust.common.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("static_file")
public class StaticFile {
	@TableId
	private Long id;
	private String name;
	private String mode;
	private boolean local;
	private String type;
	private String src;
	private Date createTime;
	private Date updateTime;

	public StaticFile(String name,String type){
		this.type=type;
		this.name=name;
		createTime=new Date();
		updateTime=new Date();
	}
}
