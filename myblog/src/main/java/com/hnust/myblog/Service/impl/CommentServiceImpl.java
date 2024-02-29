package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Comment;
import com.hnust.myblog.Mode.Base.User;
import com.hnust.myblog.Mode.Mapper.CommentMapper;
import com.hnust.myblog.Mode.Vo.CommentVo;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Service.interfaces.CommentService;
import com.hnust.myblog.Service.interfaces.UserService;
import com.hnust.myblog.Service.utils.BeanCopyUtil;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Autowired
	private UserService userService;


	@Override
	public PageVo commentList(Long articleId, Integer pageNum, Integer pageSize) {
		//查询对应文章的根评论
		LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getArticleId,articleId);
		wrapper.eq(Comment::getRootId, SystemConstance.ROOT_COMMENT_ID);

		//分页查询
		Page<Comment> page=new Page<>(pageNum,pageSize);
		page(page, wrapper);

		//字段处理
		List<CommentVo> commentVos= toCommentVoList(page.getRecords());
		PageVo pageVo = new PageVo(commentVos, page.getTotal());
		return pageVo;
	}

	//可以实现mybatis-plus的某个接口来实现普遍字段的插入(自动填充)
	@Override
	public void addComment(Comment comment, String id) {
		comment.setCreateBy(Long.parseLong(id));
		Date time=new Date();
		comment.setCreateTime(time);
		comment.setUpdateTime(time);
		save(comment);
	}

	@Override
	public PageVo linkCommentList(Integer pageNum, Integer pageSize) {
		LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
		//先查根评论
		wrapper.eq(Comment::getType,SystemConstance.FRIEND_LINK_COMMENT_TYPE);
		wrapper.eq(Comment::getRootId,SystemConstance.ROOT_COMMENT_ID);
		Page<Comment> page = new Page<>(pageNum,pageSize);
		page(page,wrapper);
		List<CommentVo> voList = toCommentVoList(page.getRecords());
		PageVo pageVo = new PageVo(voList, page.getTotal());
		return pageVo;
	}

	@Override
	public Integer countById(String id) {
		LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getCreateBy,Long.parseLong(id));
		return (int) count(wrapper);
	}


	private List<CommentVo> toCommentVoList(List<Comment> list){
		List<CommentVo> commentVos=BeanCopyUtil.copyBeanList(list, CommentVo.class);

		for (CommentVo commentVo : commentVos) {
			User user = userService.getById(commentVo.getCreateBy());
			commentVo.setUserName(user.getNickName());
			//处理to_comment_id(回复其他评论的评论)
			Long rootId = commentVo.getRootId();
			if(rootId!=SystemConstance.NO_TO_COMMENT_ID){
				User toCommentUser = userService.getById(commentVo.getToCommentUserId());
				commentVo.setToCommentUserName(toCommentUser.getNickName());
			}
			setChildren(commentVo);
			//查询所有子评论

		}
		return commentVos;
	}

	private void setChildren(CommentVo commentVo){
		Long id = commentVo.getId();
		LambdaQueryWrapper<Comment> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getRootId,id);
		List<CommentVo> children = toCommentVoList(list(wrapper));
		if(!(children.isEmpty()||children==null)) {
			commentVo.setChildren(children);
		}
	}

}
