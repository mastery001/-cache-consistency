package org.cache.config;

import java.util.concurrent.Executor;

import org.cache.config.spring.SpringBeanManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public final class RedisTopicConfig implements ApplicationContextAware , InitializingBean {

	private static Topic topic;

	private static RedisConnectionFactory connectionFactory;

	private static RedisTemplate<String, Object> redisTemplate;

	private static SpringBeanManager beanManager;

	private static RedisMessageListenerContainer container;

	public RedisTopicConfig(String topicName, RedisConnectionFactory connectionFactory) {
		topic = new ChannelTopic(topicName);
		RedisTopicConfig.connectionFactory = connectionFactory;
	}

	public static RedisMessageListenerContainer getRedisMessageContainer() {
		return container;
	}

	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public static Topic getTopic() {
		return topic;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		beanManager = new SpringBeanManager(applicationContext);
	}

	public static RedisConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	private static class AutoConfigBean {

		public static void config() {
			Executor taskExecutor = registerTaskExecutor();
			registerRedisMessageListenerContainer(taskExecutor);
			redisTemplate = registerRedisTemplate();
		}

		private static Executor registerTaskExecutor() {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(ThreadPoolTaskScheduler.class);
			beanDefinition.getPropertyValues().add("poolSize", 3);
			return (Executor) beanManager.registerBean(beanDefinition);
		}
		
		private static RedisMessageListenerContainer registerRedisMessageListenerContainer(Executor taskExecutor) {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisMessageListenerContainer.class);
			beanDefinition.setDestroyMethodName("destroy");
			beanDefinition.getPropertyValues().add("connectionFactory", connectionFactory);
			beanDefinition.getPropertyValues().add("taskExecutor", taskExecutor);
			container = (RedisMessageListenerContainer) beanManager.registerBean(beanDefinition);
			container.start();
			return container;
		}
		
		@SuppressWarnings("unchecked")
		private static RedisTemplate<String, Object> registerRedisTemplate() {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RedisTemplate.class);
			beanDefinition.getPropertyValues().add("connectionFactory", connectionFactory);
			return (RedisTemplate<String, Object>) beanManager.registerBean(beanDefinition);
		
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AutoConfigBean.config();
	}
}
