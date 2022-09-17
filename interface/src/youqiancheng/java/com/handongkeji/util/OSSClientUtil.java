package com.handongkeji.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Zhenzhuobin
 * @Date: 2020/1/3 0003 14:42
 */
public class OSSClientUtil {
    /** 阿里云API的密钥Access Key ID */
    private static String accessKeyId="LTAI4GBfcabnhQgLmk5xWWYt";
    /** 阿里云API的密钥Access Key Secret */
    private static String accessKeySecret="XYEkrDmqmoUPKjCoUbpnp46geWBjpm";
    /** 阿里云API的内或外网域名 */
    private static String endpoint="http://oss-cn-beijing.aliyuncs.com";
    /** 阿里云API的bucket名称 */
    private static String bucketName="youqianchengpc";
    /**阿里云bucket中文件夹的名称 **/
    private static String objectName="picture/";
    /** 阿里云oss访问地址 */
    private static String address = "https://p.youqiancheng.vip/picture";

    public Map<String, Object> uploadFile(InputStream inputStream, String suffix) {
        // 生成随机的文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;

        // 调用阿里云 oss把图片上传到 oss
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        String content = "Hello OSS";
// <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName+fileName,inputStream);

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();

        // 获取上传后的图片链接
        // 后端将地址拼接一下，oss那里设为了公共读，阿里云屁事太多了
        String picAddr = address + "/" + fileName;

        // 前端所必须的返回格式
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("url", picAddr);
            put("success", 1);
            put("message", "upload success!");
        }};
        return map;
    }

    public static String getUrl(String key){

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
                accessKeySecret);
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        GeneratePresignedUrlRequest generatePresignedUrlRequest ;
        generatePresignedUrlRequest =new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public static void main(String[] args) {
        String url = getUrl("picture/04b19b4304f74c4faa794499df371941,微信图片_20201204224838.jpg");
        System.out.println(url);
    }
}
