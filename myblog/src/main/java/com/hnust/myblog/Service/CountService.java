package com.hnust.myblog.Service;



import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Vo.admin.Count;
import com.hnust.myblog.Service.interfaces.ArticleService;
import com.hnust.myblog.Service.interfaces.CategoryService;
import com.hnust.myblog.Service.interfaces.CommentService;
import com.hnust.myblog.Service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountService {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	public Count index(String id) {
		Count count = new Count();
		//文章浏览总量
		count.setViewCount(articleService.viewCount(id));
		//评论总量
		count.setMessageCount(commentService.countById(id));
		//用户总量
		count.setUserCount((int) userService.count());
		//文章总数
		count.setArticleCount(articleService.countById(id));
		Long ids=Long.parseLong(id);
		setCategoryVOList(ids,count);
		setArticleRankList(ids,count);
		return count;
	}

	//统计分类
	private void setCategoryVOList(Long id,Count count){
		List<Category> list= categoryService.countById(id);
		for (Category category : list) {
		    Integer cnt=articleService.countByCategory(category.getId());
		    count.setCategoryVOList(category,cnt);
		}
	}

	private void setArticleRankList(Long id,Count count){
		List <Article> articles= articleService.topViewCount(id,3);
		for (Article article : articles) {
			count.setArticleRankVOList(article.getArticleTitle(),article.getViewCount().intValue());
		}
	}




}
