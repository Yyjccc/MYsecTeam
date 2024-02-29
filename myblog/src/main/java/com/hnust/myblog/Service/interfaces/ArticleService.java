package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Vo.ArticleDetailVo;
import com.hnust.myblog.Mode.Vo.HotArticle;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Mode.Vo.StaticFile;

import java.util.List;

public interface ArticleService extends IService<Article> {

	List<HotArticle> hotArticleList();

	PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId);

	ArticleDetailVo getArticleDetail(Long id);

	void updateViewCount(Long id);

	Integer viewCount(String id);

	Integer countById(String id);

	Integer countByCategory(Long id);

	List<Article> topViewCount(Long id, int i);

	List<Article> getListByUserId(String id);

	void setTagList(Article article);

	Article getAdminArticle(Long articleId);

	void updateImg(String id, StaticFile staticFile);

	void updateArticle(Article article);
}
