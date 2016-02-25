package org.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Main {
	
	public static void main(String[] args) {
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		//map.put("1", 1234);
		Integer i = map.putIfAbsent("1", new Integer(1));
		System.out.println(i);
	}
}
