package com.wechat.v33.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import com.wechat.v33.confing.WechatPayConfig;
import com.wechat.v33.wxenum.CombinePayUrlEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @ClassName PayService
 * @Description TODO
 * @Author zzb
 * @Date 2023/1/18 11:31
 * @Version 1.0
 */
//@Api(tags = "合单支付接口(API3)")
//@RestController
//@RequestMapping("/combine")
@Slf4j
public class PayService {

    @Resource
    private WechatPayConfig wechatPayConfig;

    @Resource
    private WechatPayRequest wechatPayRequest;

    /**
     * 无需应答签名
     */
    @Resource
    private CloseableHttpClient wxPayNoSignClient;

    // 公众号APPID
    private static final String WX_JSAPI_SUB_APPID = "xxxxxxxx";
    // 小程序APPID
    private static final String WX_JSAPI_MINI_SUB_APPID = "xxxxxxxx";
    // 模拟特约商户子商户号，实际情况：应该写在表中，与商家表进行映射，根据订单动态获取
    private static  final String subMchId ="xxx";

    /**
     * type：h5、jsapi、app、native、sub_jsapi
     * H5支付时，需要在商户平台--"产品中心"--"开发配置"自行配置支付提交的域名
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "统一下单-统一接口", notes = "统一下单-统一接口")
    //@GetMapping("/transactions")
    public Map<String, Object> transactions(String type) {
        int combineTradeNo = new Random().nextInt(999999999);
        log.info("统一下单API，支付方式：{}，合单订单号：{}", type,combineTradeNo);

        // 统一参数封装
        Map<String, Object> params = new HashMap<>(8);
        params.put("combine_appid", wechatPayConfig.getAppId());
        params.put("combine_mchid", wechatPayConfig.getMchId());
        // 合单商户订单号
        params.put("combine_out_trade_no", combineTradeNo+"");
        params.put("notify_url", wechatPayConfig.getCombineNotifyUrl());

        // 场景信息
        Map<String, Object> sceneInfoMap = new HashMap<>(4);
        // 客户端IP
        sceneInfoMap.put("payer_client_ip", "127.0.0.1");
        // 商户端设备号（门店号或收银设备ID）
        sceneInfoMap.put("device_id", "127.0.0.1");

        // 除H5与JSAPI有特殊参数外，其他的支付方式都一样
        if (type.equals(CombinePayUrlEnum.H5.getType())) {

            Map<String, Object> h5InfoMap = new HashMap<>(4);
            // 场景类型:iOS, Android, Wap
            h5InfoMap.put("type", "IOS");
            sceneInfoMap.put("h5_info", h5InfoMap);
        } else if (type.equals(CombinePayUrlEnum.JSAPI.getType()) || type.equals(CombinePayUrlEnum.SUB_JSAPI.getType())) {
            Map<String, Object> payerMap = new HashMap<>(4);
            // 用户在服务商appid下的唯一标识，测试使用默认值 o6wzFwzBMdZDr-2VgR_ZsB1d0mYk（公众号应用的）
            // 小程序下的openId暂无，所以无法测试
            // 实际情况，应该是由前端获取openId值
            payerMap.put("sub_openid", "o6wzFwzBMdZDr-2VgR_ZsB1d0mYk");
            params.put("combine_payer_info", payerMap);
        }

        params.put("scene_info", sceneInfoMap);

        // TODO 模拟两个订单
        List<Map<String, Object>> subOrders = new ArrayList<>();
        for (int a=1;a<=2;a++) {
            // 子单信息
            Map<String, Object> subOrderMap = new HashMap<>(4);
            if (type.equals(CombinePayUrlEnum.JSAPI.getType()) || type.equals(CombinePayUrlEnum.SUB_JSAPI.getType())) {
                // TODO 小程序或者公众号支付时，需要传入子应用的appId，并且在服务商平台的特约商户管理中进行授权
                // 判断是否小程序还是服务号支付
                String subAppId = WX_JSAPI_SUB_APPID;
                if (type.equals(CombinePayUrlEnum.SUB_JSAPI.getType())) {
                    // 发起JSAPI的小程序
                    subAppId = WX_JSAPI_MINI_SUB_APPID;
                }

                // 子商户申请的应用ID，若combine_payer_info参数中sub_openid有传的情况下，sub_appid必填
                subOrderMap.put("sub_appid", subAppId);
            }

            int outTradeNo = new Random().nextInt(999999999);
            // 子单商户订单号
            subOrderMap.put("out_trade_no", outTradeNo + "");
            // 发起商户号
            subOrderMap.put("mchid",wechatPayConfig.getMchId());
            // 	二级商户商户号(特约商户)，由微信支付生成并下发。
            subOrderMap.put("sub_mchid",subMchId);
            // 附加信息
            subOrderMap.put("attach", "附加信息");
            // 商品描述
            subOrderMap.put("description", "测试商品");
            // 金额信息
            Map<String, Object> amountMap = new HashMap<>(4);
            // 金额单位为分
            amountMap.put("total_amount", 1);
            amountMap.put("currency", "CNY");
            subOrderMap.put("amount", amountMap);

            // 结算信息
            Map<String, Object> settleInfoMap = new HashMap<>(4);
            settleInfoMap.put("profit_sharing",false);
            // 单笔订单最高补差金额
            settleInfoMap.put("subsidy_amount",5000);
            subOrderMap.put("settle_info", settleInfoMap);

            // 子订单列表
            subOrders.add(subOrderMap);
        }
        params.put("sub_orders",subOrders);

        String paramsStr = JSON.toJSONString(params);
        log.info("请求参数 ===> {}" + paramsStr);

        // 重写type值，因为小程序会多一个下划线(sub_type)
        String[] split = type.split("_");
        String newType = split[split.length - 1];
        String resStr = wechatPayRequest.wechatHttpPost(wechatPayConfig.getBaseUrl().concat(CombinePayUrlEnum.COMBINE_TRANSACTIONS.getType().concat(newType)), paramsStr);
        Map<String, Object> resMap = JSONObject.parseObject(resStr, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> signMap = paySignMsg(resMap, type);
        resMap.put("type", type);
        resMap.put("signMap", signMap);
        return resMap;
    }

    private Map<String, Object> paySignMsg(Map<String, Object> map, String type) {
        // 设置签名信息,Native与H5不需要
        if (type.equals(CombinePayUrlEnum.H5.getType()) || type.equals(CombinePayUrlEnum.NATIVE.getType())) {
            return null;
        }

        long timeMillis = System.currentTimeMillis();
        String appId = wechatPayConfig.getAppId();
        String timeStamp = timeMillis / 1000 + "";
        String nonceStr = timeMillis + "";
        String prepayId = map.get("prepay_id").toString();
        String packageStr = "prepay_id=" + prepayId;

        // 公共参数
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("nonceStr", nonceStr);
        resMap.put("timeStamp", timeStamp);

        // JSAPI、SUB_JSAPI(小程序)
        if (type.equals(CombinePayUrlEnum.JSAPI.getType()) || type.equals(CombinePayUrlEnum.SUB_JSAPI.getType())) {
            resMap.put("appId", appId);
            resMap.put("package", packageStr);
            // 使用字段appId、timeStamp、nonceStr、package进行签名
            String paySign = createSign(resMap);
            resMap.put("paySign", paySign);
            resMap.put("signType", "HMAC-SHA256");
        }
        // APP
        if (type.equals(CombinePayUrlEnum.APP.getType())) {
            resMap.put("appid", appId);
            resMap.put("prepayid", prepayId);
            // 使用字段appId、timeStamp、nonceStr、prepayId进行签名
            String sign = createSign(resMap);
            resMap.put("package", "Sign=WXPay");
            resMap.put("partnerid", wechatPayConfig.getMchId());
            resMap.put("sign", sign);
            resMap.put("signType", "HMAC-SHA256");
        }
        return resMap;
    }
    /**
     * 获取加密数据
     */
    private String createSign(Map<String, Object> params) {
        try {
            Map<String, Object> treeMap = new TreeMap<>(params);
            List<String> signList = new ArrayList<>(5);
            for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                signList.add(entry.getKey() + "=" + entry.getValue());
            }
            String signStr = String.join("&", signList);

            signStr = signStr + "&key=" + wechatPayConfig.getApiV3Key();
            System.out.println(signStr);

            Mac sha = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(wechatPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha.init(secretKey);
            byte[] array = sha.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
            }
            signStr = sb.toString().toUpperCase();
            System.out.println(signStr);

            return signStr;
        } catch (Exception e) {
            throw new RuntimeException("加密失败！");
        }
    }
}
