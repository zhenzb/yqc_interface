package com.handongkeji.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class SendUtils {

	public static void send(String userMobile, String userCode) throws Exception {

		// 读取配置文件
		Properties prop = new Properties();
		prop.load(new InputStreamReader(SendUtils.class.getClassLoader().getResourceAsStream("send.properties"),
				"UTF-8"));

		String url = prop.getProperty("url");
		String action = prop.getProperty("action");
		String userid = prop.getProperty("userid");
		String account = prop.getProperty("account");
		String password = prop.getProperty("password");
		String mobile = prop.getProperty("mobile");
		String countnumber = prop.getProperty("countnumber");
		String mobilenumber = prop.getProperty("mobilenumber");
		String telephonenumber = prop.getProperty("telephonenumber");
		String content = prop.getProperty("content");
		String sendTime = prop.getProperty("sendTime");
		String checkcontent = prop.getProperty("checkcontent");
		String taskName = prop.getProperty("taskName");
		String isSend = prop.getProperty("isSend");
		String ref = prop.getProperty("ref");
		String conStart = prop.getProperty("conStart");
		String conEnd = prop.getProperty("conEnd");

		if ("0".equals(isSend)) {
			System.out.println(url + ".." + action + ".." + userid + ".." + account + ".." + password + ".." + mobile
					+ ".." + countnumber + ".." + mobilenumber + ".." + telephonenumber + ".." + content + ".."
					+ sendTime + ".." + checkcontent + ".." + taskName + ".." + isSend + ".." + ref + ".." + conStart
					+ ".." + conEnd);
		}
		// 发送短信
		if (isSend.equals("1")) {
			String str = "action=" + action + "&userid=" + userid + "&account=" + account + "&password=" + password
					+ "&mobile=" + userMobile + "&countnumber=" + countnumber + "&mobilenumber=" + mobilenumber
					+ "&telephonenumber=" + telephonenumber + "&content=" + conStart + userCode + conEnd;

			URLConnection connection = new URL(url).openConnection();

			connection.setDoOutput(true);

			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");

			out.write(str);

			out.flush();
			out.close();

			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();

			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine + "\n";

			}
			// 控制台打印返回结果
			if (ref.equals("1")) {
				System.out.println("-------------------回执信息-------------------");
				System.out.println(sTotalString);
				System.out.println("----------发送结束---------");
			}
		}
	}
}
