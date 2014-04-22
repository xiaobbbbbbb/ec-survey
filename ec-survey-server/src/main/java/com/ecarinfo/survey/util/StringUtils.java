package com.ecarinfo.survey.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 过滤掉字符串中的空白,换行,制表符等
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
//			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("`");
		}
		return dest;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {		

	}

}
