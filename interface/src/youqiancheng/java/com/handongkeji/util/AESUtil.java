package com.handongkeji.util;
/**
 * AES 128bit 加密解密工具类
 * @author dufy
 */

import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESUtil {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同，也可以不同!
    private static String KEY;
    private static String IV;

    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";

    /**
     * 因为静态变量不能直接从配置文件通过@value获取，所以采用此方法
     * @param key
     */
    @Value("${AES.KEY}")
    public void AESkey(String key){
        KEY = "aaDJL2d9DfhLZO0z";
    }
    @Value("${AES.IV}")
    public void AESIv(String iv){
        IV = "412ADDSSFA342442";
    }

    /**
     * 加密方法 返回base64加密字符串
     * 和前端保持一致
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);//"算法/模式/补码方式"NoPadding PKCS5Padding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec("aaDJL2d9DfhLZO0z".getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec("412ADDSSFA342442".getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) throws Exception {
        try {
//        byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            SecretKeySpec keyspec = new SecretKeySpec("aaDJL2d9DfhLZO0z".getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec("412ADDSSFA342442".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(new Base64().decode(data));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        return decrypt(data, KEY, IV);
    }



    /**
     * 测试
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String args[]) throws Exception {
//        Map map = new HashMap<>();
//        map.put("code", "0");
//        map.put("data", "3232dsfs");
        E05RedenvelopesReceiveRecordDO e05 = new E05RedenvelopesReceiveRecordDO();
        e05.setMoney(new BigDecimal("0.06"));
        e05.setIsReceive(1);
        List<E05RedenvelopesReceiveRecordDO> shopRedPacket = new ArrayList<>();
        shopRedPacket.add(e05);
        String test1 = JSON.toJSONString(shopRedPacket);
        String test =new String(test1.getBytes(),"UTF-8");
        String data = null;
        data = encrypt(test);
        System.out.println("数据："+test);
        System.out.println("加密："+data);
        String jiemi =decrypt(data).trim();
        System.out.println("解密："+jiemi);

    }

}
