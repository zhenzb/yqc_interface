package com.handongkeji.util;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 通用util
 * @author JSen
 *
 */
public class APIUtil {

	protected static final Log logger = LogFactory.getLog(APIUtil.class);

	/**
	 * 两个项目间的方法调用
	 * @author victor
	 * @param url 连接地址
	 * @param map 参数map
	 * @return JSONObject json串
	 */
	@SuppressWarnings("all")
	public static JSONObject postdotest(String url, Map<String, Object> map) {

		JSONObject json = null;

		// 读取配置文件
		Properties prop = new Properties();

		try {
			prop.load(new InputStreamReader(
					APIUtil.class.getClassLoader().getResourceAsStream("application.properties"), "UTF-8"));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		//String contentPath = prop.getProperty("PROP.CONTENTPATH");

		/** 
		* 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
		*/
		// 创建默认的httpClient实例.    
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpPost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列    
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		Set set = map.entrySet();

		for (Iterator iter = set.iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			formparams.add(new BasicNameValuePair(key, value));
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String rs = EntityUtils.toString(entity, "UTF-8");
					json = JSONObject.fromObject(rs);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源    
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	public static JSONObject jsonPost(String url, JSONObject param) {

		JSONObject json = null;

		// 读取配置文件
		Properties prop = new Properties();

		try {
			prop.load(new InputStreamReader(
					APIUtil.class.getClassLoader().getResourceAsStream("application.properties"), "UTF-8"));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		//String contentPath = prop.getProperty("PROP.CONTENTPATH");

		/** 
		* 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
		*/
		// 创建默认的httpClient实例.    
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpPost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列    

		try {
			StringEntity se = new StringEntity(param.toString());
			httppost.setEntity(se);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String rs = EntityUtils.toString(entity, "UTF-8");
					json = JSONObject.fromObject(rs);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源    
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
