package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.Comment;
import com.hnust.myblog.Mode.Vo.PageVo;

public interface CommentService extends IService<Comment> {
	PageVo commentList(Long articleId, Integer pageNum, Integer pageSize);

	void addComment(Comment comment, String id);

	PageVo linkCommentList(Integer pageNum, Integer pageSize);

	Integer countById(String id);
}
