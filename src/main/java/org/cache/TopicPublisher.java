package org.cache;

/**
 * 消息发布者
 * @author zouziwen
 *
 * 2016年2月25日 下午5:19:56
 */
public interface TopicPublisher<V> {
	
	/**
	 * 发布消息自动通知各个业务方
	 * @param value
	 * 2016年2月25日 下午5:23:53
	 */
	void publish(V value);
}
