package com.hnust.myblog.Controller;

import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.Vo.ArticleDetailVo;
import com.hnust.myblog.Mode.Vo.HotArticle;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Service.interfaces.ArticleService;
import com.hnust.myblog.Mode.Base.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/article/list")
	public List<Article> test(){
		return articleService.list();
	}

	@GetMapping("/article/hotArticleList")
	public ResponseResult hotArticleList(){

		List<HotArticle> hotArticles= articleService.hotArticleList();
		return ResponseResult.response(hotArticles);
	}

	@GetMapping("/article/articleList")
	public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
		PageVo page =articleService.articleList(pageNum, pageSize,categoryId);

		return ResponseResult.response(page);
	}

	@GetMapping("/article/{id}")
	public ResponseResult getArticleDetail(@PathVariable Long id){
		ArticleDetailVo articleDetail = articleService.getArticleDetail(id);
		return ResponseResult.response(articleDetail);
	}

	@PutMapping("/article/updateViewCount/{id}")
	public ResponseResult updateViewCount(@PathVariable Long id){
		try {
			articleService.updateViewCount(id);
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, ArticleController.class);
		}
	}

	@PostMapping("/admin/article/update")
	public ResponseResult updateArticle(@RequestBody Article article){
		try {
			articleService.updateArticle(article);
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, ArticleController.class);
		}

	}


}
