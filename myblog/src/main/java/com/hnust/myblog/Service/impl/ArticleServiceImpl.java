package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.ArticleTag;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Mapper.ArticleMapper;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Vo.*;
import com.hnust.myblog.Mode.Vo.admin.TagVo;
import com.hnust.myblog.Service.interfaces.ArticleService;
import com.hnust.myblog.Service.interfaces.CategoryService;
import com.hnust.myblog.Service.interfaces.TagService;
import com.hnust.myblog.Service.utils.BeanCopyUtil;
import com.hnust.myblog.Service.utils.RedisCache;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private TagService tagService;

	@Autowired
	private CommonService commonService;

	@Override
	public List<HotArticle> hotArticleList() {
		LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
		//1.正式文章。
		queryWrapper.eq(Article::getStatus,SystemConstance.NORMAL_ARTICLE_STATUS);
		// 2.按照浏览量排序。
		queryWrapper.orderByDesc(Article::getViewCount);
		queryWrapper.select(Article::getId,Article::getArticleTitle,Article::getViewCount);
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
		lambdaQueryWrapper.orderByDesc(Article::getIsTop);

		Page<Article> page=new Page<>(pageNum,pageSize);
		List<Article> articles = page(page, lambdaQueryWrapper).getRecords();

		articles=articles.stream()
				.map(article -> {
					Category category = categoryService.getById(article.getCategoryId());
					if(category!=null){
						article.setCategoryName(category.getCategoryName());
					}
					return article;
				}).collect(Collectors.toList());

		List<ArticleListVo> articleListVos=BeanCopyUtil.copyBeanList(articles, ArticleListVo.class);

		PageVo pageVo = new PageVo(articleListVos,page.getTotal());
		return pageVo;
	}

	@Override
	public ArticleDetailVo getArticleDetail(Long id) {
		ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(getById(id), ArticleDetailVo.class);
		Category category = categoryService.getById(articleDetailVo.getCategoryId());
		if(category!=null){
			articleDetailVo.setCategoryName(category.getCategoryName());
		}
		return articleDetailVo;
	}

	@Override
	public void updateViewCount(Long id) {
		//更新redis数据
		redisCache.incrementCacheMapValue(SystemConstance.REDIS_KEY_ARTICLE_VIEW,id.toString(),1);
	}

	@Override
	public Integer viewCount(String id) {
		LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
		//查询用户写的文章
		Long count=0L;
		wrapper.select(Article::getViewCount);
		wrapper.eq(Article::getUserId,Long.parseLong(id));
		List<Article> list = list(wrapper);
		for (Article article : list) {
			count+=article.getViewCount();
		}
		return count.intValue();
	}

	@Override
	public Integer countById(String id) {
		LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Article::getUserId,Long.parseLong(id));
		return (int) count(wrapper);
	}

	@Override
	public Integer countByCategory(Long id) {
		return (int) count(	wrapper().eq(Article::getCategoryId,id));
	}

	@Override
	public List<Article> topViewCount(Long id, int i) {
		LambdaQueryWrapper<Article> wrapper = wrapper();
		wrapper.eq(Article::getUserId,id);
		wrapper.select(Article::getArticleTitle,Article::getViewCount);
		wrapper.last("LIMIT "+i);
		return list(wrapper);
	}

	@Override
	public List<Article> getListByUserId(String id) {
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Article::getUserId,Long.parseLong(id));
		return list(wrapper);
	}

	@Override
	public void setTagList(Article article) {
		if(article==null){
			throw new RuntimeException("article is null");
		}
		List<ArticleTag> tagsId= tagService.listByArticle(article.getId());
		ArrayList<TagVo> result = new ArrayList<>();
		if(!tagsId.isEmpty()){
			for (ArticleTag articleTag : tagsId) {
				String tagName=tagService.getNameById(articleTag.getTagId());
				result.add(new TagVo(articleTag.getTagId(),tagName));
			}
		}
		article.setTagVOList(result);
	}

	@Override
	public Article getAdminArticle(Long articleId) {
		Article article=getById(articleId);
		List<String> tagNameList=tagService.getTagNameList(articleId);
		article.setTagNameList(tagNameList);
		return article;
	}

	@Override
	public void updateImg(String id, StaticFile staticFile) {
		Article article = getById(id);
		if(article==null||staticFile==null){
			throw new RuntimeException(article==null?"id错误":"文件上传服务错误");
		}
		article.setArticleCover(commonService.getFileSrc(staticFile));
		updateById(article);
	}

	@Override
	public void updateArticle(Article article) {
		List<String> addTag = findAddTag(article.getTagNameList(), article.getId());
		for (String s : addTag) {
			tagService.addArticleTag(tagService.getIdByName(s),article.getId());
		}
		updateById(article);
	}


	private static LambdaQueryWrapper<Article> wrapper(){
		return new LambdaQueryWrapper<>();
	}

	private List<String> findAddTag(List<String> tags,Long articleId){
		List<String> list =tags;
		List<String> tagNameList = tagService.getTagNameList(articleId);
		for (String s : tagNameList) {
			if(list.contains(s)){
				list.remove(s);
			}
		}
		return list;
	}


}
