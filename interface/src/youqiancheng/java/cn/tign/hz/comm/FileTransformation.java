package cn.tign.hz.comm;

import cn.tign.hz.exception.DefineException;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description 文件转换类
 * @author  澄泓
 * @date  2020/10/26 10:47
 * @version JDK1.7
 */
public class FileTransformation {
    private static BASE64Encoder encodeBase64= new BASE64Encoder();

    /**
     * 传入本地文件路径转二进制
     * @param srcFilePath 本地文件路径
     * @return
     * @throws IOException
     */
    public static byte[] fileToBytes(String srcFilePath) throws DefineException {
        byte[] bFile;
        try {
            bFile=Files.readAllBytes(Paths.get(srcFilePath));
        }catch (IOException e){
            DefineException ex = new DefineException("输入流或输出流异常",e);
            ex.initCause(e);
            throw ex;
        }
        return bFile;
    }

    /**
     *  图片转base64
     * @param filePath 本地文件路径
     * @return
     * @throws IOException
     */
    public static String fileToBase64(String filePath) throws DefineException {
        byte[] bytes;
        String base64 = null;
        bytes=fileToBytes(filePath);
        base64 = encodeBase64.encode(bytes);
        base64 = base64.replaceAll("\r\n", "");
        return base64;

    }

    /***
     * 计算文件内容的Content-MD5
     * @param filePath 文件路径
     * @return
     */
    public static String getFileContentMD5(String filePath) throws DefineException {
        // 获取文件MD5的二进制数组（128位）
        byte[] bytes = getFileMD5Bytes1282(filePath);
        // 对文件MD5的二进制数组进行base64编码
        return new String(encodeBase64.encode(bytes));
    }

    /***
     * 获取文件MD5-二进制数组（128位）
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getFileMD5Bytes1282(String filePath) throws DefineException {
        FileInputStream fis = null;
        byte[] md5Bytes = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, length);
            }
            md5Bytes = md5.digest();
            fis.close();
        } catch (FileNotFoundException e) {
            DefineException ex = new DefineException("文件找不到", e);
            ex.initCause(e);
            throw ex;
        } catch (NoSuchAlgorithmException e) {
            DefineException ex = new DefineException("不支持此算法",e);
            ex.initCause(e);
            throw ex;
        } catch (IOException e) {
            DefineException ex = new DefineException("输入流或输出流异常",e);
            ex.initCause(e);
            throw ex;
        }
        return md5Bytes;
    }

}
