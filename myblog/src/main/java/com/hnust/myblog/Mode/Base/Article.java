package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hnust.myblog.Mode.Vo.admin.TagVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
@Schema(description = "文章模型")
public class Article {
	@TableId
	private Long id;
	private Long userId;
	private Long categoryId;

	//标题
	private String articleTitle;
	//文章内容
	private String articleContent;
	//文章类型
	private String articleType;
	//文章摘要
	private String summary;
	//缩略图
	private String articleCover;
	//是否置顶
	private int isTop;
	//状态
	private Integer status;
	//评论数
	private Integer commentCount;
	//访问量
	private Long viewCount;
	//是否评论
	private int isRecommend;

	private Date createTime;

	private Date updateTime;
	//删除标志
	private int isDelete;

	@TableField(exist = false)
	private String categoryName;

	@TableField(exist = false)
	private List<String> tagNameList;

	@TableField(exist = false)
	private List<TagVo> tagVOList;

	public Article(Long id, long viewCount) {
		this.id=id;
		this.viewCount=viewCount;
	}
}
