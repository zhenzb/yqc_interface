package com.handongkeji.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Map;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

@Slf4j
public class MD5Util {

	// 密钥 长度不得小于24
	private final static String secretKey = "123456789012345678901234";
	// 向量 可有可无 终端后台也要约定
	private final static String publicKey = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	public static String encrypt(String str) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			byte[] btInput = str.getBytes("utf-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char c[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				c[k++] = hexDigits[byte0 >>> 4 & 0xf];
				c[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(c);
		} catch (Exception e) {
			return null;
		}

	}

	public static String convertMD5(String inStr){
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 3DES加密
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey .getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(publicKey.getBytes());
		cipher.init(Cipher. ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal( plainText.getBytes( encoding));
		//return Base64.encode(encryptData);
		return Base64.encodeBase64String(encryptData);
	}

	/**
	 * 3DES解密
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory. generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding" );
		IvParameterSpec ips = new IvParameterSpec(publicKey.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		//byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		byte[] decryptData = cipher.doFinal(Base64.decodeBase64(encryptText));
		return new String(decryptData, encoding);
	}

	public static void main(String args[]) throws Exception{
		//String parmas = "{\"invitecode\":\"8627\",\"authcode\":\"777888\",\"email\":\"778899@qq.com\",\"userpassword\":\"666666\"}";  // 注册
		//String parmas = "{\"email\":\"345351346@qq.com\"}";     // 发送验证码

		String parmas = "{\"userpassword\":\"123456\",\"email\":\"2508031513@qq.com\"}";  // 登录

		//String parmas = "{\"newpassword\":\"654321\"}";  //  修改密码
		// String parmas = "{\"userid\":\"8\"}";  //  用户积分
		//String parmas = "{\"configstype\":\"1\"}";  //  用户积分
		//String parmas = "{\"currentpage\":\"1\"}";  //  首页
		//String parmas = "{\"classifyid\":\"1\"}";  //  分类列表
		//String parmas = "{\"classifyid\":\"1\"}";  //  分类列表
		//String parmas = "{\"commodityid\":\"1\"}";  //  分类列表
		//String parmas = "{\"commodityname\":\"哈哈\"}";  //  分类列表

		//String parmas = "{\"serveordername\":\"哈哈\",\"serveorderphone\":\"哈哈\",\"serveorderdetail\":\"哈哈\",\"serveid\":\"1\"}";  //  分类列表

		//String parmas = "{\"useraddressname\":\"haha\",\"useraddressphone\":\"708015\",\"useraddressstate\":\"778899@qq.com\",\"useraddressregion\":\"haha\",\"countryid\":\"11\",\"useraddress\":\"345351346@qq.com\"}";

		//String parmas = "{\"useraddressname\":\"haha\",\"useraddressphone\":\"hahahah\",\"useraddressstate\":\"hahahah\",\"useraddressregion\":\"haha\",\"countryid\":\"11\",\"useraddress\":\"345351346@qq.com\",\"useraddressid\":\"1\"}";

		//String parmas = "{\"useraddressid\":\"1\",\"exppriceid\":\"2\",\"orderleave\":\"哈哈\",\"payint\":\"1\",\"orderid\":\"32\",\"exppricetype\":\"0\"}";

		//String parmas = "{\"hfmauth\":\"hfmauth\"}";

		//String parmas = "{\"starttime\":\"2019-05-12 10:19:20\",\"endtime\":\"2019-05-13 09:19:20\"}";  // 按時間段來
		//String parmas = "{\"starttime\":\"2019-04-12 10:19:20\",\"endtime\":\"2019-05-13 09:19:20\"}";  // 按天段來
		//String parmas = "{\"starttime\":\"2019-05-12 10:19:20\",\"endtime\":\"2019-05-13 09:19:20\"}";  // 按月來

		//String parmas = "{\"orderid\":\"287\"}";

		//String parmas = "{\"hfmauth\":\"hfmauth\",\"currentpage\":\"1\"}";

		//String parmas = "{\"email\":\"345351346@qq.com\"}";

		//String parmas = "{\"useraddressid\":\"10\"}";

		//String parmas = "{\"content\":\"hahaha\"}";

		//String parmas = "{\"exppricetype\":\"1\",\"payint\":\"0\",\"useraddressid\":\"4\",\"orderid\":\"135\"}";

		System. out.println("----加密前-----：" + parmas);
		String encodeStr = MD5Util.encode(parmas);
		System. out.println("----加密后-----：" + encodeStr );
		System. out.println("----解密后-----：" + MD5Util.decode(encodeStr));
		Map<String,Object> mapType = JSON.parseObject(MD5Util.decode(encodeStr), Map.class);
		for (String obj : mapType.keySet()){
			System.out.println("key为：" + obj + "值为：" + mapType.get(obj));
		}
		log.info((String) mapType.get("authcode"));

	}

}
