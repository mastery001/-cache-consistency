package org.cache.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisTopicConfig implements ApplicationContextAware{

	private static String topicName;

	private static RedisConnectionFactory connectionFactory;
	
	private static RedisTemplate<String, Object> redisTemplate;
	
	private static ApplicationContext applicationContext;

	public RedisTopicConfig(String topicName, RedisConnectionFactory connectionFactory) {
		RedisTopicConfig.topicName = topicName;
		RedisTopicConfig.connectionFactory = connectionFactory;
		redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.afterPropertiesSet();
	}

	public static RedisMessageListenerContainer getRedisMessageContainer() {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		ThreadPoolTaskScheduler execute = new ThreadPoolTaskScheduler();
//		execute.setPoolSize(3);
//		container.setTaskExecutor(execute);
//		return container;
		return applicationContext.getBean(RedisMessageListenerContainer.class);
	}

	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public static String getTopicName() {
		return topicName;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		RedisTopicConfig.applicationContext = applicationContext;
	}

	public static RedisConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
}
