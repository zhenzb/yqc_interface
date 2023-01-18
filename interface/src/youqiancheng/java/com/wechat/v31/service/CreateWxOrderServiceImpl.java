package com.wechat.v31.service;

import com.github.binarywang.wxpay.v3.auth.AutoUpdateCertificatesVerifier;
import com.github.binarywang.wxpay.v3.auth.PrivateKeySigner;
import com.ijpay.core.kit.PayKit;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;

/**
 * @ClassName CreateWxOrderServiceImpl
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 12:41
 * @Version 1.0
 */
public class CreateWxOrderServiceImpl {

    public static void main(String[] args) {
        CreateWxOrderServiceImpl createWxOrderService = new CreateWxOrderServiceImpl();

        try {
            createWxOrderService.CreateOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CreateOrder() throws Exception{
        PrivateKey yourPrivateKey =null;

        {
            try {
                yourPrivateKey = PayKit.getPrivateKey("D:\\project\\yqc\\919yqc\\interface\\src\\youqiancheng\\resources\\cert\\apiclient_key.pem");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant("1604148735", "1223", yourPrivateKey);
// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
        CloseableHttpClient httpClient = builder.build();
//请求URL
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/partner/transactions/jsapi");
        // 请求body参数
        String reqdata = "{"
                + "\"time_expire\":\"2018-06-08T10:34:56+08:00\","
                + "\"amount\": {"
                + "\"total\":100,"
                + "\"currency\":\"CNY\""
                + "},"
                + "\"settle_info\": {"
                + "\"profit_sharing\":false"
                + "},"
                + "\"sp_mchid\":\"1230000109\","
                + "\"description\":\"Image形象店-深圳腾大-QQ公仔\","
                + "\"sub_appid\":\"wxd678efh567hg6999\","
                + "\"notify_url\":\"https://www.weixin.qq.com/wxpay/pay.php\","
                + "\"payer\": {"
                + "\"sp_openid\":\"oUpF8uMuAJO_M2pxb1Q9zNjWeS6o\","
                + "\"sub_openid\":\"oUpF8uMuAJO_M2pxb1Q9zNjWeS6o\""
                + "},"
                + "\"sp_appid\":\"wx8888888888888888\","
                + "\"out_trade_no\":\"1217752501201407033233368018\","
                + "\"goods_tag\":\"WXG\","
                + "\"sub_mchid\":\"1900000109\","
                + "\"attach\":\"自定义数据说明\","
                + "\"detail\": {"
                + "\"invoice_id\":\"wx123\","
                + "\"goods_detail\": ["
                + "{"
                + "\"goods_name\":\"iPhoneX 256G\","
                + "\"wechatpay_goods_id\":\"1001\","
                + "\"quantity\":1,"
                + "\"merchant_goods_id\":\"商品编码\","
                + "\"unit_price\":828800"
                + "},"
                + "{"
                + "\"goods_name\":\"iPhoneX 256G\","
                + "\"wechatpay_goods_id\":\"1001\","
                + "\"quantity\":1,"
                + "\"merchant_goods_id\":\"商品编码\","
                + "\"unit_price\":828800"
                + "}"
                + "],"
                + "\"cost_price\":608800"
                + "},"
                + "\"scene_info\": {"
                + "\"store_info\": {"
                + "\"address\":\"广东省深圳市南山区科技中一道10000号\","
                + "\"area_code\":\"440305\","
                + "\"name\":\"腾讯大厦分店\","
                + "\"id\":\"0001\""
                + "},"
                + "\"device_id\":\"013467007045764\","
                + "\"payer_client_ip\":\"14.23.150.211\""
                + "}"
                + "}";
        StringEntity entity = new StringEntity(reqdata,"utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
            } else if (statusCode == 204) { //处理成功，无返回Body
                System.out.println("success");
            } else {
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } finally {
            response.close();
        }
    }


}
