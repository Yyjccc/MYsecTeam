package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo extends ArticleListVo{
	private Long categoryId;
	private String articleContent;

}
