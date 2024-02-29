package com.hnust.myblog.Mode.Vo.admin;

import com.hnust.myblog.Mode.Base.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
	private int count;
	private List<ArticleVo> recordList;

	public ArticleListVo(List<Article> articles) {
		recordList=new ArrayList<>();
		for (Article article : articles) {
			ArticleVo articleVo = new ArticleVo(article, article.getTagVOList());
			recordList.add(articleVo);
		}
		count=recordList.size();
	}
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class ArticleVo {
	private Long id;
	private String articleCover;
	private String articleTitle;
	private String articleType;
	private String categoryName;
	private Date createTime;
	private int isDelete;
	private int isRecommend;
	private int isTop;
	private int likeCount;
	private int status;
	private Long viewCount;
	private List<TagVo> tagVOList;


	public ArticleVo(Article article, List<TagVo> tagVOList) {
		this.tagVOList=tagVOList;
		id=article.getId();
		articleCover=article.getArticleCover();
		articleTitle=article.getArticleTitle();
		articleType=article.getArticleType();
		categoryName=article.getCategoryName();
		createTime=article.getCreateTime();
		isDelete=article.getIsDelete();
		isRecommend=article.getIsRecommend();
		isTop=article.getIsTop();
		likeCount=0;
		viewCount=article.getViewCount();
		status=article.getStatus();
	}
}
