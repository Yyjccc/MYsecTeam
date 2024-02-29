//package com.hnust.myblog.Other.job;
//
//import com.hnust.myblog.Mode.Base.Article;
//import com.hnust.myblog.Service.interfaces.ArticleService;
//import com.hnust.myblog.Service.utils.RedisCache;
//import com.hnust.myblog.Service.utils.SystemConstance;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class UpdateViewCountJob {
//
//	@Autowired
//	private RedisCache redisCache;
//
//	@Autowired
//	private ArticleService articleService;
//	@Scheduled(cron = "0 0 * * * *")
//	public void updateViewCount(){
//		Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstance.REDIS_KEY_ARTICLE_VIEW);
//		List<Article> articles = cacheMap.entrySet().stream()
//				.map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//				.collect(Collectors.toList());
//				articleService.updateBatchById(articles);
//				log.info("redis缓存数据存入数据库");
//	}
//}
