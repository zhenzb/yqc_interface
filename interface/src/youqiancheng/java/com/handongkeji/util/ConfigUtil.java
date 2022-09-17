package com.handongkeji.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {

	private static Log log = LogFactory.getLog(ConfigUtil.class);

	private static Map<String, String> map = new HashMap<String, String>();

	private synchronized static void getConfig() {
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(
				"ipconfig.properties");
		Properties pro = new Properties();
		try {
			pro.load(is);
			Enumeration<Object> keys = pro.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String val = (String) pro.get(key);
				map.put(key, val);
			}
		} catch (IOException e) {
			log.error("config.properties读取失败", e);
		}

	}

	public static String getValue(String key) {
		if (map.size() == 0) {
			getConfig();
		}
		return map.get(key);

	}

}
