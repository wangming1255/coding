package com.xyan.code.util;

/**
 * @description 名称解析
 * @author wangming
 * @date 2016年2月3日 上午9:26:40
 */
public class NameParser {

	/**
	 * @Description:获取实体类名称
	 * @param tableName
	 * @return 
	 * @author:wangming
	 * @date:2016年2月2日 下午6:13:03
	 */
	public static String getDomainName(String tableName) {
		int index = tableName.indexOf('_');
		String className = tableName.substring(index + 1);
		return capitalize(className, true);
	}

	/**
	 * @Description:驼峰命名
	 * @param value 要驼峰命名的字符串
	 * @param firstUpper 第一个字母是否大写
	 * @return
	 * @author:wangming
	 * @date:2016年2月2日 下午6:15:29
	 */
	public static String capitalize(String value, boolean firstUpper) {
		StringBuilder stringBuilder = new StringBuilder(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c != '_') {
				if (i == 0 && firstUpper) {
					stringBuilder.append(Character.toUpperCase(c));
				} else {
					stringBuilder.append(Character.toLowerCase(c));
				}
			} else {
				i++;
				c = value.charAt(i);
				stringBuilder.append(Character.toUpperCase(c));
			}
		}
		return stringBuilder.toString();
	}
	
	

	
}
