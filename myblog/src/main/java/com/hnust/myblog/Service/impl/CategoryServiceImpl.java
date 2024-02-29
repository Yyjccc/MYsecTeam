package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Mapper.CategoryMapper;
import com.hnust.myblog.Mode.Vo.CategoryList;
import com.hnust.myblog.Service.interfaces.ArticleService;
import com.hnust.myblog.Service.interfaces.CategoryService;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	@Autowired
	@Lazy
	private ArticleService articleService;


	@Override
	public List<CategoryList> getCategoryList() {
		List<Category> list = list();
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Article::getStatus, SystemConstance.NORMAL_ARTICLE_STATUS);
		List<Article> articles = articleService.list(wrapper);
		Set<Long> categoryId=new HashSet<>();
		ArrayList<CategoryList> result = new ArrayList<>();
		for (Article article : articles) {
			Long id = article.getCategoryId();
			if(!categoryId.contains(id)){
				categoryId.add(id);
				Category category = getBaseMapper().selectById(id);
				result.add(new CategoryList(category));
			}
		}
		return result;
	}

	@Override
	public List<Category> countById(Long id) {
		LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Category::getCreateBy,id);
		wrapper.select(Category::getId,Category::getCategoryName);
		return list(wrapper);
	}
}
