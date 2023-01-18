package com.wechat.v33.wxenum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定义统一枚举
 */
@AllArgsConstructor
@Getter
public enum CombinePayUrlEnum {


    /**
     * native
     */
    NATIVE("native"),
    /**
     * app
     */
    APP("app"),
    /**
     * h5
     */
    H5("h5"),
    /**
     *  jsapi
     */
    JSAPI("jsapi"),

    /**
     *  小程序jsapi
     */
    SUB_JSAPI("sub_jsapi"),

    /**
     * 查询订单
     */
    ORDER_QUERY_BY_NO("/combine-transactions/out-trade-no/"),

    /**
     * 关闭订单
     */
    CLOSE_ORDER_BY_NO("/combine-transactions/out-trade-no/%s/close"),

    /**
     * 申请退款
     */
    DOMESTIC_REFUNDS("/refund/domestic/refunds"),

    /**
     * 查询单笔退款
     */
    DOMESTIC_REFUNDS_QUERY("/refund/domestic/refunds/"),

    /**
     * 申请交易账单
     */
    TRADE_BILLS("/bill/tradebill"),

    /**
     * 申请资金账单
     */
    FUND_FLOW_BILLS("/bill/fundflowbill"),

    /**
     * 申请单个子商户资金账单
     */
    SUB_MERCHANT_FUND_FLOW_BILLS("/bill/sub-merchant-fundflowbill"),

    /**
     * 合单支付APIV3
     */
    COMBINE_TRANSACTIONS("/combine-transactions/");


    /**
     * 类型
     */
    private final String type;
}
