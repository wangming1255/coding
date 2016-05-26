package com.xyan.code.util;

/**
 * @description 字符串工具类
 * @author wangming
 * @date 2016年2月3日 上午9:23:20
 */
public class StringUtils {

	/**
	 * @Description:首字母小写
	 * @param s
	 *            字符串
	 * @return
	 * @author:wangming
	 * @date:2016年2月3日 上午9:23:52
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {

			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	/**
	 * @Description:首字母转大写
	 * @param s
	 *            字符串
	 * @return
	 * @author:wangming
	 * @date:2016年2月3日 上午9:24:08
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		int len = searchStr.length();
		int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (regionMatches(str, true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}

	static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start,
			int length) {
		if (cs instanceof String && substring instanceof String) {
			return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
		} else {
			// Implement rather than convert to String
			return cs.toString().regionMatches(ignoreCase, thisStart, substring.toString(), start, length);
		}
	}
}
