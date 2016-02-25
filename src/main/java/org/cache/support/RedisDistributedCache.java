package org.cache.support;

import org.cache.AbstractDistributedCache;
import org.cache.DistributedCache;

/**
 * 基于redis的分布式缓存
 * 
 * @author zouziwen
 *
 *         2016年2月25日 下午5:16:35
 */
public class RedisDistributedCache<K, V> extends AbstractDistributedCache<K, V> implements DistributedCache<K, V> {

	@SuppressWarnings("unused")
	private final RedisTopicSubscribe<K, V> topicSubscribe;

	public RedisDistributedCache() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public RedisDistributedCache(int initialCapacity) {
		super(initialCapacity);
		topicPublisher = new RedisTopicPublisher<K , V>();
		topicSubscribe = new RedisTopicSubscribe<K, V>(this);
	}

}
