package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article_tag")
public class ArticleTag {
	@TableId
	private Long id;
	private Long tagId;
	private Long articleId;

	public ArticleTag(Long tagId,Long articleId){
		this.tagId=tagId;
		this.articleId=articleId;
	}
}
