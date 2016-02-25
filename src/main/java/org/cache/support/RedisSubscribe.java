package org.cache.support;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisSubscribe implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] pattern) {
		
	}

}
