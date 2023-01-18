package com.wechat.service;

import com.alipay.api.domain.PreOrderResult;

import javax.servlet.http.HttpServletRequest;

public interface AppletOrderService {

    PreOrderResult placeOrder(String body, String out_trade_no, String total_fee, String openId);

   // PayResult getWxPayResult(HttpServletRequest request);
}
