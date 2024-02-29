package com.hnust.myblog.Service.utils;

public abstract class SystemConstance {
	public static final int HOT_ARTICLE_MAX=10;

	public static final int NORMAL_ARTICLE_STATUS=1;

	/**
	 * 友链审核通过的状态
	 */
	public static final int LINK_NORMAL_STATUS=1;

	/**
	 * 文章根评论id值
	 */
	public static final Long ROOT_COMMENT_ID=-1L;
	/**
	 * 不是回复评论
	 */

	public static final Long NO_TO_COMMENT_ID=-1L;

	/**
	 * 友链评论类型
	 */
	public static final String FRIEND_LINK_COMMENT_TYPE="1";

	/**
	 * 微服务模块名称
	 */
	public static final String MODULE_NAME="blog";

	/**
	 * 文章浏览量数据放入redis中的key名称
	 */
	public static final String REDIS_KEY_ARTICLE_VIEW="blog_article_viewCount";


	public static final String MENU="C";

	public static final String BUTTON="B";
}
