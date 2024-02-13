package com.hnust.myblog.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Vo.ArticleListVo;
import com.hnust.myblog.Mode.Vo.HotArticle;
import com.hnust.myblog.Mode.Vo.PageVo;

import java.util.List;

public interface ArticleService extends IService<Article> {

	List<HotArticle> hotArticleList();




	PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
