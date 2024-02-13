package com.hnust.myblog.Controller;

import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.Vo.HotArticle;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Service.ArticleService;
import com.hnust.myblog.Mode.Base.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/list")
	public List<Article> test(){
		return articleService.list();
	}

	@GetMapping("/hotArticleList")
	public ResponseResult hotArticleList(){

		List<HotArticle> hotArticles= articleService.hotArticleList();
		return ResponseResult.response(hotArticles);
	}

	@GetMapping("/articleList")
	public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
		PageVo page =articleService.articleList(pageNum, pageSize,categoryId);

		return ResponseResult.response(page);
	}

}
