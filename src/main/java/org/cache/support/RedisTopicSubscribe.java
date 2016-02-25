package org.cache.support;

import org.cache.TopicPublisher.Entry;
import org.cache.config.RedisTopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis的订阅者的处理逻辑
 * 
 * @author mastery
 * @time 2016年2月25日下午9:39:12
 * @param <K>
 * @param <V>
 */
public class RedisTopicSubscribe<K, V> implements MessageListener {

	/**
	 * 维持redis的本地缓存
	 */
	private final RedisDistributedCache<K, V> cache;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public RedisTopicSubscribe(RedisDistributedCache<K, V> cache) {
		this.cache = cache;
		RedisTopicConfig.getRedisMessageContainer().addMessageListener(this,
				new ChannelTopic(RedisTopicConfig.getTopicName()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			// 得到消息体
			Entry<K, V> entry = (Entry<K, V>) RedisTopicConfig.getRedisTemplate().getValueSerializer()
					.deserialize(message.getBody());
			System.out.println(entry);
			// 更新缓存
			cache.set0(entry.key(), entry.value());
		} catch (SerializationException e) {
			logger.info("deserialize " + message + "error ; exception is {}", e);
		}
	}

}
