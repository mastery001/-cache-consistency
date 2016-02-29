package org.cache;

import org.cache.TopicPublisher.Entry;
import org.cache.config.RedisTopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * 消息消费者
 * @author zouziwen
 *
 * 2016年2月26日 下午4:59:24
 */
public abstract class TopicSubscribe<K , V> extends MessageListenerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 维持redis的本地缓存
	 */
	private final DistributedCache<K, V> cache;
	
	public TopicSubscribe(DistributedCache<K, V> cache) {
		this.cache = cache;
		init();
	}
	
	protected void init() {
		setSerializer(new JdkSerializationRedisSerializer());
		afterPropertiesSet();
		RedisTopicConfig.getRedisMessageContainer().addMessageListener(this,RedisTopicConfig.getTopic());
		// 当消费者创建便自动将缓存同步
		try {
			cache.synchronize();
		} catch (Exception e) {
			logger.info("cache synchronized failed, exception is {}" , e);
		}
	}

	/**
	 * 处理redis publish的信息
	 * @param entry
	 * 2016年2月26日 下午5:05:13
	 */
	public abstract void handleMessage(Entry<K , V> entry);
	
	@Override
	protected void handleListenerException(Throwable ex) {
		logger.info("subscribe error ; exception is {}" , ex);
	}
	
	protected DistributedCache<K , V> getCache() {
		return cache;
	}
}
