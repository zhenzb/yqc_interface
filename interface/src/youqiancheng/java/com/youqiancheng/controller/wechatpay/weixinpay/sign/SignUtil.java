package com.youqiancheng.controller.wechatpay.weixinpay.sign;


import com.thoughtworks.xstream.XStream;
import com.youqiancheng.controller.wechatpay.weixinpay.util.MD5;
import com.youqiancheng.controller.wechatpay.weixinpay.UniCallbackResData;
import com.youqiancheng.controller.wechatpay.weixinpay.UniCallbackTransferData;
import com.youqiancheng.controller.wechatpay.weixinpay.UnifiedOrderApp;
import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
import com.youqiancheng.controller.wechatpay.weixinpay.util.RandomUtils;
import com.youqiancheng.util.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author Captain Ren
 * @Description TODO
 * @Date 2019/4/9 0009 12:51
 * @Param
 * @return
 **/

public class SignUtil
{
    protected static final Log logger = LogFactory.getLog(SignUtil.class);
    /**
     * @Description：返回给微信的参数
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }


    /**
     * 描述:格式化日期方法
     * **/
    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String time = formatter.format(date);
        return time;
    }

    /**
     * 将日期字符串格式化为另一种格式字符串
     * @param dateStr
     * @param sourceFormat
     * @param distFormat
     * @return
     */
    public static String formatDateStrToDistStr(String dateStr, String sourceFormat, String distFormat){
        SimpleDateFormat format = new SimpleDateFormat(sourceFormat);
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatDate(date, distFormat);
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> obj2Map(Object obj)
    {

        Map<String, Object> map = new HashMap<String, Object>();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++)
        {
            String varName = fields[i].getName();
            varName = varName.toLowerCase();//将key置为小写，默认为对象的属性

            try
            {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null)
                    map.put(varName, o.toString());
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            }
            catch (IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
            catch (IllegalAccessException ex)
            {
                ex.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 是否微信支付签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(Map map, String signOld) {
        SortedMap parameters = new TreeMap(map);
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + WexinPayConfig.wx_apikey);
        logger.info("回调签名内容为：" + sb.toString());
        // 算出签名
        String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toLowerCase();
        String tenpaySign = signOld.toLowerCase();
        return tenpaySign.equals(sign);
    }

    /**
     *统一支付接口
     */
    public static UnifiedOrderApp getUnifiedOrderResultForApp(HttpServletRequest request, String notifyUrl,String attach, String body,String out_trade_no, String total_fee ) throws Exception {
        UnifiedOrderApp app = new UnifiedOrderApp();
        String nonceStr = RandomUtils.getRandomStr();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        SortedMap<Object, Object> parameters = new TreeMap<>();
        parameters.put("appid", WexinPayConfig.wx_appid);
        parameters.put("mch_id", WexinPayConfig.wx_mchid);
        parameters.put("nonce_str", nonceStr);
        parameters.put("body", body);
        parameters.put("attach", attach);
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("notify_url", notifyUrl);
        parameters.put("out_trade_no", out_trade_no);
        BigDecimal t = new BigDecimal(total_fee);
        String total_s = t.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
        parameters.put("total_fee", total_s);
        parameters.put("trade_type", "APP");



        // 调用统一支付接口的签名
        String sign = createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        logger.info("签名为："  + sign);
        // 调用接口所需要的参数转成xml格式
        String requestXML = getRequestXml(parameters);
        // 调用接口
        String result = httpsRequest(WexinPayConfig.wx_unifiedoroer_url, "POST", requestXML);
        logger.info("out_trade_no=" + out_trade_no + ";----->" + result);
        // 解析微信返回的信息，以对象形式存储便于取值
        UniCallbackResData uniCallbackResData = (UniCallbackResData) getObjectFromXML(result, UniCallbackResData.class);
        String prepayid = uniCallbackResData.getPrepay_id();
        SortedMap<Object, Object> params = new TreeMap<Object, Object>();
        params.put("appid", WexinPayConfig.wx_appid);
        params.put("partnerid", WexinPayConfig.wx_mchid);
        params.put("prepayid", prepayid);
        params.put("package", "Sign=WXPay");
        params.put("noncestr", nonceStr);
        params.put("timestamp", timeStamp);
        // 发起支付的paySign签名
        String paySign = createSign("UTF-8", params);
        app.setAppid(WexinPayConfig.wx_appid);
        app.setPartnerid(WexinPayConfig.wx_mchid);
        app.setPrepayid(prepayid);
        app.setPackagestr("Sign=WXPay");
        app.setNoncestr(nonceStr);
        app.setTimestamp(timeStamp);
        app.setSign(paySign);
        return app;

    }
    public static String getUnifiedOrderResultForPC(HttpServletRequest request, String notifyUrl,String attach, String body,String out_trade_no, String total_fee) throws Exception {
        String nonceStr = RandomUtils.getRandomStr();
        SortedMap<Object, Object> parameters = new TreeMap<>();
        parameters.put("appid", WexinPayConfig.wx_appid);
        parameters.put("mch_id", WexinPayConfig.wx_mchid);
        parameters.put("nonce_str", nonceStr);
        parameters.put("body", body);
        parameters.put("attach", attach);
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        parameters.put("notify_url", notifyUrl);
        parameters.put("out_trade_no", out_trade_no);
        BigDecimal t = new BigDecimal(total_fee);
        String total_s = t.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
        parameters.put("total_fee", total_s);
        parameters.put("trade_type", "NATIVE");



        // 调用统一支付接口的签名
        String sign = createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        logger.info("签名为："  + sign);
        // 调用接口所需要的参数转成xml格式
        String requestXML = getRequestXml(parameters);
        // 调用接口
        String result = httpsRequest(WexinPayConfig.wx_unifiedoroer_url, "POST", requestXML);
        logger.error("out_trade_no=" + out_trade_no + ";----->" + result);
        // 解析微信返回的信息，以对象形式存储便于取值
        UniCallbackResData uniCallbackResData = (UniCallbackResData) getObjectFromXML(result, UniCallbackResData.class);
        String codeUrl = uniCallbackResData.getCode_url();
        return codeUrl;

    }

    //退款
    public static UniCallbackResData getUnifiedOrderResultForRefund(String transactionId,String out_refund_no, String totalFee,String refundFee) throws Exception {
        String nonceStr = RandomUtils.getRandomStr();
        SortedMap<Object, Object> parameters = new TreeMap<>();
        parameters.put("appid", WexinPayConfig.wx_appid);
        parameters.put("mch_id", WexinPayConfig.wx_mchid);
        parameters.put("nonce_str", nonceStr);
        //微信订单号——与商家订单号二选一
        parameters.put("transaction_id", transactionId);
        parameters.put("out_refund_no", out_refund_no);
        BigDecimal total_fee = new BigDecimal(totalFee);
        String total_s = total_fee.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
        BigDecimal refund_fee = new BigDecimal(refundFee);
        String refund_s = refund_fee.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
        parameters.put("total_fee", total_s);
        parameters.put("refund_fee", refund_s);

        // 调用统一支付接口的签名
        String sign = createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        logger.info("签名为"  + sign);
        // 调用接口所需要的参数转成xml格式
        String requestXML = getRequestXml(parameters);
        // 调用接口
        String result = httpsRequest(WexinPayConfig.wx_refund_url, "POST", requestXML);
        logger.error("退款请求结果"+"----->" + result);
        // 解析微信返回的信息，以对象形式存储便于取值
        UniCallbackResData uniCallbackResData = (UniCallbackResData) getObjectFromXML(result, UniCallbackResData.class);
        return uniCallbackResData;

    }

    //转账
    public static UniCallbackTransferData getUnifiedOrderResultForTransfers(String tradeNo,String openid,String amount) throws Exception {
        String nonceStr = RandomUtils.getRandomStr();
        SortedMap<Object, Object> parameters = new TreeMap<>();
        parameters.put("mch_appid", WexinPayConfig.wx_appid);
        parameters.put("mchid", WexinPayConfig.wx_mchid);
        parameters.put("nonce_str", nonceStr);
        parameters.put("partner_trade_no", tradeNo);//商户订单号，需保持唯一性
        parameters.put("openid", openid);//用户openid
        parameters.put("check_name", "NO_CHECK");
        parameters.put("desc", "转账");
        BigDecimal namount = new BigDecimal(amount);
        String amountstr = namount.multiply(new BigDecimal(100)).toString().replace(".00", "").replace(".0", "");
        parameters.put("amount", amountstr);

        // 调用统一支付接口的签名
        String sign = createSign("UTF-8", parameters);
        parameters.put("sign", sign);
        logger.info("签名为："  + sign);
        // 调用接口所需要的参数转成xml格式
        String requestXML = getRequestXml(parameters);
        // 调用接口
        String result = httpsRequest(WexinPayConfig.wx_transfers_url, "POST", requestXML);
        logger.error("partner_trade_no=" + tradeNo + ";----->" + result);
        // 解析微信返回的信息，以对象形式存储便于取值
        UniCallbackTransferData uniCallbackTransferData = (UniCallbackTransferData) getObjectFromXML(result, UniCallbackResData.class);
        return uniCallbackTransferData;

    }

    public static Object getObjectFromXML(String xml, Class<?> tClass) {
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = new XStream();
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    }

    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return null;
    }

    /**
     * @Description：将请求参数转换为xml格式的string
     * @param parameters  请求参数
     * @return
     */
    public static String getRequestXml(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
            }else {
                sb.append("<"+k+">"+v+"</"+k+">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * @Description：sign签名
     * @param characterEncoding 编码格式
     * @param parameters 请求参数
     * @return
     */
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WexinPayConfig.wx_apikey);
        logger.info("签名内容为：" + sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }


    //方法重载,也是一种签名
    public static String createSign(Map<String,String> parameters){
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (!key.equals("key")){
                String value = parameters.get(key);
                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }

        }

        prestr+="&key="+parameters.get("key");
        System.out.println("签名字符串:"+prestr.toString());
        String sign = DigestUtils.md5Hex(prestr).toUpperCase();
        return sign;
    }

    //方法重载,也是一种签名
    public static String buildSign(Map<String,String> parameters) throws Exception {
        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (!key.equals("key")){
                String value = parameters.get(key);
                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }

        }

        prestr+="&secretKey="+"8c62fce9c6384df7a9b5bbfd52d63d7b"; //正式环境
       // prestr+="&secretKey="+"f27ee2fd7849433da20cff0e4ab1bf97"; //测试环境
        System.out.println("签名字符串:"+prestr.toString());
        String sign = HmacSHA256(prestr,"8c62fce9c6384df7a9b5bbfd52d63d7b");
        return sign;
    }

    public static String HmacSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }

    public static String sign(SortedMap<Object, Object> map)
    {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<Object, Object> entry : map.entrySet())
        {
            if (entry.getValue() != "")
            {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WexinPayConfig.wx_apikey;
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String randomStringByLength(int length)
    {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

private static final String yeYunUrl = "https://vendor.cococc888.com/realNameSign/signing";
    private static final String yeYunOrderUrl = "https://vendor.cococc888.com/order/newSubmit";
    public static void main(String[] args){

       // signContract();
        order();
    }

    //签约测试
    public static void signContract(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        logger.info(format);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("merchantId","2021041318332983");
        parameters.put("uid","1");
        parameters.put("name","甄卓彬");
        parameters.put("bankCardNum","6212260200107585248");
        parameters.put("idCard","130125199002187036");
        parameters.put("mobile","13520289150");
        parameters.put("signingTime",format);
        parameters.put("signingStatus","1");
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("jsonObject:"+jsonObject);

        String s = HttpUtils.sendPost(jsonObject, yeYunUrl);

        JSONObject JSONResult = JSONObject.fromObject(s);
        System.out.println(JSONResult.getInt("code")+"------------"+JSONResult.getString("msg"));
    }

//    public static String getAsciiSort(Map<String, Object> map) {
//        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
//        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
//        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
//            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
//                return ((String) o1.getKey()).compareToIgnoreCase((String) o2.getKey());
//            }
//        });
//    }

//reult:{"code":1001,"msg":"请求成功","data":{"requestTime":"20210509175027","orderId":"02105091750291391329720738148352","orderNum":"123456987","taskNumber":"RW20210506222357752279"}}
    public static void order(){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        logger.info("请求时间："+format);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("merchantId","2021041318332983");
        parameters.put("orderNum","123444556678");
        parameters.put("taskNumber","RW20210618215757745542");
        parameters.put("name","甄卓彬");
        parameters.put("bankCardNum","6212260200107585248");
        parameters.put("idCard","130125199002187036");
        parameters.put("mobile","13520289150");
        parameters.put("requestTime",format);
        parameters.put("expense","50");
        parameters.put("remark","测试下单");
        String sign = "";
        try {
            sign = buildSign(parameters);
        }catch (Exception e){
            e.printStackTrace();
        }
        parameters.put("sign",sign);
        logger.info("sign: "+sign);

        JSONObject jsonObject = JSONObject.fromObject(parameters);
        logger.info("jsonObject:"+jsonObject);

        String s = HttpUtils.sendPost(jsonObject, yeYunOrderUrl);

        JSONObject JSONResult = JSONObject.fromObject(s);
        System.out.println(JSONResult.getInt("code")+"------------"+JSONResult.getString("msg"));
    }
}
