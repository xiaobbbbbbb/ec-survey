package com.ecarinfo.survey.cache.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.ecarinfo.common.utils.PropUtil;

public class Redis {
	private static final Logger logger = Logger.getLogger(Redis.class);
	private static final JedisPool pool;// = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
	private static boolean isEnable = true;
	public static final int DEFAULS_EXPIRE = Integer.parseInt(PropUtil.getProp("redis.properties", "redis.default.expire"));
	private static final String prefix = PropUtil.getProp("redis.properties", "redis.prefix");
	static {  
		Properties prop = PropUtil.getProperties("redis.properties");
	    if (prop == null) {  
	        throw new IllegalArgumentException("[redis.properties] is not found!");  
	    }  
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(Integer.valueOf(prop.getProperty("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(prop.getProperty("redis.pool.maxIdle")));
		config.setMaxWait(Long.valueOf(prop.getProperty("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(prop.getProperty("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(prop.getProperty("redis.pool.testOnReturn")));
		pool = new JedisPool(config, prop.getProperty("redis.ip"),Integer.valueOf(prop.getProperty("redis.port")));
		//just for test
		try {
			Jedis redis = pool.getResource();
			release(redis);
		} catch (Exception e) {
			isEnable = false;
		}
	}  
	
	/**
	 * 校验Redis是否有效
	 */
	public static final void validateRedis() {
		try {
			Jedis redis = pool.getResource();
			isEnable = true;
			release(redis);
		} catch (Exception e) {
			isEnable = false;
		}
	}
	
	/**
	 * 获取池对象
	 * @return
	 */
	public static Jedis getRedis() {
		try {
			if(isEnable) {
				Jedis redis = pool.getResource();
				isEnable = true;
				return redis;
			}
		} catch (Exception e) {
			logger.warn("获取 redis 异常。");
			isEnable = false;
		}
		return null;
	}
	
	/**
	 * 释放对象池  
	 * @param jedis
	 */
	public static void release(Jedis jedis) {
		pool.returnResource(jedis);
	}

	public static final Long hset(String mapName,String mapKey,String mapValue,int ... expire) {
		if(!isEnable) {
			return -1l;
		}
		try {
			Jedis redis = getRedis();
			mapName = prefix+mapName;
			if(expire.length == 0) {
				redis.expire(mapName,DEFAULS_EXPIRE);
			}
			//设置失效时间
			if(expire.length > 0 && expire[0] >0) {
				redis.expire(mapName,expire[0]);
			}
			Long res = redis.hset(mapName, mapKey, mapValue);
			logger.info("add cache:[mapName="+mapName+",mapKey="+mapKey+",mapValue="+mapValue+"].");
			release(redis);
			return res;
		} catch (Exception e) {
			logger.warn("redis 异常。");
			return -1l;
		}
	}
	
	public static final int hdel(String mapName,String mapKey) {
		if(!isEnable) {
			return -1;
		}
		try {
			Jedis redis = getRedis();
			mapName = prefix+mapName;
			Long res = redis.hdel(mapName, mapKey);
			logger.info("delete cache:[mapName="+mapName+",mapKey="+mapKey+"].");
			release(redis);
			return res.intValue();
		} catch (Exception e) {
			logger.warn("redis 异常。");
			return -1;
		}
	}
	
	public static final String hget(String mapName,String mapKey) {
		if(!isEnable) {
			return null;
		}
		try {
			Jedis redis = getRedis();
			mapName = prefix+mapName;
			String mapValue = redis.hget(mapName, mapKey);
			logger.info("get From cache:[mapName="+mapName+",mapKey="+mapKey+"].");
			release(redis);
			return mapValue;
		} catch (Exception e) {
			logger.warn("redis 异常。");
			return null;
		}
	}
	
	public static final Map<String,String> hGetAll(String mapName) {
		if(!isEnable) {
			return new HashMap<String, String>();
		}
		try {
			Jedis redis = getRedis();
			mapName = prefix+mapName;
			Map<String,String> map = redis.hgetAll(mapName);
			logger.info("hGetAll From cache:[mapName="+mapName+"].");
			release(redis);
			return map;
		} catch (Exception e) {
			logger.warn("redis 异常。");
			return null;
		}
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++) {
			Jedis redis = getRedis();
			System.out.println(redis);
		}
	}
}
