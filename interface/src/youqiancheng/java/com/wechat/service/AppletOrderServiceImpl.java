package com.wechat.service;

import com.alipay.api.domain.PreOrderResult;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import com.ijpay.core.kit.PayKit;
import com.sun.xml.internal.ws.util.xml.XmlUtil;
import com.wechat.domain.PreOrder;
import com.wechat.domain.WxContants;
import com.wechat.domain.WxPayProperties;
import io.netty.handler.codec.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import okhttp3.HttpUrl;
import java.security.Signature;
import java.util.Base64;

/**
 * @ClassName AppletOrderServiceImpl
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 8:41
 * @Version 1.0
 */
public class AppletOrderServiceImpl implements AppletOrderService{
    @Autowired
    private WxPayProperties wxPayProperties;

    @Override
    public PreOrderResult placeOrder(String body, String out_trade_no, String total_fee, String openId) {
        // 生成预付单对象
        PreOrder preOrder = new PreOrder();
        preOrder.setAppid(wxPayProperties.getApp_id());
        preOrder.setMch_id(wxPayProperties.getMch_id());
        preOrder.setSub_appid(wxPayProperties.getSub_app_id());
        preOrder.setSub_mch_id(wxPayProperties.getSub_mch_id());
        String nonce_str = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        preOrder.setNonce_str(nonce_str);

        preOrder.setBody(body);
        preOrder.setOut_trade_no(out_trade_no);

        preOrder.setTotal_fee(new BigDecimal(total_fee));
        preOrder.setSpbill_create_ip(wxPayProperties.getSpbill_create_ip());
        preOrder.setNotify_url(wxPayProperties.getNotify_url());
        preOrder.setTrade_type(WxContants.TRADE_TYPE);
        preOrder.setSub_openid(openId);
        SortedMap<Object, Object> p = new TreeMap<Object, Object>();
        p.put("appid", wxPayProperties.getApp_id());
        p.put("mch_id", wxPayProperties.getMch_id());
        p.put("sub_appid", wxPayProperties.getSub_app_id());
        p.put("sub_mch_id", wxPayProperties.getSub_mch_id());
        p.put("body", body);
        p.put("nonce_str", nonce_str);
        p.put("out_trade_no", out_trade_no);
        p.put("total_fee", total_fee);
        p.put("spbill_create_ip", wxPayProperties.getSpbill_create_ip());
        p.put("notify_url", wxPayProperties.getNotify_url());
        p.put("trade_type", WxContants.TRADE_TYPE);
        p.put("sub_openid", openId);
        // 签名
        //String sign = SignUtils.createSignByMd5(p, wxPayProperties.getKey());
        HttpUrl httpurl = HttpUrl.parse(" https://api.mch.weixin.qq.com/v3/certificates");
        String sign = getToken("GET", httpurl, p.toString());
        preOrder.setSign(sign);
        //String xml = XmlUtil.object2Xml(preOrder, PreOrder.class);
        // 调用下单地址
        //String returnXml = HttpUtil.sendPost(WxContants.pay_url, xml);
       // System.out.println(returnXml);
        // XML转换为Object
       // PreOrderResult preOrderResult = (PreOrderResult) XmlUtil.xml2Object(returnXml, PreOrderResult.class);
        // XML转换为Object
        // 一般企业开发中支付流水入库，支付状态更新这些都需要做，此出省略
        return null;
    }

//    @Override
//    public PayResult getWxPayResult(HttpServletRequest request) throws Exception {
//        InputStream inStream = request.getInputStream();
//        BufferedReader in = null;
//        String result = "";
//        in = new BufferedReader(
//                new InputStreamReader(inStream));
//        String line;
//        while ((line = in.readLine()) != null) {
//            result += line;
//        }
//        PayResult pr = (PayResult)XmlUtil.xml2Object(result, PayResult.class);
//        System.out.println(pr.toString());
//        return pr;
//    }

//    String schema = "WECHATPAY2-SHA256-RSA2048";
//    HttpUrl httpurl = HttpUrl.parse(url);
    //https://api.mch.weixin.qq.com/v3/certificates

    public static void main(String[] args) {
        AppletOrderServiceImpl appletOrderService = new AppletOrderServiceImpl();
        SortedMap<Object, Object> p = new TreeMap<Object, Object>();
        p.put("appid", "wxeb5de11b49702a70"); //注意这里是服务号的appid，不是小程序的
        p.put("mch_id","1604148735");//这里是用服务商的id 在我的账号一栏可以找到
        p.put("sub_appid", "wxeb5d2451b49702a70");//这里才是小程序的appid
        p.put("sub_mch_id", "1606268735"); //这里对应特约商户号id 付款到对应商户的凭证就是这个 在注册特约商户的时候邮件里可以找到 这里建议配置到数据库动态传递
        p.put("body", "ceshi");//这里随意填写，也可以填写商品名称
        p.put("nonce_str", "2kna43o4i234345j435"); //随机字符串
        p.put("out_trade_no", "12354334562446"); //订单号
        p.put("total_fee", 1); //这里必须是整数，单位是分
        p.put("spbill_create_ip", "101.41.173.161"); //这里可以随意填写
        p.put("notify_url", "https://www.youqiancheng.vip/#/");//支付回调的地址
        p.put("trade_type", "JSAPI"); //公众平台支付或小程序支付填写：JSAPI，如果是APP的填写：APP
        p.put("sub_openid", "123456789"); //此参数是在发起支付前在小程序内调起wx.login 方法获得code 然后后台通过置换 获得用户openid
        HttpUrl httpurl = HttpUrl.parse(" https://api.mch.weixin.qq.com/v3/certificates");
        String sign = appletOrderService.getToken("GET", httpurl, p.toString());
        System.out.println("sign:"+sign);
    }

    String getToken(String method, HttpUrl url, String body) {
        String nonceStr = "your nonce string";
        long timestamp = System.currentTimeMillis() / 1000;
        String message = buildMessage(method, url, timestamp, nonceStr, body);
        String signature = null;
        try {
            signature = sign(message.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "mchid=\"" + "yourMerchantId" + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + "yourCertificateSerialNo" + "\","
                + "signature=\"" + signature + "\"";
    }
    PrivateKey yourPrivateKey;

    {
        try {
            yourPrivateKey = PayKit.getPrivateKey("D:\\project\\yqc\\919yqc\\interface\\src\\youqiancheng\\resources\\cert\\apiclient_key.pem");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    String sign(byte[] message) {
        Signature sign = null;
        try {
            sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(yourPrivateKey);
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
      return null;
    }

    String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

}
