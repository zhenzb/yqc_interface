package com.wechat.v32.config;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

/**
 * @ClassName WechatPayConfig
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 13:25
 * @Version 1.0
 */
//@Component
@Data
@Slf4j
@ConfigurationProperties(prefix = "wxpay")
public class WechatPayConfig {
    /** * 应用编号 */
    @Value("${wxpay.appId}")
    private String appId;
    /** * 商户号 */
    @Value("${wxpay.mchId}")
    private String mchId;
    /** * 服务商商户号 */
//    @Value("${wxpay.slMchId}")
//    private String slMchId;
    /** * APIv2密钥 */
    @Value("${wxpay.apiKey}")
    private String apiKey;
    /** * APIv3密钥 */
    @Value("${wxpay.apiV3Key}")
    private String apiV3Key;
    /** * 支付通知回调地址 */
    @Value("${wxpay.combineNotifyUrl}")
    private String notifyUrl;
    /** * 退款回调地址 */
    @Value("${wxpay.refundNotifyUrl}")
    private String refundNotifyUrl;
    /** * API 证书中的 key.pem */
    @Value("${wxpay.keyPemPath}")
    private String keyPemPath;
    /** * 商户序列号 */
    @Value("${wxpay.serialNo}")
    private String serialNo;
    /** * 微信支付V3-url前缀 */
    @Value("${wxpay.baseUrl}")
    private String baseUrl;


    /** * 获取商户的私钥文件 * @param keyPemPath * @return */
    public PrivateKey getPrivateKey(String keyPemPath){

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(keyPemPath);
        if(inputStream==null){

            throw new RuntimeException("私钥文件不存在");
        }
        return PemUtil.loadPrivateKey(inputStream);
    }
//    /** * 获取证书管理器实例 * @return */
//    @Bean
//    public Verifier getVerifier() throws GeneralSecurityException, IOException, HttpCodeException, NotFoundException {
//
//        log.info("获取证书管理器实例");
////获取商户私钥
//        PrivateKey privateKey = getPrivateKey(keyPemPath);
////私钥签名对象
//        PrivateKeySigner privateKeySigner = new PrivateKeySigner(serialNo, privateKey);
////身份认证对象
//        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);
//// 使用定时更新的签名验证器，不需要传入证书
//        CertificatesManager certificatesManager = CertificatesManager.getInstance();
//        certificatesManager.putMerchant(mchId,wechatPay2Credentials,apiV3Key.getBytes(StandardCharsets.UTF_8));
//        return certificatesManager.getVerifier(mchId);
//    }
//    /** * 获取支付http请求对象 * @param verifier * @return */
//    @Bean(name = "wxPayClient")
//    public CloseableHttpClient getWxPayClient(Verifier verifier) {
//
////获取商户私钥
//        PrivateKey privateKey = getPrivateKey(keyPemPath);
//        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
//                .withMerchant(mchId, serialNo, privateKey)
//                .withValidator(new WechatPay2Validator(verifier));
//// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
//        return builder.build();
//    }
    /** * 获取HttpClient，无需进行应答签名验证，跳过验签的流程 */
    @Bean(name = "wxPayNoSignClient")
    public CloseableHttpClient getWxPayNoSignClient(){

//获取商户私钥
        PrivateKey privateKey = getPrivateKey(keyPemPath);
//用于构造HttpClient
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
//设置商户信息
                .withMerchant(mchId, serialNo, privateKey)
//无需进行签名验证、通过withValidator((response) -> true)实现
                .withValidator((response) -> true);
// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        return builder.build();
    }
}
