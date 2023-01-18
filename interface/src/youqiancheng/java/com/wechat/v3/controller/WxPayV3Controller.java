package com.wechat.v3.controller;

import com.wechat.v3.bean.WxPayV3Bean;
import com.wechat.v3.utils.WxPayStateEnum;
import com.wechat.v3.utils.WxPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;


import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.wechat.pay.contrib.apache.httpclient.constant.WechatPayHttpHeaders.*;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
/**
 * 小程序，确定订单信息后——>确认支付——>调用后端的生成订单接口——>后端生成系统自有订单——>后端通过自有订单号、金额、openid等去请求微信下单接口，
 * 微信返回预支付交易会话标识prepay_id——>后端给appid、timestamp、nonceStr、prepayId签名，并将签名、timestamp、nonceStr、prepay_id返回给小程序——>
 * 小程序调用wx.requestPayment拉起微信支付——>用户付完钱后，微信根据下单时候填的回调地址，请求自有系统的回调接口，自有系统处理相应的逻辑
 */

/**
 * @ClassName WxPayV3Controller
 * @Description 微信支付回调
 * @Author zzb
 * @Date 2022/12/31 12:11
 * @Version 1.0
 */

@Slf4j
public class WxPayV3Controller {
    @Resource
    private WxPayV3Bean wxPayV3Bean;

    @Autowired
    private WxPayUtil wxPayUtil;

    /**
     * 微信支付回调通知
     */
    @PostMapping(value = "/payNotify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("微信回调开始");
        //读取请求体的信息
        ServletInputStream inputStream = request.getInputStream();
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s;
        //读取回调请求体
        while ((s = bufferedReader.readLine()) != null) {
            stringBuffer.append(s);
        }
        String s1 = stringBuffer.toString();

        String timestamp = request.getHeader(WECHAT_PAY_TIMESTAMP);
        String nonce = request.getHeader(WECHAT_PAY_NONCE);
        String serialNo = request.getHeader(WECHAT_PAY_SERIAL);
        String signature = request.getHeader(WECHAT_PAY_SIGNATURE);
        // 构建request，传入必要参数
        NotificationRequest notificationRequest = new NotificationRequest.Builder().withSerialNumber(serialNo)
                .withNonce(nonce)
                .withTimestamp(timestamp)
                .withSignature(signature)
                .withBody(s1)
                .build();
        NotificationHandler handler = new NotificationHandler(wxPayUtil.getVerifier(), wxPayV3Bean.getApiKey3().getBytes(StandardCharsets.UTF_8));
        // 验签和解析请求体
        Notification notification = handler.parse(notificationRequest);
        Assert.assertNotNull(notification);
        log.info("回调结果：{}",notification);

        JSONObject jsonObject = JSONObject.parseObject(notification.getDecryptData());
        String tradeState = jsonObject.getString("trade_state");
        String paySn = jsonObject.getString("transaction_id");
        String successTime = jsonObject.getString("success_time");
        String attach = jsonObject.getString("attach");
        JSONObject amount = JSONObject.parseObject(jsonObject.getString("amount"));
        String payerTotal = amount.getString("payer_total");
        String orderSn = jsonObject.getString("out_trade_no");

        try {
//            PayNotifyDTO payNotifyDTO = new PayNotifyDTO().setPaySn(paySn)
//                    .setPayPrice(new BigDecimal(payerTotal).divide(new BigDecimal("100")))
//                    .setPayTime(wxPayUtil.dateTimeToDate(successTime))
//                    .setAttach(attach)
//                    .setOrderSn(orderSn);
            if (StringUtils.hasText(tradeState) && WxPayStateEnum.SUCCESS.getName().equals(tradeState)) {
                //用payNotifyDTO处理相关逻辑（比如：更改订单状态为已支付，将微信支付单号-支付金额-支付时间存到订单表等等）
            }
            response.setStatus(200);
        }catch (Exception e){
            Map<String, String> map = new HashMap<>(12);
            response.setStatus(500);
            map.put("code", "FAIL");
            map.put("message", "失败");
            response.addHeader(CONTENT_TYPE, APPLICATION_JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
        }finally {
            response.flushBuffer();
        }
    }

}
