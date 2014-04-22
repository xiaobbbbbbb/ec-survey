//package com.ecarinfo.survey.aop;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import com.ecarinfo.survey.cache.redis.RedisCache;
//
//@Aspect
//@Component
//public class CacheAspect {
//	private static final Logger logger = Logger.getLogger(CacheAspect.class
//			.getName());
//	@Resource
//	private RedisCache redisCache;
//
//	@Around("execution (* com.ecarinfo.*.*dao.*.update*(..)) or execution (* com.ecarinfo.*.*dao.*.delete*(..))")
//	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		 long time = System.currentTimeMillis();
//		Object obj = joinPoint.proceed();
//		 StringBuffer buffer = new StringBuffer();
//		 buffer.append(joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName());
//		Object[] models = joinPoint.getArgs();// 取得方法参数
//		if (models != null) {
//			if (models.length == 1) {
//				buffer.append(models[0].getClass().getSimpleName());
//				removeFromCache(models[0].getClass());
//			} else if (models.length == 2) {
//				buffer.append(models[1].getClass().getSimpleName());
//				removeFromCache(models[1].getClass());
//			}
//		}
//		 buffer.append("--costTime:"+(System.currentTimeMillis() - time));
//		 logger.warn(buffer.toString());
//		 buffer.setLength(0);
//		 buffer = null;
//		return obj;
//	}
//
//	private void removeFromCache(Class<?> entityClass) {
////		if (entityClass == null) {
////			return;
////		}
////		try {
////			
////			redisCache.removeCache(entityClass);
////		} catch (Exception e) {
////			logger.error(entityClass.getName() + "删除缓存失败", e);
////		}
//
//	}
//
//}