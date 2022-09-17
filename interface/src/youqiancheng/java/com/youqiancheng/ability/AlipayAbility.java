package com.youqiancheng.ability;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.alipay.AliPayConfig;
import com.youqiancheng.controller.alipay.PayInfo;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName AlipayAbility
 * @Description TODO
 * @Author zzb
 * @Date 2021/10/10 13:53
 * @Version 1.0
 **/
@Component
public class AlipayAbility {

    @Resource
    private AliPayConfig aliPayConfig;

    public ResultVo buildAlipayUrl(String orderNo, BigDecimal money) throws IOException {

        //**********支付流程************
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = aliPayConfig.alipayClientCert();
            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(aliPayConfig.return_url);
            alipayRequest.setNotifyUrl( aliPayConfig.notify_url);
            //下单时传递的请求参数, 详情参照（https://docs.open.alipay.com/api_1/alipay.trade.page.pay/）请求参数
            Map<String, Object> bizContentMap = new TreeMap<String, Object>();
            //商户网站唯一订单号(必填，类型为String), 最大长度64

            bizContentMap.put("out_trade_no",orderNo);
            //商品的标题/交易标题/订单标题/订单关键字等(必填，类型为String), 最大长度256。
            bizContentMap.put("subject", "有钱城订单");
            //订单总金额，单位为元，精确到小数点后两位(必填，类型为String), 最大长度9。
            //bizContentMap.put("total_amount", "0.01");
            bizContentMap.put("total_amount",money);
            //销售产品码，与支付宝签约的产品码名称(必填，类型为String), 目前仅支持FAST_INSTANT_TRADE_PAY
            bizContentMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
            //对一笔交易的具体描述信息。 (非必填，类型为String), 最大长度128。
            bizContentMap.put("body", "有钱城订单");
            bizContentMap.put("timeout_express", "1m");

            //填充业务参数
            alipayRequest.setBizContent(JSONObject.toJSONString(bizContentMap));
            String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            return ResultVOUtils.success(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, e.getErrMsg());
        }

    }
}
