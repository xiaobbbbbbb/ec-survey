package com.ecarinfo.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

public class EntityUtil {
	private static final Logger logger = Logger.getLogger(EntityUtil.class);

	public static List<Object> getOneFieldValues(Collection<?> entitys, String fieldName) {
		List<Object> values = new ArrayList<Object>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (Object object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					values.add(field.get(object));
				}
			}
		} catch (Exception e) {
			logger.error("getOneFieldValues..", e);
		}
		return values;
	}

	public static <T> Map<Object, T> getField2EntityMap(List<T> entitys, String fieldName) {
		Map<Object, T> map = new HashMap<Object, T>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (T object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					map.put(field.get(object), object);
				}
			}
		} catch (Exception e) {
			logger.error("getOneFieldValues..", e);
		}
		return map;
	}
}
