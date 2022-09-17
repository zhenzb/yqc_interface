package com.youqiancheng.controller.wechatpay;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.youqiancheng.controller.wechatpay.weixinpay.RefundReqData;
import com.youqiancheng.controller.wechatpay.weixinpay.RefundResData;
import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
import com.youqiancheng.controller.wechatpay.weixinpay.util.HttpsRequest;
import com.youqiancheng.controller.wechatpay.weixinpay.util.MD5;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Captain Ren
 * @Description TODO
 * @Date 2019/4/16 0016 16:01
 * @Param
 * @return
 **/
@Slf4j
public class WeiXinPayRefund
{
    /**
     * 微信退款
     * @param total_fee 订单总金额
     * @param refund_fee 退款金额
     * @param out_trade_no 系统订单号
     * @param out_refund_no 退款单号（同一笔支付订单可分多次退款，该单号为系统中的退款单号）
     */
    public static Map<String, Object> refundWeiXinPay(String total_fee, String refund_fee, String out_trade_no, String trade_no, String out_refund_no)
    {
        boolean b = false;
        Integer total_fee_int = null;
        Integer refund_fee_int = null;
        try
        {
            BigDecimal total  = new BigDecimal(total_fee).multiply(new BigDecimal(String.valueOf(100)));
            BigDecimal refund  = new BigDecimal(refund_fee).multiply(new BigDecimal(String.valueOf(100)));
            total_fee_int = total.intValue();
            refund_fee_int = refund.intValue();
            if (total_fee_int == null || total_fee_int <= 0 || refund_fee_int <= 0)
            {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e)
        {
            log.info("参数错误：" + e.getMessage());
        }

        String mchID =  WexinPayConfig.wx_mchid;

        String errorCod = "";
        Map<String, Object> map = new HashMap<>();
        RefundReqData postrefunddata = new RefundReqData(WexinPayConfig.wx_appid, trade_no, out_trade_no, "", out_refund_no, total_fee_int, refund_fee_int, mchID, "CNY");
        log.info("提交的微信支付的退单参数：\n" + JSON.toJSONString(postrefunddata));
        RefundResData rsModel = refundOrder(postrefunddata);
        log.info("微信支付的下单返回的数据：\n" + rsModel == null ? "null" : rsModel.toString());
        if (rsModel != null && "SUCCESS".equals(rsModel.getReturn_code()))
        {
            if ("SUCCESS".equals(rsModel.getResult_code()))
            {
                b = true;
            }
            else
            {
                b = false;
                errorCod = rsModel.getErr_code_des();
            }
        }
        else
        {
            b= false;
            errorCod = rsModel.getErr_code_des();
        }
        map.put("b", b);
        map.put("errorCod", errorCod);
        return map;
    }
    /**
     * 微信转账
     */
    public static Map<String, Object> TranferWeiXinPay(String total_fee, String refund_fee, String out_trade_no, String trade_no, String out_refund_no)
    {
        boolean b = false;
        Integer total_fee_int = null;
        Integer refund_fee_int = null;
        try
        {
            BigDecimal total  = new BigDecimal(total_fee).multiply(new BigDecimal(String.valueOf(100)));
            BigDecimal refund  = new BigDecimal(refund_fee).multiply(new BigDecimal(String.valueOf(100)));
            total_fee_int = total.intValue();
            refund_fee_int = refund.intValue();
            if (total_fee_int == null || total_fee_int <= 0 || refund_fee_int <= 0)
            {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e)
        {
            log.info("参数错误：" + e.getMessage());
        }

        String mchID =  WexinPayConfig.wx_mchid;

        String errorCod = "";
        Map<String, Object> map = new HashMap<>();
        RefundReqData postrefunddata = new RefundReqData(WexinPayConfig.wx_appid, trade_no, out_trade_no, "", out_refund_no, total_fee_int, refund_fee_int, mchID, "CNY");
        log.info("提交的微信支付的退单参数：\n" + JSON.toJSONString(postrefunddata));
        RefundResData rsModel = refundOrder(postrefunddata);
        log.info("微信支付的下单返回的数据：\n" + rsModel == null ? "null" : rsModel.toString());
        if (rsModel != null && "SUCCESS".equals(rsModel.getReturn_code()))
        {
            if ("SUCCESS".equals(rsModel.getResult_code()))
            {
                b = true;
            }
            else
            {
                b = false;
                errorCod = rsModel.getErr_code_des();
            }
        }
        else
        {
            b= false;
            errorCod = rsModel.getErr_code_des();
        }
        map.put("b", b);
        map.put("errorCod", errorCod);
        return map;
    }


    public static RefundResData refundOrder(RefundReqData postdata)
    {
        String url = WexinPayConfig.wx_refund_url;
        RefundResData rsModel = null;

        try
        {
            HttpsRequest hsq = null;
            try
            {
                hsq = new HttpsRequest();
            }
            catch (Exception e)
            {
                log.info("微信退款请求异常：" + e.getMessage());
                e.printStackTrace();
            }

            String result = hsq.sendPost(url, postdata);

            log.info("微信支付退单返回值：" + result);

            rsModel = (RefundResData) getObjectFromXML(result, RefundResData.class);
        }
        catch (UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e)
        {
            log.info("微信支付退单出错！：" + e.getMessage());
            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("微信支付退单出错：" + e.getMessage());
            return null;
        }
        return rsModel;
    }

    //解析微信返回的xml数据
    public static Object getObjectFromXML(String xml, Class<?> tClass) {
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream(new DomDriver());
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    }

    //获取签名
    public static String getSign(Map<String,Object> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WexinPayConfig.wx_apikey;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }
}
