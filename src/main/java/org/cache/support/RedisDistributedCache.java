package org.cache.support;

import org.cache.AbstractDistributedCache;
import org.cache.DistributedCache;
import org.cache.TopicPublisher;

/**
 * 基于redis的分布式缓存
 * @author zouziwen
 *
 * 2016年2月25日 下午5:16:35
 */
public class RedisDistributedCache<K, V> extends AbstractDistributedCache<K, V> implements DistributedCache<K, V>{

	private final TopicPublisher<V> topicPublisher;
	
	public RedisDistributedCache() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public RedisDistributedCache(int initialCapacity) {
		super(initialCapacity);
		topicPublisher = new RedisTopicPublisher<V>();
	}

	@Override
	public TopicPublisher<V> getPublisher() {
		return topicPublisher;
	}
	
	
}
