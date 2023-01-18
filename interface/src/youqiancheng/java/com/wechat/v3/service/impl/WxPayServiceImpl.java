package com.wechat.v3.service.impl;

import com.wechat.v3.bean.PayOrderDTO;
import com.wechat.v3.bean.WxPayV3Bean;
import com.wechat.v3.service.WxPayService;
import com.wechat.v3.utils.WxPayStateEnum;
import com.wechat.v3.utils.WxPayUtil;
import com.wechat.v3.vo.WxPayVO;
import com.wechat.v3.vo.WxQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ijpay.core.kit.WxPayKit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
/**
 * @ClassName WxPayServiceImpl
 * @Description 微信下单/查单
 * @Author zzb
 * @Date 2022/12/31 12:09
 * @Version 1.0
 */

@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Resource
    private WxPayV3Bean wxPayV3Bean;

    @Autowired
    private WxPayUtil wxPayUtil;

    @Override
    public WxPayVO createOrder(PayOrderDTO orderDetailEntity) throws Exception {
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        https://api.mch.weixin.qq.com/v3/pay/partner/transactions/jsapi
        httpPost.addHeader(ACCEPT, APPLICATION_JSON.toString());
        httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON.toString());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", wxPayV3Bean.getMchId())
                .put("appid", wxPayV3Bean.getAppId())
                .put("description", orderDetailEntity.getGoodsName())
                .put("notify_url", wxPayV3Bean.getNotifyUrl())
                .put("out_trade_no", orderDetailEntity.getOrderSn())
                .put("attach", orderDetailEntity.getUserId().toString());
        rootNode.putObject("amount")
                .put("total", orderDetailEntity.getTotalPrice());
        rootNode.putObject("payer")
                .put("openid", orderDetailEntity.getOpenId());

        objectMapper.writeValue(bos, rootNode);
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

        CloseableHttpClient httpClient = wxPayUtil.getHttpClient();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        WxPayVO wxPayVO = new WxPayVO();
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                wxPayVO.setNonceStr(WxPayKit.generateStr());
                wxPayVO.setTimeStamp(System.currentTimeMillis() / 1000 + "");
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                String prepay_id = jsonObject.getString("prepay_id");
                wxPayVO.setPrepayId("prepay_id=" + prepay_id);
                wxPayVO.setPaySign(wxPayUtil.getSign(wxPayV3Bean.getAppId(), wxPayVO.getTimeStamp(), wxPayVO.getNonceStr(), wxPayVO.getPrepayId()));
                log.info("200success,return body：{} ", EntityUtils.toString(response.getEntity()));
            } else if (statusCode == 204) {
                log.info("204success");
            } else {
                log.info("failed,resp code：{} ,return body：{} ", statusCode, EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("系统错误");
        }
        return wxPayVO;
    }

    /**
     * 订单查询
     *
     * @return
     */
    @Override
    public WxQueryVO queryOrder(String payNumber, Integer queryType) {
        WxQueryVO vo = new WxQueryVO();
        try {
            String url = "";
            if (queryType.equals(1)) {
                //根据支付订单查询
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/id/" + payNumber;
            } else {
                //根据系统自有订单号查询
                url = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + payNumber;
            }
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameter("mchid", wxPayV3Bean.getMchId());

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader(ACCEPT, APPLICATION_JSON.toString());

            CloseableHttpClient httpClient = wxPayUtil.getHttpClient();
            CloseableHttpResponse response = httpClient.execute(httpGet);

            String bodyAsString = EntityUtils.toString(response.getEntity());
            JSONObject data = JSON.parseObject(bodyAsString);
            log.info("微信订单查询结果：{}", data);
            if (StringUtils.hasText(data.getString("out_trade_no"))
                    && StringUtils.hasText(data.getString("trade_state"))) {
                vo.setOutTradeNo(data.getString("out_trade_no"));
                vo.setTradeState(WxPayStateEnum.getByName(data.getString("trade_state")).getCode());
                vo.setTradeStateDesc(data.getString("trade_state_desc"));
                vo.setTransactionId(data.getString("transaction_id"));
            } else {
                vo.setTradeState(WxPayStateEnum.ABSENCE.getCode());
                vo.setTradeStateDesc(data.getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
}
