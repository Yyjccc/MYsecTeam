package com.hnust.myblog.Mode.Base;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
@Schema(description = "评论模型")
public class Comment {
	private Long id;

	@Schema(description = "评论类型")
	private String type;

	@Schema(description = "文章id")
	private Long articleId;

	private Long rootId;

	private String content;

	private Long toCommentUserId;


	private Long createBy;

	private Date createTime;

	private Date updateTime;
}
