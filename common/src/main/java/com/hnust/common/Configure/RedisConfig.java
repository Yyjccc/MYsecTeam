package com.hnust.common.Configure;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	//配置redis使用fastjson
	@Bean
	public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object,Object> template=new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		FastJsonRedisSerializer serializer=new FastJsonRedisSerializer<>(Object.class);

		//使用StringRedisSerializer序列化和反序列化key值
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		//Hash的key也使用StringRedisSerializer
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}
}
