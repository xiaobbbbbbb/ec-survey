package com.ecarinfo.survey.cache;
/**
 * 在线设备管理接口
 */
import java.util.Map;
import java.util.Set;
public interface OnlineManager <K,V> {

	/**
	 * 通过Key得到当前的信息
	 * 
	 * @param imei
	 * @return
	 */
	V get(K key);

	/**
	 * 得到所有在线的
	 * 
	 * @return
	 */
	Map<K,V> getAll();
	
	/**得到所有线的ID列表
	 * @return
	 */
	Set<K> getAllKeys();

	/**
	 * 增加在线
	 * @param key
	 * @param param
	 */
	void add(K key, V value);
	
	/**
	 * 删除在线
	 * @param key
	 */
	void remove(K key);

}