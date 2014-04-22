package com.ecarinfo.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * EC项目EL自定义表达式
 */
public class EcELUtil {
	private static final Logger logger = Logger.getLogger(EcELUtil.class);

	public static String decode(String str) {
		try {
			if (StringUtils.isNotBlank(str)) {
				return new String(str.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (Exception e) {
			logger.error("字符转码自定义EL表达式异常", e);
		}
		return "";
	}
}
