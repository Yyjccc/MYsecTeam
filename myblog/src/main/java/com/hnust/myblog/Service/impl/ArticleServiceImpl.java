package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Mapper.ArticleMapper;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Vo.ArticleListVo;
import com.hnust.myblog.Mode.Vo.HotArticle;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Service.ArticleService;
import com.hnust.myblog.Service.CategoryService;
import com.hnust.myblog.Service.utils.BeanCopyUtil;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

	@Autowired
	private CategoryService categoryService;

	@Override
	public List<HotArticle> hotArticleList() {
		LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
		//1.正式文章。
		queryWrapper.eq(Article::getStatus,SystemConstance.NORMAL_ARTICLE_STATUS);
		// 2.按照浏览量排序。
		queryWrapper.orderByDesc(Article::getViewCount);
		queryWrapper.select(Article::getId,Article::getTitle,Article::getViewCount);
		// 3.返回十条数据
		Page<Article> page=new Page<>(1, SystemConstance.HOT_ARTICLE_MAX);
		page(page,queryWrapper);
		List<Article> articles = page.getRecords();
		List<HotArticle> hotArticles = BeanCopyUtil.copyBeanList(articles,HotArticle.class);
		return hotArticles;
	}



	@Override
	public PageVo articleList(Integer pageNum, Integer pageSize, Long categoryId) {
		//查询条件:状态发布，对isTop降序
		LambdaQueryWrapper<Article> lambdaQueryWrapper=wrapper();
		lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&& categoryId>0,Article::getCategoryId,categoryId);
		lambdaQueryWrapper.eq(Article::getStatus,SystemConstance.NORMAL_ARTICLE_STATUS);
		lambdaQueryWrapper.orderByDesc(Article::isTop);

		Page<Article> page=new Page<>(pageNum,pageSize);
		List<Article> articles = page(page, lambdaQueryWrapper).getRecords();

		articles=articles.stream()
				.map(article -> {
					Category category = categoryService.getById(article.getCategoryId());
					article.setCategoryName(category.getName());
					return article;
				}).collect(Collectors.toList());

		List<ArticleListVo> articleListVos=BeanCopyUtil.copyBeanList(articles, ArticleListVo.class);

		PageVo pageVo = new PageVo(articleListVos,page.getTotal());
		return pageVo;
	}


	private static LambdaQueryWrapper<Article> wrapper(){
		return new LambdaQueryWrapper<>();
	}


}
