package org.cache;

/**
 * 分布式缓存
 * 	读写是安全的
 * @author zouziwen
 *
 * 2016年2月25日 下午4:18:35
 * @since 0.0.1
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
	 * 同步远程缓存至本地
	 * @return
	 * 2016年2月29日 上午11:09:14
	 */
	boolean synchronize();
	
	/**
	 * 判断键是否存在
	 * @param key
	 * @return
	 * 2016年2月25日 下午4:33:12
	 */
	boolean containsKey(K key);
	
}
