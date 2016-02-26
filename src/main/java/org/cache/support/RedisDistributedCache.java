package org.cache.support;

import org.cache.AbstractDistributedCache;
import org.cache.DistributedCache;
import org.cache.TopicPublisher;
import org.cache.TopicSubscribe;

/**
 * 基于redis的分布式缓存
 * 
 * @author zouziwen
 *
 *         2016年2月25日 下午5:16:35
 */
public class RedisDistributedCache<K, V> extends AbstractDistributedCache<K, V> implements DistributedCache<K, V> {

	//private final RedisTopicSubscribe<K, V> topicSubscribe;

	public RedisDistributedCache() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public RedisDistributedCache(int initialCapacity) {
		super(initialCapacity);
	}

	@Override
	protected TopicPublisher<K, V> getTopicPublisher() {
		return new RedisTopicPublisher<K , V>();
	}

	@Override
	protected TopicSubscribe<K, V> getTopicSubscribe() {
		return new RedisTopicSubscribe<K, V>(this);
	}

}
