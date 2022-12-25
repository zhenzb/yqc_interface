package com.wechat;

import ch.qos.logback.core.joran.spi.XMLUtil;
import com.google.gson.Gson;
import com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil;
import com.youqiancheng.util.HttpUtil;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;


/**
 * @ClassName WechatPayController
 * @Description 微信小程序支付类，生成微信小程序端调用支付所需参数
 * @Author zzb
 * @Date 2022/12/1 22:03
 * @Version 1.0
 */
public class WechatPayController {


   // private static final SignUtil PayCommonUtil = ;

    //微信下单支付
    @ResponseBody
    @RequestMapping("doOrder")
    public void doOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        //得到openid
//        String openid = request.getParameter("openid");
//        int fee = 0;
//        //得到小程序传过来的价格，注意这里的价格必须为整数，1代表1分，所以传过来的值必须*100；
//        if (null != request.getParameter("price")) {
//            fee = Integer.parseInt(request.getParameter("price").toString());
//
//        }
//        System.out.println(request.getParameter("price"));
//        System.out.println(fee);
//        //订单编号
//        String did = request.getParameter("did");
//        //订单标题
//        String title = request.getParameter("title");
//        //时间戳
//        String times = System.currentTimeMillis() + "";
//        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
//        packageParams.put("appid", "wxa**********2e2");
//        packageParams.put("mch_id", "1486425722");
//        packageParams.put("nonce_str", times);//时间戳
//        packageParams.put("body", title);//支付主体
//        packageParams.put("out_trade_no", did);//编号
//        packageParams.put("total_fee", fee);//价格
//        // packageParams.put("spbill_create_ip", getIp2(request));这里之前加了ip，但是总是获取sign失败，原因不明，之后就注释掉了
//        packageParams.put("notify_url", "/notify");//支付回调接口，用于支付成功后处理业务逻辑，小程序端支付success不能保证100%回调成功，建议采用后端异步回调处理方式，回调方法在最后
//        packageParams.put("trade_type", "JSAPI");//这个api有，固定的
//        packageParams.put("openid", openid);//openid
//        //获取sign
//        String sign = PayCommonUtil.createSign("UTF-8", packageParams, "x********************************4");//最后这个是自己设置的32位密钥
//        packageParams.put("sign", sign);
//        //转成XML
//        String requestXML = PayCommonUtil.getRequestXml(packageParams);
//        System.out.println(requestXML);
//        //得到含有prepay_id的XML
//        String resXml = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
//        System.out.println(resXml);
//        //解析XML存入Map
//        Map map = XMLUtil.doXMLParse(resXml);
//        System.out.println(map);
//        // String return_code = (String) map.get("return_code");
//        //得到prepay_id
//        String prepay_id = (String) map.get("prepay_id");
//        SortedMap<Object, Object> packageP = new TreeMap<Object, Object>();
//        packageP.put("appId", "wxa**********2e2");//！！！注意，这里是appId,上面是appid，真怀疑写这个东西的人。。。
//        packageP.put("nonceStr", times);//时间戳
//        packageP.put("package", "prepay_id=" + prepay_id);//必须把package写成 "prepay_id="+prepay_id这种形式
//        packageP.put("signType", "MD5");//paySign加密
//        packageP.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
//        //得到paySign
//        String paySign = PayCommonUtil.createSign("UTF-8", packageP, "x********************************4");
//        packageP.put("paySign", paySign);
//        //将packageP数据返回给小程序
//        Gson gson = new Gson();
//        String json = gson.toJson(packageP);
//        PrintWriter pw = response.getWriter();
//        System.out.println(json);
//        pw.write(json);
//        pw.close();

    }

    // 商户API私钥路径
    @Value("${wechat.apiclientKey}")
    public  String apiclientKey;

    // 微信支付平台证书路径
    @Value("${wechat.apiclientCert}")
    public  String apiclientCert;
    /** 商户号 */
    public static String merchantId = "1604148735";
    /** 商户API私钥路径 */
    public static String privateKeyPath = "D:\\project\\yqc\\919yqc\\interface\\src\\youqiancheng\\resources\\cert\\apiclient_key.pem";
    /** 商户证书序列号 */
    public static String merchantSerialNumber = "1BEB8DBF9F7AFF7325EC740C211F3C9AF665CC87";
    /** 微信支付平台证书路径 */
    public static String wechatPayCertificatePath = "D:\\project\\yqc\\919yqc\\interface\\src\\youqiancheng\\resources\\alicert\\apiclient_cert.pem";

    public static void main(String[] args) {
        Config config =
                new RSAConfig.Builder()
                        .merchantId(merchantId)
                        .privateKeyFromPath(privateKeyPath)
                        .merchantSerialNumber(merchantSerialNumber)
                        .wechatPayCertificatesFromPath(wechatPayCertificatePath)
                        .build();
        JsapiService service = new JsapiService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(100);
        request.setAmount(amount);
        request.setAppid("wxeb5de11b49702a70");
        request.setMchid(merchantId);
        request.setDescription("测试商品标题");
        request.setNotifyUrl("https://client.youqiancheng.vip/eNotify/psnWebVerify");
        request.setOutTradeNo("out_trade_no_001");
        Payer payer = new Payer();
        payer.setOpenid("Uj4yt5MOnMh8QTZjCqwqOf3nA");
        request.setPayer(payer);
        PrepayResponse response = service.prepay(request);
        System.out.println("返回结果: "+response.getPrepayId());
    }

}
