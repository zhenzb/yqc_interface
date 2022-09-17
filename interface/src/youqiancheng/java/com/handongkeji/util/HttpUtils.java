package com.handongkeji.util;

public class HttpUtils {
	
	protected static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(HttpUtils.class);
	
	private static final int HTTP_TIMEOUT = 10000;
	
	/*** HTTP 超时设置 ***/
	private static final int HTTP_REQ_TIME = 3;
	
	/*** HTTP 允许请求次数 ***/
	private static final String HTTP_METHOD = "POST";

	public static String requestBss(String bssPath) {
		if (!bssPath.startsWith("/")) {
			bssPath = "/" + bssPath;
		}
		String REQ_BSS_URL = "http://" + PropertiesUtils.getProperty("BssServerIP") + bssPath;
		logger.info("REQ_BSS_URL =>>> " + REQ_BSS_URL);
		return requestHttp(REQ_BSS_URL);
	}

	@SuppressWarnings("finally")
	public static String requestHttp(String httpURL) {
		java.net.HttpURLConnection conn = null;
		java.io.BufferedReader reader = null;
		String response = "";
		try {
			for (int i = 0; i < HTTP_REQ_TIME; i++) { /***
														 * 有时候HTTP会请求失败,所以需要重复请求
														 ***/
				String str = "";
				conn = (java.net.HttpURLConnection) new java.net.URL(httpURL).openConnection(java.net.Proxy.NO_PROXY);
				conn.setReadTimeout(HTTP_TIMEOUT);
				conn.setRequestMethod(HTTP_METHOD);
				conn.setConnectTimeout(HTTP_TIMEOUT);
				reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "utf-8"));
				while ((str = reader.readLine()) != null) {
					response += str;
				}
				if (!"".equals(response)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BSS ERROR -> " + e.getMessage());
		} finally {
			try {
				reader.close(); /*** 关闭连接 ***/
				conn.disconnect(); /*** 关闭连接 ***/
			} catch (Exception e) {
				logger.error("关闭  HTTPCONNECTION 异常 -> " + e.getMessage());
			} finally {
				return response;
			}
		}
	}

	@SuppressWarnings("finally")
	public static String requestHttp(String httpURL, String HttpMethod) {
		java.net.HttpURLConnection conn = null;
		java.io.BufferedReader reader = null;
		if (StringUtil.isNullOrEmpty(HttpMethod) || (!"POST".equals(HttpMethod) && !"GET".equals(HttpMethod))) {
			HttpMethod = "POST";
		}

		String response = "";
		try {
			for (int i = 0; i < HTTP_REQ_TIME; i++) { /***
														 * 有时候HTTP会请求失败,所以需要重复请求
														 ***/
				String str = "";
				conn = (java.net.HttpURLConnection) new java.net.URL(httpURL).openConnection(java.net.Proxy.NO_PROXY);
				conn.setReadTimeout(HTTP_TIMEOUT);
				conn.setRequestMethod(HTTP_METHOD);
				conn.setConnectTimeout(HTTP_TIMEOUT);
				reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "utf-8"));
				while ((str = reader.readLine()) != null) {
					response += str;
				}
				if (!"".equals(response)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BSS ERROR -> " + e.getMessage());
		} finally {
			try {
				reader.close(); /*** 关闭连接 ***/
				conn.disconnect(); /*** 关闭连接 ***/
			} catch (Exception e) {
				logger.error("关闭  HTTPCONNECTION 异常 -> " + e.getMessage());
			} finally {
				return response;
			}
		}
	}
}