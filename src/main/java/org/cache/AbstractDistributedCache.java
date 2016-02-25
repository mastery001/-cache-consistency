package org.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.cache.TopicPublisher.Entry;

public abstract class AbstractDistributedCache<K, V> implements DistributedCache<K, V> {

	protected class CacheObject<K2, V2> {
		
		/**
		 * 使用读写锁来保证安全
		 */
		private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
		private final Lock readLock = cacheLock.readLock();
		private final Lock writeLock = cacheLock.writeLock();
		
		public CacheObject(K2 key) {
			this.key = key;
		}

		public final K2 key;
		private V2 cacheObject;

		public V2 set(V2 value) {
			writeLock.lock();
			try {
				if(cacheObject != value) {
					this.cacheObject = value;
				}
				return cacheObject;
			} finally {
				writeLock.unlock();
			}
		}

		public V2 getObject() {
			readLock.lock();
			try {
				return cacheObject;
			} finally {
				readLock.unlock();
			}
		}

		@Override
		public String toString() {
			return "CacheObject [key=" + key + ", cacheObject=" + cacheObject + "]";
		}

	}

	protected final ConcurrentMap<K, CacheObject<K, V>> cacheMap;
	
	protected TopicPublisher<K , V> topicPublisher;

	protected int cacheSize; // 缓存大小， 0 - 无限制

	protected static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

	public AbstractDistributedCache() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * init the cache size
	 * 
	 * @param initialCapacity
	 *            2016年2月25日 下午4:38:25
	 */
	public AbstractDistributedCache(int initialCapacity) {
		if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
		this.cacheSize = initialCapacity;
		cacheMap = new ConcurrentHashMap<K, CacheObject<K, V>>(initialCapacity);
	}

	@Override
	public int size() {
		return cacheMap.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public V set(K key, V value) {
		V retVal = set0(key, value);
		topicPublisher.publish(new Entry<K , V>(key , value));
		return retVal;
	}
	
	/**
	 * 只做添加操作而不发布消息至redis队列
	 * @time 2016年2月25日下午10:41:07
	 * @param key
	 * @param value
	 * @return
	 */
	public V set0(K key, V value) {
		CacheObject<K,V> co = new CacheObject<K,V>(key);
		CacheObject<K,V> oldCo = cacheMap.putIfAbsent(key, co);
		V retVal ;
		// 如果是之前值不存在
		if(oldCo == null) {
			retVal = co.set(value);
		}else {
			retVal = oldCo.set(value);
		}
		return retVal;
	}

	@Override
	public V get(K key) {
		return cacheMap.get(key).getObject();
	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

}
