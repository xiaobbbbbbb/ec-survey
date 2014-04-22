//package com.ecarinfo.survey.cache.redis;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.jboss.netty.util.internal.ConcurrentHashMap;
//import org.springframework.stereotype.Component;
//
//import com.ecarinfo.common.utils.JSONUtil;
//import com.ecarinfo.persist.exdao.GenericDao;
//
//@Component("redisCache")
//public class RedisCache {
//	private static final Logger logger = Logger.getLogger(RedisCache.class.getSimpleName());
//	@Resource
//	private GenericDao genericDao;
//	
//	//记录曾经缓存过的类 Map<类,Map<entityKey,entityKey>>
//	private static final Map<Class<?>,Map<Object,Object>> cachedClassNameMap = new ConcurrentHashMap<Class<?>,Map<Object,Object>>();
////	private static final Map<Class<?>,String> cachedClassNameMapWithNoPersist = new ConcurrentHashMap<Class<?>,String>();
//	
//	/**
//	 * 更新缓存(主要是在入库的缓存)
//	 * @param key
//	 * @param cache
//	 */
//	public final <T extends Serializable> void updateCache(Object key,T cache,int ...expTime) {
//		if(key == null) {
//			logger.warn("findCache key is null");
//			return;
//		}
//		String cacheJson = JSONUtil.toJson(cache);
//		if(expTime.length == 0) {
//			Redis.hset(cache.getClass().getSimpleName(), cache.getClass().getSimpleName()+"-"+key, cacheJson, Redis.DEFAULS_EXPIRE);
//		} else {
//			Redis.hset(cache.getClass().getSimpleName(), cache.getClass().getSimpleName()+"-"+key, cacheJson, expTime[0]);
//		}
//	}
//	
//	/**
//	 * 根据主键查找对象,调用了此方法的实现，如果存在，会被自动缓存
//	 * @param key
//	 * @param clazz
//	 * @param fromDB　是否从ＤＢ中查找（当且仅当缓存中不存在时，才会从ＤＢ查找）
//	 * @return
//	 */
//	public final <T extends Serializable> T findCache(Object key,Class<T> clazz,boolean fromDB) {
//		if(key == null) {
//			logger.warn("findCache key is null");
//			return null;
//		}
//		String cacheJson = Redis.hget(clazz.getSimpleName(), clazz.getSimpleName()+"-"+key);
//		T t = null;
//		if(cacheJson != null) {
//			t = JSONUtil.fromJson(cacheJson, clazz);
//		}
//		if(t == null && fromDB) {
//			t = genericDao.findByPK(clazz, key);
//			if(t != null) {
//				saveCache(key,t);
//			}
//		}
//		return t;
//	}
//	
//	public final <T> Map<String,T> getAll(String mapName,Class<T> clazz) {
//		Map<String,String> cacheMap = Redis.hGetAll(mapName);
//		Map<String,T> resMap = new HashMap<String, T>();
//		for(Map.Entry<String,String> en:cacheMap.entrySet()) {
//			resMap.put(en.getKey(), JSONUtil.fromJson(en.getValue(),clazz));
//		}
//		return resMap;
//	}
//	
//	public final <T> void saveCache(Object key,T cache,int ...expTime) {
//		if(key == null) {
//			throw new RuntimeException("saveCache key is null");
//		}
//		String cacheJson = JSONUtil.toJson(cache);
//		if(expTime.length == 0) {
//			Redis.hset(cache.getClass().getSimpleName(), cache.getClass().getSimpleName()+"-"+key, cacheJson, Redis.DEFAULS_EXPIRE);
//		} else {
//			Redis.hset(cache.getClass().getSimpleName(), cache.getClass().getSimpleName()+"-"+key, cacheJson, expTime[0]);
//		}
//	}
//	
//	public final <T> void removeCache(Class<T> clazz,Object key) {
//		Redis.hdel(clazz.getSimpleName(), clazz.getSimpleName()+"-"+key);
//	}
//	
//}
