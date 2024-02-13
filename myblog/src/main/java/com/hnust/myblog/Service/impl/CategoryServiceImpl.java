package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Mapper.CategoryMapper;
import com.hnust.myblog.Mode.Vo.CategoryList;
import com.hnust.myblog.Service.ArticleService;
import com.hnust.myblog.Service.CategoryService;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
