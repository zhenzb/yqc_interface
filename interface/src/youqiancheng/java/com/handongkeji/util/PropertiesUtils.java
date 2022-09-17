package com.handongkeji.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*********************************************************
 ****** @author [ CaiYong:271668825@QQ.COM ] *************
 ****** @date   2014-10-10[WILLIAM PROJECT ] *************
 *********************************************************/
public class PropertiesUtils {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PropertiesUtils.class);
	private static String configFile = "ipconfig.properties";
	private static Properties props = null;

	private PropertiesUtils() {
		logger.info("PropertiesUtils Instance ...");
	}

	public static String getProperty(String propertyName) {
		return getProperty(configFile, propertyName);
	}

	public static String getProperty(String fileName, String propertyName) {
		try {
			InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
			props = new Properties();
			props.load(in);
			return props.getProperty(propertyName);
		} catch (Exception e) {
			logger.error("ERROR =>>> Get property value by " + propertyName + " :" + e.getMessage());
			return "";
		}
	}

	/**
	 * 获取property文件util类
	 * 	RestAPIConfig.properties
	 * @author JSen
	 * @return
	 */
	public static Properties getProperties() {
		Properties p = new Properties();
		try {
			InputStream inputStream = PropertiesUtils.class.getClassLoader()
					.getResourceAsStream("sysConfig.properties");
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;
	}
}