//package com.youqiancheng.controller.client.alipay;
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class AlipayConfig {
//
//	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
//	//public static String APP_ID = "2016102200737549";
//	public static String APP_ID = "2021001163690789";
//
//	// 商户私钥，您的PKCS8格式RSA2私钥
//	//public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCpaiCNPpOe8tfP3xvC/Da56Rf61d6TeTjuAAbjO1hM5oL08HU0rMAqz8mlbS1xKk/47vu7N8pMmm85sedPGDLYY2cRBU7rmAxdPwN7q3bwB1GhqaKnYxxqg1ZwCkd1cw6dQQgN8V3/CyQ3P3HLa+o5nzVGmxGfCCNCkpfla8o2qTVWn/VTPT0F6Bw0wATFtlo1gIa+uEdpUxW5rdyZTBjwF7EzRNpgZRAwKAb1ytMnYkepGYauCDlcQcB4CpZuuDpjWcPT+F74O66bJQVi2y7XVABmRD3KqrQeoQqFyCOmT8lkKBEo1BCM4tyq11a1tceWupq1ivN+EXbLSl2SaYNFAgMBAAECggEASMgCIgv/KWsUkgY8jAEElcvw9F8LFvoeHgk+7EIjhi4Flqry/2UzzXClJX+I5TQTVVN+LAkkzNIP9I5cXBt+RH432hUYuZgBv9GBcpDQMQBD7prM+PJYU94GtW2FE40Rezdj+XF3O0mTDnUgMw8yefzOy311DYhWdZQ602ICZoaJ3uSb9HSuXxL0O8oAM0oHI3tEPS7zv7zk7vL7OSCcXdjXi6rBITfF5Js97rvDFfMRKPfxOHgXmT85OhWb1vdytlel0gvf9mL6V6gYdRyLI370/EF362I4kunNH2XMYW6Z5K2Nnh+cighAlrIa9ynrrHjVijTI8f51PXjF+FhZAQKBgQDcPtN2hZAJcSM8e+1wglTGKd32XsfziLooe2uCEcpzxk+dTXBD9SfFQz/TG14DY0AOK6LNzkRePQbVBHG7MLLGCiGpVu38GUJhZfUfYEi5tnlm4YzX2pnKdA7X3fW+Dh/ELtDkXHAGl9dLtXmFUgSlqADpo24p7in0GRUBzwnhhQKBgQDE6tKkrFZsjEq2i1Se2nXgxcqqHuXx0Tmj296pYPJQstchocd8ske62da7HSPBOtXPXKE1te/FsBWzITFrqaWaoUPZeswoKtL/TBil2fAGw08krXSz8/TV3uLUfdeng1/9UpZNO//Z+FkAakNDAdSDVk/WH1bQgHGBcSa70MXmwQKBgHqHSUWq0JMwKhZAK7xoBZmpOR8TY9IMwuQ6pXjbRPJb5Xbx2f80qEeKPjvIAemIkd5QUGaG1mpHMUHZExy83vhmMovvHL8fnlppSpGGE2+eILQnzx9XMi/7mTZm+Ywed3XBRd2Fv50rZE9uSojtNECk0r0oeuVA5n4yZgl3kVuxAoGATmRVWy0ocqofDDDHjT3W85aKKdOHIw9x7OOY7sEjdjDcKA1phyDKIX6bV34wP7qWZ6iunN1DCOYinV83d27f+ID+q9OQ9uSlfvDiVxQ909No7GcJMKKL86B5mLJIae2JByYbo7+hCTsu41VqVYCjA24W9l0fiH25shhDnT1tq4ECgYBJTIejBvH295eGcqgwxsfudNvjO18pp3+9+zk+EBw+oeM4hSvQiMxaqonAxO+CdHxU2vGK6xLBRCvDt7G0U2DHuIgCzWf6RJyqO31U5uAyRe33AXNM7pJySmYNhnURax5HytRJQhfmZp74PddXN7lgGbZQY8mT3WYrBaYKP5gReA==";
//	public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCnF/khU9aUKbGJvWt5JreYqZ8d7+oAMQzraBUJyJbIE760i/GXkmvLIarmdfzMXVLWWZH1E3NX6hlgjDBFr/xFyMntJL2qmwJylcai89EFpUV2T7IoUEj+OooYscR+Pnlhk5kzNENpfDXfADSt1iHuH9qLa82vAbRRAC5TnRemNQ3vdtXGg09Y0F1WxdRMWVA6juhEhH8Jm+rER6btR9JH4rmgpcOs8Rdc3AZnk/viyUmC1Ueyy5Q+u4FLLtdpF50j8Vuey4ZTjHmrHjSS+y3LbiSIDVucitEeRFprhal8HfVvB0zJXJQm6aFgAC4HCGI+pnEQlOcYe2k3CT00163zAgMBAAECggEAHOw5Y1tvuRW+dWkkQURCY1p5AGWWy5Gx+s15Vsgx0VAZoobWurXNksuxGVMy/uzoedHnQdSsEnujCOM7N9TOhgXLbhD+A5nxQoRKbcpmU+PXsjL5U6ESAW+Al+WtbWzC9fDmg8Z4JLorWP5gH/cAyIca/Scb4z8YRy0Q18hJX6tns++Khwwvte8G0TvYiNYcgWFv/zs7e3rhrHoUdkWDICQ9X3b13pBlV+f9+0TxrHKTFU7cGVWpGvA4HlvXNO8UOZr3O0Lcy1saYVxWjAEPt3cyfQ2nEqyZJVpc993MAk0rAGzd8dRtAwxRC2TRxg8bUW6IU9MIVNShEbrIdThgAQKBgQDQQXeQJDN2xTzPGcQ80aBw+SiCQx420JMwvaTOmKyiWyEQmfVYWXYGLeZbBeSwluHOW5HP9OEA/iSJRYAvEfmhnmqx5i9I+FCVJ9a84HNrovuZPv6RINTYS3V2Lt4JcX7Q92eLQ3gWPPJUMBPuvjpuAc1/PG3EIe2Ip4CB9c0I8wKBgQDNZrPXuC+O3rU1aKLFOtfNwUQQxwN3u5k4PCurjLVVWUXeYZTbfqsAYwOeTzQ80nz5tjRFXjFKTWC9MxaFTIESErkxtGklkNr+82vB49NRrBUpszWxj9kXHAbOKUZoy5aJnyoVUjStC7I+kgCH+bW7LRlbNWLM2byu80iXgAQHAQKBgFvjlxLltkOAsd0bnGfNgwJ2HbakUM+U5m1KME7sNLgKh6Ngm03ZY2dEl3CwQADfAlFgPA1LGoCqoopjPTUHtGLomGFYlKKY1k2J34lFbAghSOyMTBzTJLcvTGvSMCxwhKlFkM10/qapS69z60ebbgKeDwNGMZSp97olVmGBiG+bAoGAMJ6j6Wcu0rnC2Q6YoOkJ1iluTC5fksL0RMxxZd0bu0Uzda1RiBdWsPO/EoUbD16fuJg5jUX7kuP4BzEFnq36S2XlMm/LB9pfDERgeHfOOKHsLH/AMPSk5YR70+cPRJUUYCUt1/gqgd+n5Im42HbyXokRXHapmWXc9OifgUULLwECgYA/9wz5ud6fEcKoqNEVijGq04IqZy4Gw4509S0vO1+dNARHACIM1EoUMa9tIJBXVfgotTmeb4gXxuR3zFN0SzXomQ/0rjDZEJoZRhQEzLRlu5o26CCiDngjGfw3w9kufCT2U916henQmy/lvPGdUb3YGm/bUQNO2fzBID1p2vn1rw==";
//
//	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//	//public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp+syICoM7MfWrV02MwRM0E5RcJ1e2NcryWvCUBccJf70YXFGNLBlppTJ8+gYxhmmYT7+8ybqC5N5HWqhQo4LCbiOtZU1pJUWAMkAdKyvZ+5mYmr2PiBDM5lbT74HaF54QlNEFz63gVNOMocYXynfE27c4zuz1kMruzmjylind1OfrDWtDAtnliXBW76IJRpbC9mvQSO9CiBkfC43Vxq/IQ0iXi+EsgLOYwz5lFwuQ5223+QiKK439H37Eor5wV8RP2TKH51EuiluHf5l7UipQJABVL4kIGIWiQ6hQYnWCTH5oF5Wnx5NjIOO9ss7Ph7SMCslUsccQtX3Yfr5chNzbwIDAQAB";
//	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu0rpFmfQoTTu2Z5vJdf2HGg2gSfq1UfH7cIuLNUZSyNYjTHyJ0+8MjxpBDRGvhagfFLncwEzOdqDDsZtt9ycA4lCkKJdyT02fXKNTXsQ5HCyl9YaY+xi1joif38PRNPgOSe0wLvuFdp+v8SKmxsXBKYPksuKjTH9bNmBiV5wc4nG2QHpADzsKiJiuYX7BmEsRq1KKFe+3riYi1bMX0uL7Yvvr8Cs1RZVWkYIeF0RV6s+tB9b00Bhp+Ow+3m3hpq7ERT+QES/3hWvxsVS4jH2lvllJ2MTv6bRuo7NO5APu/tnonP0Idn2DqtkqTp/xG49xFliHg5tih6jBFF0jrhwCwIDAQAB";
//
//	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://localhost:8080/pc/alipay/pay/notifyalipay";
//	public static String refund_notify_url = "http://localhost:8080/pc/alipay/refund/notifyalipay";
//
//	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
//	public static String return_url = "http://localhost:8080/pc/alipay/notifyalipay";
//
//	// 签名方式
//	public static String sign_type = "RSA2";
//
//	// 字符编码格式
//	public static String CHARSET = "utf-8";
//
//	// 支付宝网关，这是沙箱的网关
//	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
//
//	// 日志地址
//	public static String log_path = "C:\\";
//
//
////↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
//
//	/**
//	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
//	 * @param sWord 要写入日志里的文本内容
//	 */
//	public static void logResult(String sWord) {
//		FileWriter writer = null;
//		try {
//			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
//			writer.write(sWord);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//}
