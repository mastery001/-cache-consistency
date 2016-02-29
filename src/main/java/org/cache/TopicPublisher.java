package org.cache;

import java.io.Serializable;
import java.util.List;

/**
 * 消息发布者
 * @author zouziwen
 *
 * 2016年2月25日 下午5:19:56
 */
public interface TopicPublisher<K , V> {
	
	/**
	 * 发布消息自动通知各个业务方
	 * @param value
	 * 2016年2月25日 下午5:23:53
	 */
	void publish(Entry<K , V> value);
	
	/**
	 * 获取所有已经发布至远端的Topic
	 * @return
	 * 2016年2月29日 上午11:19:14
	 */
	List<Entry<K , V>> topics();
	
	/**
	 * 发布的消息格式，键值对
	 * @author mastery
	 * @time 2016年2月25日下午9:36:45
	 * @param <K>
	 * @param <V>
	 */
	public class Entry<K , V> implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4020439916431982082L;

		private final K key;
		
		private final V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K key() {
			return key;
		}
		
		public V value() {
			return value;
		}

		@Override
		public String toString() {
			return "Entry [key=" + key + ", value=" + value + "]";
		}
		
	}
}
