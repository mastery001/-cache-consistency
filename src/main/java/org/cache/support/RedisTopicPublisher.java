package org.cache.support;

import org.cache.TopicPublisher;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis消息发布者
 * @author zouziwen
 *
 * 2016年2月25日 下午5:28:47
 */
class RedisTopicPublisher<V> implements TopicPublisher<V>{

	private final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	
	@Override
	public void publish(V value) {
		redisTemplate.convertAndSend("", value);
	}
	

}
