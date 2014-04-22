package com.ecarinfo.utils;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Description: 汉字拼音处理工具类
 * @Date 2012-11-6
 * @Version V1.0
 */

public class PinYinUtil {
	/**
	 * 得到 全拼
	 */
	public static String getPingYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += java.lang.Character.toString(t1[i]);
				}
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 得到中文首字母
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 得到第一个中文首字母
	 */
	public static String getPinYinOneHeadChar(String str) {
		String convert = "";
		if (StringUtils.isNotEmpty(str)) {
			char word = str.charAt(0);
			if (!isHeadChar(str)) {
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				convert = String.valueOf(pinyinArray[0].charAt(0));
			} else {
				convert = String.valueOf(word);
			}
		}
		return convert;
	}

	/**
	 * 判断字符串首字符是否为字母
	 */
	public static boolean isHeadChar(String s) {
		char c = s.charAt(0);
		int i = (int) c;
		if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将字符串转移为ASCII码
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) {
		// System.err.println(getPinYinHeadChar("邓支晓"));
		System.err.println(getPinYinOneHeadChar("张支晓").toUpperCase());
		System.err.println(getPinYinOneHeadChar("d支晓"));
		// System.err.println(getPinYinHeadChar("dengzhix"));
		// System.out.println(getCnASCII("蛋蛋"));
	}
}
