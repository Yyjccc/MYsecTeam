package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
	private Long id;

	private Long articleId;

	private Long rootId;

	private String content;

	private Long toCommentUserId;

	private String toCommentUserName;

	private Long toCommentId;

	private Long createBy;

	private Date createTime;

	private String userName;

	private List<CommentVo> children;
}
