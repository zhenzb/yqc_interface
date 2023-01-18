package com.wechat.v3.service;

import com.wechat.v3.bean.PayOrderDTO;
import com.wechat.v3.vo.WxPayVO;
import com.wechat.v3.vo.WxQueryVO;

public interface WxPayService {
    /**
     * 微信下单
     * @param dto
     * @return
     */
    WxPayVO createOrder(PayOrderDTO dto) throws Exception;

    /**
     * 查询订单。queryType。1，根据支付订单查询。2，根据系统自有订单号查询
     * @return
     */
    WxQueryVO queryOrder(String payNumber, Integer queryType);
}
