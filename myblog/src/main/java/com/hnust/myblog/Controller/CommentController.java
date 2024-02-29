package com.hnust.myblog.Controller;

import com.hnust.myblog.Mode.Base.Comment;
import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.Vo.PageVo;
import com.hnust.myblog.Mode.enums.HttpCode;
import com.hnust.myblog.Service.interfaces.CommentService;
import com.hnust.myblog.Service.utils.CommServiceClient;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Tag(name = "评论控制器",description = "评论相关接口")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@Autowired
	private CommServiceClient client;

	@GetMapping("/commentList")
	public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
		PageVo comments= commentService.commentList(articleId,pageNum,pageSize);
		return ResponseResult.response(comments);
	}

	@GetMapping("/linkCommentList")
	public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
		PageVo comments=commentService.linkCommentList(pageNum,pageSize);
		return ResponseResult.response(comments);

	}


	//后面可以自建Filter处理token，错误处理进一步详细
	@PostMapping("/add")
	public ResponseResult addComment(@RequestBody Comment comment,@RequestHeader("token") String token){
		String claims =client.decode(token).getData();
		if(claims==null){
			return ResponseResult.error(HttpCode.NO_AUTH);
		}
		commentService.addComment(comment,claims);

		return ResponseResult.response();
	}
}
