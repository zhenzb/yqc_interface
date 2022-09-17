package com.youqiancheng.controller.wechatpay;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.wechatpay.weixinpay.UniCallbackResData;
import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
import com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil;
import com.youqiancheng.controller.wechatpay.weixinpay.util.RandomUtils;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.service.client.service.D02OrderItemClientService;
import com.youqiancheng.service.client.service.D06PayOrderClientService;
import com.youqiancheng.vo.client.D01OrderClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述: <br>
 * 〈微信退款——用于后台直接调用〉

 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */
@Component
public class WeiXinRefundUtil {
    protected static final Log logger = LogFactory.getLog(WeiXinRefundUtil.class);

    @Resource
    private D06PayOrderClientService d06PayOrderClientService;
    @Autowired
    private D02OrderItemClientService d02OrderItemAppService;
    @Autowired
    private D01OrderClientService d01OrderClientService;

    public ResultVo weixinRefund1(Long id) {
        logger.info("微信退款获取签名请求参数【{orderId = " + id + "}】");
        /************业务流程调用数据*****************/
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        //查询售后信息
        D03CustomerServiceDO customService = d06PayOrderClientService.getCustomService(id);
        //支付时微信支付流水单据号
        String orderNo=customService.getTradeNo();
        //退款金额
        BigDecimal  money=customService.getMoney();
        //退款单号
        String refundNo=customService.getServiceNo();
        //查询支付订单信息
        D06PayOrderDO payOrderByItemId = d06PayOrderClientService.getPayOrderByItemId(customService.getOrderItemId());
        //订单总金额
        BigDecimal totalMoney=payOrderByItemId.getOrderPrice();
        /*************退款流程************/
        try {
            // 获取请求参数
            //UniCallbackResData res= SignUtil.getUnifiedOrderResultForRefund(orderNo,refundNo,totalMoney.toString(),money.toString());
            UniCallbackResData res= SignUtil.getUnifiedOrderResultForRefund(orderNo,refundNo,"0.01","0.01");
            if (res.getReturn_code().equals("SUCCESS")) {
                /***保存退款信息*****/
                customService.setRefundType(StatusConstant.payType.Wechat.getCode());
                customService.setRefundNo(res.getRefund_id());
                customService.setStatus(StatusConstant.CustomServiceStatus.refund.getCode());
                d06PayOrderClientService.updateCustomService(customService);
            } else {
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款失败！"+res.getReturn_msg());
            }
        } catch (Exception e) {
            logger.error("退款失败！", e);
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款失败！"+e.getMessage());
        }
        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款失败！");
    }

    public ResultVo weixinRefund(Long id) {
        logger.info("微信退款获取签名请求参数【{orderId = " + id + "}】");
        /************业务流程调用数据*****************/
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        //查询售后信息
        D03CustomerServiceDO customService = d06PayOrderClientService.getCustomService(id);
        //支付时微信支付流水单据号
        String orderNo=customService.getTradeNo();
        //退款金额
        BigDecimal  money=customService.getMoney();
        //退款单号
        String refundNo=customService.getServiceNo();
        //查询支付订单信息
        D06PayOrderDO payOrderByItemId = d06PayOrderClientService.getPayOrderByItemId(customService.getOrderItemId());
        //订单总金额
        BigDecimal totalMoney=payOrderByItemId.getOrderPrice();
        /*************退款流程************/
        try {
            //订单总金额、退款金额、系统订单号、退款单号（同一笔支付订单可分多次退款，该单号为系统中的退款单号）
            Map<String, Object> res = WeiXinPayRefund.refundWeiXinPay(String.valueOf(money), String.valueOf(money), "", orderNo, refundNo);
            if ((boolean) res.get("b"))  //退款成功
            {
                /***保存退款信息*****/
                customService.setRefundType(StatusConstant.payType.Wechat.getCode());
                //customService.setRefundNo(res.get("refund_id").toString());
                customService.setStatus(StatusConstant.CustomServiceStatus.refund.getCode());
                d06PayOrderClientService.updateCustomService(customService);
                D02OrderItemDO d02OrderItemDO = d02OrderItemAppService.get(customService.getOrderItemId());
                d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
                d02OrderItemAppService.update(d02OrderItemDO);
                //D01OrderClientVO d01OrderClientVO = d01OrderClientService.get(customService.getOrderId());
//                D01OrderDO d01OrderDO = new D01OrderDO();
//                d01OrderDO.setId(d01OrderClientVO.getId());
//                d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
//                d01OrderClientService.update(d01OrderDO);
                return ResultVOUtils.success("退款成功！");
            } else {
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款失败！"+res.get("errorCod").toString());
            }
        } catch (Exception e) {
            logger.error("退款失败！", e);
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"退款失败！"+e.getMessage());
        }

    }




    //	https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers


    /**
     * 功能描述: <br>
     * 〈转账〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */

    public ResultVo weixinTransfer(Long id,String ipAddr,String openId) {
        logger.info("微信转账获取签名请求参数【{orderId = " + id + "}】");
        /************业务流程调用数据*****************/
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        //提现申请信息
        F06WithdrawalApplicationDO transferAccountsInfo = d06PayOrderClientService.getTransferAccountsInfo(id);

        //金额
        BigDecimal  money=transferAccountsInfo.getActualWithdrawalMoney();
        //退款单号
        String orderNo=transferAccountsInfo.getOrderNo();
        /*************转账流程************/
        //创建一个唯一订单号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        String orderId = time + (int) (Math.random() * 1000000);
        String xml = wxSendWallet(openId,ipAddr,transferAccountsInfo.getActualWithdrawalMoney().toString());
        try {
            //指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //windows系统
            //FileInputStream instream = new FileInputStream(new File("D:\\youqiancheng\\src\\youqiancheng\\resources\\cert\\apiclient_cert.p12"));
            //linux系统,读取本机存放的PKCS12证书文件
            //InputStream instream = WeiXinRefundUtil.class.getClassLoader().getResourceAsStream("");
            InputStream instream = WeiXinRefundUtil.class.getClassLoader().getResourceAsStream("apiclient_cert.p12");

            try {
                //指定PKCS12的密码(商户ID)
                //keyStore.load(instream, accountUtil.getWxPartnerId().toCharArray());
                keyStore.load(instream,WexinPayConfig.wx_mchid.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WexinPayConfig.wx_mchid.toCharArray()).build();
            //指定TLS版本, Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            //设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost(WexinPayConfig.wx_transfers_url);
            //这里要设置编码，不然xml中有中文的话会提示签名失败或者展示乱码
            httppost.addHeader("Content-Type", "text/xml");
            StringEntity se = new StringEntity(xml, "UTF-8");
            httppost.setEntity(se);

            CloseableHttpResponse responseEntry = httpclient.execute(httppost);
            try {
                HttpEntity entity = responseEntry.getEntity();
                if (entity != null) {
                    System.out.println("响应内容长度 : " + entity.getContentLength());
                    System.out.println("响应 : " + entity);
                    SAXReader saxReader = new SAXReader();
                    Document document = saxReader.read(entity.getContent());
                    Element rootElt = document.getRootElement();
                    String resultCode = rootElt.elementText("result_code");
                    if (resultCode.equals("SUCCESS")) {
                        System.out.println("响应内容 : " + resultCode);
                        /***保存转账信息*****/
                        //transferAccountsInfo.setTransferNo(res.getPayment_no());
                        transferAccountsInfo.setStatus(StatusConstant.TransferStatus.transfer.getCode());
                        d06PayOrderClientService.updateTransfer(transferAccountsInfo);
                        return ResultVOUtils.success(resultCode);
                        //保存数据业务逻辑
                    } else {
                        System.out.println(rootElt.elementText("err_code_des"));
                        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"转账失败！"+rootElt.elementText("err_code_des"));
                    }
                }
                EntityUtils.consume(entity);
            } catch (Exception e) {
                System.out.println("请求失败");
            } finally {
                responseEntry.close();
            }
        } catch (Exception e) {
            System.out.println("请求失败");
        }
        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"转账失败！");
    }

    public static String wxSendWallet( String openid,String spbill_create_ip,String amount) {
        String data = null;
        try {
            String nonceStr = RandomUtils.getRandomStr();
            //SortedMap接口主要提供有序的Map实现，默认的排序是根据key值进行升序排序
            SortedMap<Object,Object> parameters = new TreeMap<>();
            parameters.put("mch_appid",WexinPayConfig.wx_appid );
            parameters.put("mchid", WexinPayConfig.wx_mchid);
            parameters.put("nonce_str", nonceStr);
            String outTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
            parameters.put("partner_trade_no", outTradeNo);
            //parameters.put("openid", "olO37vgr9-Wydc4YaXIBFSkObz-0");
            parameters.put("openid", openid);
            parameters.put("check_name", "NO_CHECK");
            BigDecimal t = new BigDecimal(amount);
            String amount_s = t.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
            //parameters.put("amount", amount_s);
            parameters.put("amount", "1");
            parameters.put("spbill_create_ip",  spbill_create_ip);
            parameters.put("desc", "福利红包");
            parameters.put("key", WexinPayConfig.wx_apikey);
            //签名
            String sign = SignUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 调用接口所需要的参数转成xml格式
            data = SignUtil.getRequestXml(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
