package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.ArticleTag;
import com.hnust.myblog.Mode.Base.Tag;
import com.hnust.myblog.Mode.Mapper.ArticleTagMapper;
import com.hnust.myblog.Mode.Mapper.TagMapper;
import com.hnust.myblog.Service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
	@Autowired
	private ArticleTagMapper articleTagMapper;

	@Override
	public List<ArticleTag> listByArticle(Long id) {
		LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ArticleTag::getArticleId,id);
	    return  articleTagMapper.selectList(wrapper);
	}

	@Override
	public String getNameById(Long tagId) {
		Tag tag = getById(tagId);
		return tag.getTagName();
	}

	@Override
	public Long getIdByName(String s) {
		LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Tag::getTagName,s);
		Tag tag = getOne(wrapper);
		return tag.getId();
	}

	@Override
	public void addArticleTag(Long tagId, Long articleId) {
		ArticleTag articleTag = new ArticleTag(tagId, articleId);
		articleTagMapper.insert(articleTag);
	}

	@Override
	public List<String> getTagNameList(Long articleId) {
		LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(ArticleTag::getArticleId,articleId);
		List<ArticleTag> list = articleTagMapper.selectList(wrapper);
		return list.stream().map((articleTag) -> {
			Tag tag=getById(articleTag.getTagId());
			return tag.getTagName();
		}).collect(Collectors.toList());
	}
}
