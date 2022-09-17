package com.youqiancheng.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class AliPayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    @Value("${aliPay.APP_ID}")
    public  String APP_ID ;

    // 商户私钥，您的PKCS8格式RSA2私钥
    @Value("${aliPay.APP_PRIVATE_KEY}")
    public  String APP_PRIVATE_KEY;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    @Value("${aliPay.ALIPAY_PUBLIC_KEY}")
    public  String ALIPAY_PUBLIC_KEY;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    @Value("${aliPay.notify_url}")
    public  String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    @Value("${aliPay.return_url}")
    public  String return_url;

    // 签名方式
    @Value("${aliPay.sign_type}")
    public  String sign_type;

    // 字符编码格式
    @Value("${aliPay.CHARSET}")
    public  String CHARSET;

    // 支付宝网关，这是沙箱的网关
    @Value("${aliPay.gatewayUrl}")
    public  String gatewayUrl ;
    // 日志地址
    @Value("${aliPay.log_path}")
    public  String log_path;
    //   #应用公钥证书
    @Value("${alicert.aliAppCertPublicKey}")
    public  String aliAppCertPublicKey;
    // 支付宝公钥证书
    @Value("${alicert.aliCertPublicKey}")
    public  String aliCertPublicKey;
    // 支付宝根证书
    @Value("${alicert.aliRootCert}")
    public  String aliRootCert;

//    @Bean
//    public AlipayClient alipayClient() {
//        return  new DefaultAlipayClient(gatewayUrl, APP_ID,APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, sign_type);
//    }

    @Bean
    public AlipayClient alipayClientCert() throws AlipayApiException {
      CertAlipayRequest certAlipayRequest   =   new   CertAlipayRequest ();
      certAlipayRequest . setServerUrl ( gatewayUrl );
      certAlipayRequest . setAppId ( APP_ID );
      certAlipayRequest . setPrivateKey ( APP_PRIVATE_KEY );
      certAlipayRequest . setFormat ( "json" );
      certAlipayRequest . setCharset ( CHARSET );
      certAlipayRequest . setSignType ( sign_type );
      certAlipayRequest . setCertPath (aliAppCertPublicKey);
      certAlipayRequest . setAlipayPublicCertPath (aliCertPublicKey);
      certAlipayRequest . setRootCertPath ( aliRootCert);
        return  new   DefaultAlipayClient ( certAlipayRequest );
    }



//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public  void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
