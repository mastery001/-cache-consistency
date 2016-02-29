package org.cache.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.cache.TopicPublisher;
import org.cache.redis.config.RedisTopicConfig;
import org.cache.util.NamePreservingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * redis消息发布者
 * 
 * @author zouziwen
 *
 *         2016年2月25日 下午5:28:47
 */
class RedisTopicPublisher<K, V> implements TopicPublisher<K, V> {

	private final RedisTemplate<String, Object> redisTemplate;
	
	private final ValueOperations<String, Object> valOps;

	private final String topicName;
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	private final String threadName = RedisTopicPublisher.class.getSimpleName() + "-1";
	
	private static final String SEPARATE_UNDERLINE = "_";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * redis key 的前缀
	 * 2016年2月29日 上午11:36:43
	 */
	private final String key_prefix;

	public RedisTopicPublisher() {
		redisTemplate = RedisTopicConfig.getRedisTemplate();
		valOps = redisTemplate.opsForValue();
		topicName = RedisTopicConfig.getTopic().getTopic();
		key_prefix = topicName + SEPARATE_UNDERLINE;
	}

	@Override
	public void publish(final Entry<K, V> value) {
		executor.execute(new NamePreservingRunnable(new Runnable(){

			@Override
			public void run() {
				logger.info("publish message is {} , and connection status is {}" , value , !redisTemplate.getConnectionFactory().getConnection().isClosed());
				redisTemplate.convertAndSend(topicName, value);
				valOps.set(key_prefix + value.key(), value);
			}
			
		} , threadName));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entry<K, V>> topics() {
		Set<String> keys = valOps.getOperations().keys(key_prefix + "*");
		List<Entry<K, V>> list = new ArrayList<Entry<K, V>>(keys.size());
		for(Iterator<String> it = keys.iterator(); it.hasNext();) {
			list.add((Entry<K, V>) valOps.get(it.next()));
		}
		return list;
	}

}
