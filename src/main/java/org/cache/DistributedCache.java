package org.cache;

/**
 * 分布式缓存
 * 	读写是安全的
 * @author zouziwen
 *
 * 2016年2月25日 下午4:18:35
 */
public interface DistributedCache<K , V>{

	int size();
	
	boolean isEmpty();
	
	/**
	 * set值 (不可重复写)
	 * @param key
	 * @param value
	 * @return	返回原先存在的值
	 * 2016年2月25日 下午4:28:38
	 */
	V set(K key , V value);
	
	
	/**
	 * 得到对应键的值(可重复读)
	 * @param key
	 * @return
	 * 2016年2月25日 下午4:33:00
	 */
	V get(K key);
	
	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 * 2016年2月25日 下午4:33:12
	 */
	boolean containsKey(K key);
	
	/**
	 * 获得消息发布者
	 * @return
	 * 2016年2月25日 下午5:25:08
	 */
	TopicPublisher<V> getPublisher();
	
	
}
