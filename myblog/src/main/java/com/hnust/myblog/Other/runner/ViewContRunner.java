//package com.hnust.myblog.Other.runner;
//
//import com.hnust.myblog.Mode.Base.Article;
//import com.hnust.myblog.Mode.Mapper.ArticleMapper;
//import com.hnust.myblog.Service.utils.RedisCache;
//import com.hnust.myblog.Service.utils.SystemConstance;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class ViewContRunner implements CommandLineRunner {
//	@Autowired
//	private ArticleMapper articleMapper;
//
//	@Autowired
//	private RedisCache redisCache;
//
//	//Springboot启动时候调用代码：将部分数据库中部分数据写入redis
//	@Override
//	public void run(String... args) throws Exception {
//		//查询信息id,viewCount
//		List<Article> articles = articleMapper.selectList(null);
//		Map<String, Integer> viewCountMap = articles.stream()
//				.collect(Collectors.toMap(article -> article.getId().toString(), article -> {
//					return article.getViewCount().intValue();
//				}));
//		redisCache.setCacheMap(SystemConstance.REDIS_KEY_ARTICLE_VIEW,viewCountMap);
//		log.info("数据库数据缓存到redis");
//	}
//}
