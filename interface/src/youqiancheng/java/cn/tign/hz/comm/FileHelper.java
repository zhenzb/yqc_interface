package cn.tign.hz.comm;

import cn.tign.hz.exception.DefineException;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @description 文件辅助类
 * @author 宫清
 * @date 2019年7月9日 下午4:54:07
 * @since JDK1.7
 */
public class FileHelper {

	// ------------------------------公有方法start--------------------------------------------

	/**
	 * 不允许外部创建实例
	 */
	private FileHelper() {
	}

	/**
	 * @description 获取文件字节流
	 * @param filePath {@link String} 文件地址
	 * @return
	 * @throws DefineException
	 * @date 2019年7月10日 上午9:17:00
	 * @author 宫清
	 */
	public static byte[] getBytes(String filePath) throws DefineException {
		File file = new File(filePath);
		FileInputStream fis = null;
		byte[] buffer = null;
		try {
			fis = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			fis.read(buffer);
		} catch (Exception e) {
			DefineException ex = new DefineException("获取文件字节流失败", e);
			ex.initCause(e);
			throw ex;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					DefineException ex = new DefineException("关闭文件字节流失败", e);
					ex.initCause(e);
					throw ex;
				}
			}
		}
		return buffer;
	}


	/**
	 * @description 计算文件contentMd5值
	 *
	 * @param filePath {@link String} 文件路径
	 * @return
	 * @author 宫清
	 * @throws DefineException
	 * @date 2019年7月14日 下午1:35:41
	 */
	public static String getContentMD5(String filePath) throws DefineException {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(getFileMd5Bytes128(filePath));
	}

	
	/**
	 * @description 根据文件路径，获取文件base64
	 *
	 * @param path
	 * @return	
	 * @throws DefineException
	 * @author 宫清
	 * @date 2019年7月21日 下午4:22:08
	 */
	public static String getBase64Str(String path) throws DefineException {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(path));
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			return new BASE64Encoder().encode(bytes);
		} catch (Exception e) {
			DefineException ex = new DefineException("获取文件输入流失败", e);
			ex.initCause(e);
			throw ex;
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					DefineException ex = new DefineException("关闭文件输入流失败", e);
					ex.initCause(e);
					throw ex;
				}
			}
		}
	}
	
	/**
	 * @description 获取文件名称
	 *
	 * @param path 文件路径
	 * @return
	 * @author 宫清
	 * @date 2019年7月21日 下午8:21:16
	 */
	public static String getFileName(String path){
		return new File(path).getName();
	}
	// ------------------------------公有方法end----------------------------------------------

	// ------------------------------私有方法start--------------------------------------------

	/**
	 * @description 获取文件Md5二进制数组（128位）
	 *
	 * @param filePath {@link String} 文件路径
	 * @return
	 * @author 宫清
	 * @throws DefineException
	 * @date 2019年7月14日 下午1:36:42
	 */
	private static byte[] getFileMd5Bytes128(String filePath) throws DefineException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[fis.available()];
			int len = -1;
			while ((len = fis.read(buffer, 0, 1024)) != -1) {
				md5.update(buffer, 0, len);
			}
			return md5.digest();
		} catch (Exception e) {
			DefineException ex = new DefineException("获取文件md5二进制数组失败", e);
			ex.initCause(e);
			throw ex;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					DefineException ex = new DefineException("关闭读写流失败", e);
					ex.initCause(e);
					throw ex;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			String s  = "d:/AAAAA.pdf";
			System.out.println(getContentMD5(s));
		} catch (DefineException e) {
			e.printStackTrace();
		}
	}
	// ------------------------------私有方法end----------------------------------------------

}
