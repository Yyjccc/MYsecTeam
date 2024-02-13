package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class Article {
	@TableId
	private Long id;
	//标题
	private String title;
	//文章内容
	private String content;
	//文章类型
	private String type;
	//文章摘要
	private String summary;
	//所属分类id
	private Long categoryId;
	//缩略图
	private String thumbnail;
	//是否置顶
	private boolean top;
	//状态
	private Integer status;
	//评论数
	private Integer commentCount;
	//访问量
	private Long viewCount;
	//是否评论
	private boolean isComment;

	private Long createBy;

	private Date createTime;

	private Long updateBy;

	private Date updateTime;
	//删除标志
	private boolean delFlag;

	@TableField(exist = false)
	private String categoryName;
}
