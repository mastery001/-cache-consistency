package org.cache.support;

import org.cache.TopicPublisher;
import org.cache.config.RedisTopicConfig;
import org.slf4j.LoggerFactory;
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

	public RedisTopicPublisher() {
		redisTemplate = RedisTopicConfig.getRedisTemplate();
		topicName = RedisTopicConfig.getTopic().getTopic();
	}

	@Override
	public void publish(final Entry<K, V> value) {
		LoggerFactory.getLogger(getClass()).info("publish message is {} , and connection status is {}" , value , !redisTemplate.getConnectionFactory().getConnection().isClosed());
		redisTemplate.convertAndSend(topicName, value);
	}

}
