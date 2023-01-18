package com.wechat.v32.wxenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WechatPayUrlEnum {
    /** * native */
    NATIVE("native"),
    /** * app */
    APP("app"),
    /** * h5 */
    H5("h5"),
    /** * jsapi */
    JSAPI("jsapi"),
    /** * 小程序jsapi */
    SUB_JSAPI("sub_jsapi"),
    /** * 下单 */
    PAY_TRANSACTIONS("/pay/partner/transactions/"),
    /** * 查询订单 */
    ORDER_QUERY_BY_NO("/pay/partner/transactions/out-trade-no/"),
    /** * 关闭订单 */
    CLOSE_ORDER_BY_NO("/pay/partner/transactions/out-trade-no/%s/close"),
    /** * 申请退款 */
    DOMESTIC_REFUNDS("/refund/domestic/refunds"),
    /** * 查询单笔退款 */
    DOMESTIC_REFUNDS_QUERY("/refund/domestic/refunds/"),
    /** * 申请交易账单 */
    TRADE_BILLS("/bill/tradebill"),
    /** * 申请资金账单 */
    FUND_FLOW_BILLS("/bill/fundflowbill"),
    /** * 申请单个子商户资金账单 */
    SUB_MERCHANT_FUND_FLOW_BILLS("/bill/sub-merchant-fundflowbill");
    /** * 类型 */
    private final String type;
}
