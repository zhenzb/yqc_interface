//package com.youqiancheng.controller.wechatpay;
//
//import com.handongkeji.util.manager.ResultVOUtils;
//import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
//import com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil;
//import com.youqiancheng.controller.wechatpay.weixinpay.util.IpAddressUtil;
//import com.youqiancheng.controller.wechatpay.weixinpay.util.RandomUtils;
//import com.youqiancheng.vo.result.ResultEnum;
//import com.youqiancheng.vo.result.ResultVo;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.util.EntityUtils;
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.net.ssl.SSLContext;
//import javax.servlet.http.HttpServletRequest;
//import java.io.InputStream;
//import java.security.KeyStore;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.UUID;
//
///**
// * @Author: wrc
// * @Classname TransferController
// * @Description TODO
// * @Date 2020/6/9 9:59
// * @Created wrc
// */
//@RestController
//@RequestMapping("/transfer")
//public class TransferController {
//
//    private static final String TRANSFERS_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; // 企业付款
//
//    private static final String TRANSFERS_PAY_QUERY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo"; // 企业付款查询
//
//    // private static final String API_SECRET = MyWxpayConfig.A
//
//    /**
//     * 企业向个人支付转账
//     *
//     * @param request
//     * @param openid   用户openid
//     */
//    @PostMapping("wxSendWallet")
//    public ResultVo wxSendWallet (String openid, HttpServletRequest request){
//
//            //创建一个唯一订单号
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//            String time = sdf.format(new Date());
//            String orderId = time + (int) (Math.random() * 1000000);
//            String spbill_create_ip = IpAddressUtil.getIpAddr(request);
//            String xml = wxSendWallet(openid,spbill_create_ip );
//            try {
//                //指定读取证书格式为PKCS12
//                KeyStore keyStore = KeyStore.getInstance("PKCS12");
//                //windows系统
//                //FileInputStream instream = new FileInputStream(new File("D:\\youqiancheng\\src\\youqiancheng\\resources\\cert\\apiclient_cert.p12"));
//                //linux系统,读取本机存放的PKCS12证书文件
//                //InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream("");
//                InputStream instream = WeiXinRefundUtil.class.getClassLoader().getResourceAsStream("apiclient_cert.p12");
//                try {
//                    //指定PKCS12的密码(商户ID)
//                    //keyStore.load(instream, accountUtil.getWxPartnerId().toCharArray());
//                    keyStore.load(instream,WexinPayConfig.wx_mchid.toCharArray());
//                } finally {
//                    instream.close();
//                }
//                // Trust own CA and all self-signed certs
//                SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,WexinPayConfig.wx_mchid.toCharArray()).build();
//                //指定TLS版本, Allow TLSv1 protocol only
//                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
//                        sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//                //设置httpclient的SSLSocketFactory
//                CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//                HttpPost httppost = new HttpPost(WexinPayConfig.wx_transfers_url);
//                //这里要设置编码，不然xml中有中文的话会提示签名失败或者展示乱码
//                httppost.addHeader("Content-Type", "text/xml");
//                StringEntity se = new StringEntity(xml, "UTF-8");
//                httppost.setEntity(se);
//                CloseableHttpResponse responseEntry = httpclient.execute(httppost);
//                try {
//                    HttpEntity entity = responseEntry.getEntity();
//                    if (entity != null) {
//                        System.out.println("响应内容长度 : " + entity.getContentLength());
//                        System.out.println("响应 : " + entity);
//                        SAXReader saxReader = new SAXReader();
//                        Document document = saxReader.read(entity.getContent());
//                        Element rootElt = document.getRootElement();
//                        String resultCode = rootElt.elementText("result_code");
//                        if (resultCode.equals("SUCCESS")) {
//                            System.out.println("响应内容 : " + resultCode);
//                            //保存数据业务逻辑
//                            return ResultVOUtils.success(resultCode);
//                        } else {
//                            System.out.println(rootElt.elementText("err_code_des"));
//                            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,rootElt.elementText("err_code_des"));
//                        }
//                    }
//                    EntityUtils.consume(entity);
//                } catch (Exception e) {
//                    System.out.println("请求失败");
//                    return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"转账失败！");
//                } finally {
//                    responseEntry.close();
//                }
//            } catch (Exception e) {
//                System.out.println("请求失败");
//                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"转账失败！");
//            }
//            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"转账失败！");
//        }
//
//
//    public static String wxSendWallet( String openid,String spbill_create_ip) {
//        String data = null;
//        try {
//            String nonceStr = RandomUtils.getRandomStr();
//            //SortedMap接口主要提供有序的Map实现，默认的排序是根据key值进行升序排序
//            SortedMap<Object,Object> parameters = new TreeMap<>();
//            parameters.put("mch_appid",WexinPayConfig.wx_appid );
//            parameters.put("mchid", WexinPayConfig.wx_mchid);
//            parameters.put("nonce_str", nonceStr);
//            String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
//            parameters.put("partner_trade_no", outTradeNo);
//            parameters.put("openid", "olO37vgr9-Wydc4YaXIBFSkObz-0");
//            parameters.put("openid", openid);
//            parameters.put("check_name", "NO_CHECK");
//            parameters.put("amount", "101");
//            parameters.put("spbill_create_ip",  spbill_create_ip);
//            parameters.put("desc", "福利红包");
//            parameters.put("key", WexinPayConfig.wx_apikey);
//            //签名
//            String sign = SignUtil.createSign("UTF-8", parameters);
//            parameters.put("sign", sign);
//            // 调用接口所需要的参数转成xml格式
//            data = SignUtil.getRequestXml(parameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//
////    /**
////     * 企业向个人转账查询
////     * @param request
////     * @param response
////     * @param tradeno 商户转账订单号
////     * @param callback
////     */
////    @RequestMapping(value = "/pay/query", method = RequestMethod.POST)
////    public void orderPayQuery(HttpServletRequest request, HttpServletResponse response, String tradeno,
////                              String callback) {
////        LOG.info("[/transfer/pay/query]");
////        if (StringUtil.isEmpty(tradeno)) {
////            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
////                    .toJSONString(new JsonResult(-1, "转账订单号不能为空", new ResponseData()), SerializerFeatureUtil.FEATURES)));
////        }
////
////        Map<String, String> restmap = null;
////        try {
////            Map<String, String> parm = new HashMap<String, String>();
////            parm.put("appid", APP_ID);
////            parm.put("mch_id", MCH_ID);
////            parm.put("partner_trade_no", tradeno);
////            parm.put("nonce_str", PayUtil.getNonceStr());
////            parm.put("sign", PayUtil.getSign(parm, API_SECRET));
////
////            String restxml = HttpUtils.posts(TRANSFERS_PAY_QUERY, XmlUtil.xmlFormat(parm, true));
////            restmap = XmlUtil.xmlParse(restxml);
////        } catch (Exception e) {
////            LOG.error(e.getMessage(), e);
////        }
////
////        if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
////            // 订单查询成功 处理业务逻辑
////            LOG.info("订单查询：订单" + restmap.get("partner_trade_no") + "支付成功");
////            Map<String, String> transferMap = new HashMap<>();
////            transferMap.put("partner_trade_no", restmap.get("partner_trade_no"));//商户转账订单号
////            transferMap.put("openid", restmap.get("openid")); //收款微信号
////            transferMap.put("payment_amount", restmap.get("payment_amount")); //转账金额
////            transferMap.put("transfer_time", restmap.get("transfer_time")); //转账时间
////            transferMap.put("desc", restmap.get("desc")); //转账描述
////            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
////                    .toJSONString(new JsonResult(1, "订单转账成功", new ResponseData(null, transferMap)), SerializerFeatureUtil.FEATURES)));
////        }else {
////            if (CollectionUtil.isNotEmpty(restmap)) {
////                LOG.info("订单转账失败：" + restmap.get("err_code") + ":" + restmap.get("err_code_des"));
////            }
////            WebUtil.response(response, WebUtil.packJsonp(callback, JSON
////                    .toJSONString(new JsonResult(-1, "订单转账失败", new ResponseData()), SerializerFeatureUtil.FEATURES)));
////        }
////    }
//}
