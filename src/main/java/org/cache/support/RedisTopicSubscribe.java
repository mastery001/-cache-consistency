package org.cache.support;

import org.cache.DistributedCache;
import org.cache.TopicPublisher.Entry;
import org.cache.TopicSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * redis的订阅者的处理逻辑
 * 
 * @author mastery
 * @time 2016年2月25日下午9:39:12
 * @param <K>
 * @param <V>
 */
class RedisTopicSubscribe<K, V> extends TopicSubscribe<K, V> {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public RedisTopicSubscribe(DistributedCache<K, V> cache) {
		super(cache);
	}

	public void handleMessage(Entry<K , V> entry) {
		logger.info("entry is {}" ,entry);
		// 更新缓存
		getCache().set0(entry.key(), entry.value());
	}

	@Override
	protected RedisDistributedCache<K, V> getCache() {
		return (RedisDistributedCache<K, V>) super.getCache();
	}

}
