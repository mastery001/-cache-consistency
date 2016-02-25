package org.cache.support;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.cache.TopicPublisher;
import org.cache.config.RedisTopicConfig;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis消息发布者
 * 
 * @author zouziwen
 *
 *         2016年2月25日 下午5:28:47
 */
class RedisTopicPublisher<K, V> implements TopicPublisher<K, V> {

	private final RedisTemplate<String, Object> redisTemplate;

	private final String topicName;

	private Executor executor = Executors.newCachedThreadPool();

	public RedisTopicPublisher() {
		redisTemplate = RedisTopicConfig.getRedisTemplate();
		topicName = RedisTopicConfig.getTopicName();
	}

	@Override
	public void publish(final Entry<K, V> value) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				redisTemplate.convertAndSend(topicName, value);
			}

		});

	}

}
