package org.cache;

import org.cache.support.RedisDistributedCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(ac);
		//System.out.println(ac.getBean(RedisMessageListenerContainer.class));
		DistributedCache<String, Integer> cache = new RedisDistributedCache<String, Integer>();
		cache.set("1", 1);
	}
}
