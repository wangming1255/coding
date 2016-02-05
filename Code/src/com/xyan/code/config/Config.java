package com.xyan.code.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 读取.properties配置文件的内容至Map中。
 */
public class Config {
	private static Config instance;
	private Map<String, String> properties;

	private Config() {
		properties = read("gen");
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	/**
	 * 读取.properties配置文件的内容至Map中
	 * @param propertiesFile
	 * @return
	 */
	private static Map<String, String> read(String propertiesFile) {
		ResourceBundle rb = ResourceBundle.getBundle(propertiesFile);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enu = rb.getKeys();
		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = rb.getString(key);
			map.put(key, value);
		}
		return map;
	}

	public String getProperty(String pName) {
		return properties.get(pName);
	}

	public String getDatabaseDriver() {
		return getProperty("driver");
	}

	public String getDatabaseURL() {
		return getProperty("url");
	}

	public Properties getDatabaseProperty() {
		Properties databaseProperties = new Properties();
		databaseProperties.put("user", getProperty("user"));
		databaseProperties.put("password", getProperty("pwd"));
		databaseProperties.put("remarksReporting",
				getProperty("remarksReporting"));
		return databaseProperties;
	}

}
