package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.ArticleTag;
import com.hnust.myblog.Mode.Base.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
	List<ArticleTag> listByArticle(Long id);

	String getNameById(Long tagId);

	Long getIdByName(String s);

	void addArticleTag(Long tagId, Long articleId);

	List<String> getTagNameList(Long articleId);
}
