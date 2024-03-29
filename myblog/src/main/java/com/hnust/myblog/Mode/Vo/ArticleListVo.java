package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
	private Long id;
	//标题
	private String articleTitle;
	//文章摘要
	private String summary;
	//所属分类
	private String categoryName;
	//缩略图
	private String articleCover;

	//评论数
	private Integer commentCount;
	//访问量
	private Long viewCount;

	private Date createTime;

}
