package com.ecarinfo.survey.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component("ecOnlineManager")
public class EcOnlineManager<K,V> implements OnlineManager<K,V> {
	private Map<K,V> onlines = new ConcurrentHashMap<K, V>();
	@Override
	public V get(K key) {
		return onlines.get(key);
	}

	@Override
	public Map<K, V> getAll() {
		return onlines;
	}

	@Override
	public Set<K> getAllKeys() {
		return onlines.keySet();
	}

	@Override
	public void add(K key, V value) {
		onlines.put(key, value);
	}

	@Override
	public void remove(K key) {
		onlines.remove(key);
	}

}
