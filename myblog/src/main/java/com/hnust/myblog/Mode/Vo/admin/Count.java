package com.hnust.myblog.Mode.Vo.admin;

import com.hnust.myblog.Mode.Base.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Count {
	//文章浏览总数
	private Integer viewCount;
	//评论总数
	private Integer messageCount;
	//用户量
	private Integer userCount;

	//文章数
	private Integer articleCount;
	//文章分类统计
	private List<CategoryCount> categoryVOList=new ArrayList<>();

	//标签统计
	private List<TagVo> tagVOList;

	//文章浏览统计
	private List<ArticleCount> articleRankVOList=new ArrayList<>();
	//访客统计
	private List userViewVOList;
	//上传文章统计
	private List<AddArticle> articleStatisticsList;

	public void setCategoryVOList(Category category,Integer count){
		 categoryVOList.add(new CategoryCount(category.getId(),category.getCategoryName(),count));
	}

	public void setArticleRankVOList(String title,Integer count) {
		articleRankVOList.add(new ArticleCount(title,count));
	}
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class CategoryCount{
	private Long id;
	private String categoryName;
	private Integer articleCount;
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class AddArticle{
	private Date date;
	private Integer count;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ArticleCount{
	private String articleTitle;
	private Integer viewCount;
}
