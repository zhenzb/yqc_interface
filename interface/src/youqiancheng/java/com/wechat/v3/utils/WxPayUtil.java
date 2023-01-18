package com.wechat.v3.utils;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import com.ijpay.core.kit.PayKit;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
//import com.entity.pay.WxPayV3Bean;
import com.wechat.v3.bean.WxPayV3Bean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
/**
 * @ClassName WxPayUtil
 * @Description 支付相关
 * @Author zzb
 * @Date 2022/12/31 12:04
 * @Version 1.0
 */

@Slf4j
@Data
public class WxPayUtil {

    @Resource
    private WxPayV3Bean wxPayV3Bean;

    private CloseableHttpClient httpClient;
    private Verifier verifier;

    @PostConstruct
    public void initHttpClient(){
        log.info("微信支付httpClient初始化");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(FileUtil.getInputStream(wxPayV3Bean.getKeyPath()));
        X509Certificate wechatPayCertificate = PemUtil.loadCertificate(FileUtil.getInputStream(wxPayV3Bean.getPlatformCertPath()));

        ArrayList<X509Certificate> listCertificates = new ArrayList<>();
        listCertificates.add(wechatPayCertificate);

        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(wxPayV3Bean.getMchId(), wxPayV3Bean.getMchSerialNo(), merchantPrivateKey)
                .withWechatPay(listCertificates)
                .build();
    }


    @PostConstruct
    public void initVerifier() throws Exception{
        log.info("微信支付Verifier初始化");
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(FileUtil.getInputStream(wxPayV3Bean.getKeyPath()));
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(wxPayV3Bean.getMchId(), new WechatPay2Credentials(wxPayV3Bean.getMchId(),
                new PrivateKeySigner(wxPayV3Bean.getMchSerialNo(), merchantPrivateKey)), wxPayV3Bean.getApiKey3().getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        verifier = certificatesManager.getVerifier(wxPayV3Bean.getMchId());
    }


    /**
     * 生成签名
     * @param appId
     * @param timestamp
     * @param nonceStr
     * @param prepayId
     * @return
     * @throws Exception
     */
    public String getSign(String appId, String timestamp, String nonceStr, String prepayId) throws Exception{
        String message=appId + "n"
                + timestamp + "n"
                + nonceStr + "n"
                + prepayId + "n";

        String sign = PayKit.createSign(message, wxPayV3Bean.getKeyPath());

        return sign;
    }


    /**
     * 回调的支付时间转成date
     * @param dateTimeString
     * @return
     */
    public Date dateTimeToDate(String dateTimeString){
        DateTime dateTime = new DateTime(dateTimeString);
        long timeInMillis = dateTime.toCalendar(Locale.getDefault()).getTimeInMillis();
        return new Date(timeInMillis);
    }

}
