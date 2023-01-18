//package com.wechat.v32.controller;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//
//import com.wechat.v32.config.WechatPayConfig;
//import com.wechat.v32.service.WechatPayRequest;
//import com.wechat.v32.wxenum.WechatPayUrlEnum;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
///**
// * @ClassName OtherPayController
// * @Description 其他处理(退款、查询、取消订单等)接口
// * 包含接口：根据订单号查询订单、关闭(取消)订单、申请退款、查询单笔退款信息、申请交易账单、申请资金账单、申请单个子商户资金账单、下载账单
// * @Author zzb
// * @Date 2022/12/31 13:42
// * @Version 1.0
// */
//@Api(tags = "支付接口(API3)")
//@RestController
//@RequestMapping("/test")
//@Slf4j
//public class OtherPayController {
//
//    @Resource
//    private WechatPayConfig wechatPayConfig;
//    @Resource
//    private WechatPayRequest wechatPayRequest;
//    /** * 无需应答签名 */
//    @Resource
//    private CloseableHttpClient wxPayNoSignClient;
//    // 模拟特约商户子商户号，实际情况：应该写在表中，与商家表进行映射，根据订单动态获取
//    private static final String subMchId ="xxx";
//    @ApiOperation(value = "根据订单号查询订单-统一接口", notes = "根据订单号查询订单-统一接口")
//    @GetMapping("/transactions/{orderNo}")
//    public Map<String, Object> transactionsByOrderNo(@PathVariable("orderNo") String orderNo) {
//
//// TODO 如果是扫码支付时，该接口就很有必要，应该前端通过轮询的方式请求该接口查询订单是否支付成功
//        log.info("根据订单号查询订单，订单号： {}", orderNo);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.ORDER_QUERY_BY_NO.getType().concat(orderNo))
//                .concat("?sp_mchid=").concat(wechatPayConfig.getMchId())
//                .concat("&sub_mchid=").concat(subMchId);
//        String res = wechatPayRequest.wechatHttpGet(url);
//        log.info("查询订单结果：{}",res);
//        Map<String, Object> resMap = JSONObject.parseObject(res, new TypeReference<Map<String, Object>>(){
//        });
//        String outTradeNo = resMap.get("out_trade_no").toString();
//        String appId = resMap.get("sp_appid").toString();
//        String mchId = resMap.get("sp_mchid").toString();
//        String subMchId = resMap.get("sub_mchid").toString();
//// 支付后才返回参数
//        Object payer = resMap.get("payer");
//        Object attach = resMap.get("attach");
///** * 交易状态，枚举值： * SUCCESS：支付成功 * REFUND：转入退款 * NOTPAY：未支付 * CLOSED：已关闭 * REVOKED：已撤销（仅付款码支付会返回） * USERPAYING：用户支付中（仅付款码支付会返回） * PAYERROR：支付失败（仅付款码支付会返回） */
//        String tradeState = resMap.get("trade_state").toString();
//        log.info("outTradeNo："+outTradeNo);
//        log.info("appId："+appId);
//        log.info("mchId："+mchId);
//        log.info("tradeState："+tradeState);
//        log.info("payer："+payer);
//        log.info("payer："+attach);
//        log.info("subMchId："+subMchId);
//        return resMap;
//    }
//    /** * 关闭(取消)订单 * @param orderNo * @return */
//    @ApiOperation(value = "关闭(取消)订单-统一接口", notes = "关闭(取消)订单-统一接口")
//
//    @PostMapping("/closeOrder/{orderNo}")
//    public void closeOrder(@PathVariable("orderNo") String orderNo) {
//
//// TODO 用于在客户下单后，不进行支付，取消订单的场景
//        log.info("根据订单号取消订单，订单号： {}", orderNo);
//        String url = String.format(WechatPayUrlEnum.CLOSE_ORDER_BY_NO.getType(), orderNo);
//        url = wechatPayConfig.getBaseUrl().concat(url);
//// 设置参数
//        Map<String, String> params = new HashMap<>(2);
//        params.put("sp_mchid", wechatPayConfig.getMchId());
//        params.put("sub_mchid", subMchId);
//        String paramsStr = JSON.toJSONString(params);
//        log.info("请求参数 ===> {}" + paramsStr);
//        String res = wechatPayRequest.wechatHttpPost(url,paramsStr);
//    }
//    /** * 申请退款 * @param orderNo */
//    @ApiOperation(value = "申请退款-统一接口", notes = "申请退款-统一接口")
//
//    @PostMapping("/refundOrder/{orderNo}")
//    public void refundOrder(@PathVariable("orderNo") String orderNo) {
//
//        log.info("根据订单号申请退款，订单号： {}", orderNo);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.DOMESTIC_REFUNDS.getType());
//// 设置参数
//        Map<String, Object> params = new HashMap<>(2);
//// 订单编号
//        params.put("out_trade_no", orderNo);
//        params.put("sub_mchid", subMchId);
//// 退款单编号 - 自定义
//        int outRefundNo = new Random().nextInt(999999999);
//        log.info("退款申请号：{}",outRefundNo);
//        params.put("out_refund_no",outRefundNo+"");
//// 退款原因
//        params.put("reason","申请退款");
//// 退款通知回调地址
//        params.put("notify_url", wechatPayConfig.getRefundNotifyUrl());
//        Map<String, Object> amountMap =new HashMap<>();
////退款金额，单位：分
//        amountMap.put("refund", 1);
////原订单金额，单位：分
//        amountMap.put("total", 1);
////退款币种
//        amountMap.put("currency", "CNY");
//        params.put("amount", amountMap);
//        String paramsStr = JSON.toJSONString(params);
//        log.info("请求参数 ===> {}" + paramsStr);
//        String res = wechatPayRequest.wechatHttpPost(url,paramsStr);
//        log.info("退款结果：{}",res);
//    }
//    /** * 查询单笔退款信息 * @param refundNo * @return */
//    @ApiOperation(value = "查询单笔退款信息-统一接口", notes = "查询单笔退款信息-统一接口")
//    @GetMapping("/queryRefundOrder/{refundNo}")
//    public Map<String, Object> queryRefundOrder(@PathVariable("refundNo") String refundNo) {
//
//        log.info("根据订单号查询退款订单，订单号： {}", refundNo);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.DOMESTIC_REFUNDS_QUERY.getType().concat(refundNo))
//                .concat("?sub_mchid=").concat(subMchId);
//        String res = wechatPayRequest.wechatHttpGet(url);
//        log.info("查询退款订单结果：{}",res);
//        Map<String, Object> resMap = JSONObject.parseObject(res, new TypeReference<Map<String, Object>>(){
//        });
//        String successTime = resMap.get("success_time").toString();
//        String refundId = resMap.get("refund_id").toString();
///** * 款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台-交易中心，手动处理此笔退款。 * 枚举值： * SUCCESS：退款成功 * CLOSED：退款关闭 * PROCESSING：退款处理中 * ABNORMAL：退款异常 */
//        String status = resMap.get("status").toString();
///** * 枚举值： * ORIGINAL：原路退款 * BALANCE：退回到余额 * OTHER_BALANCE：原账户异常退到其他余额账户 * OTHER_BANKCARD：原银行卡异常退到其他银行卡 */
//        String channel = resMap.get("channel").toString();
//        String userReceivedAccount = resMap.get("user_received_account").toString();
//        log.info("successTime："+successTime);
//        log.info("channel："+channel);
//        log.info("refundId："+refundId);
//        log.info("status："+status);
//        log.info("userReceivedAccount："+userReceivedAccount);
//// TODO 在查询单笔退款信息时，可以再去查询一次订单的状态，保证该订单已经退款完毕了
//        return resMap;
//    }
//    /** * 申请交易账单 * @param billDate 格式yyyy-MM-dd 仅支持三个月内的账单下载申请 ，如果传入日期未为当天则会出错 * @param billType 分为：ALL、SUCCESS、REFUND * ALL：返回当日所有订单信息（不含充值退款订单） * SUCCESS：返回当日成功支付的订单（不含充值退款订单） * REFUND：返回当日退款订单（不含充值退款订单） * @return */
//    @ApiOperation(value = "申请交易账单-统一接口", notes = "申请交易账单-统一接口")
//
//    @GetMapping("/tradeBill")
//    public String tradeBill(@RequestParam("billDate") String billDate, @RequestParam("billType") String billType) {
//
//        log.info("申请交易账单，billDate：{}，billType：{}", billDate,billType);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.TRADE_BILLS.getType())
//                .concat("?bill_date=").concat(billDate).concat("&bill_type=").concat(billType);
//// 填则默认返回服务商下的交易或退款数据，下载某个子商户下的交易或退款数据，则该字段必填
//        url = url.concat("&sub_mchid=").concat(subMchId);
//        String res = wechatPayRequest.wechatHttpGet(url);
//        log.info("查询退款订单结果：{}",res);
//        Map<String, Object> resMap = JSONObject.parseObject(res, new TypeReference<Map<String, Object>>(){
//        });
//        String downloadUrl = resMap.get("download_url").toString();
//        return downloadUrl;
//    }
//    /** * * @param billDate 格式yyyy-MM-dd 仅支持三个月内的账单下载申请，如果传入日期未为当天则会出错 * @param accountType 分为：BASIC、OPERATION、FEES * BASIC：基本账户 * OPERATION：运营账户 * FEES：手续费账户 * @return */
//    @ApiOperation(value = "申请资金账单-统一接口", notes = "申请资金账单-统一接口")
//
//    @GetMapping("/fundFlowBill")
//    public String fundFlowBill(@RequestParam("billDate") String billDate, @RequestParam("accountType") String accountType) {
//
//        log.info("申请交易账单，billDate：{}，accountType：{}", billDate,accountType);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.FUND_FLOW_BILLS.getType())
//                .concat("?bill_date=").concat(billDate).concat("&account_type=").concat(accountType);
//        String res = wechatPayRequest.wechatHttpGet(url);
//        log.info("查询退款订单结果：{}",res);
//        Map<String, Object> resMap = JSONObject.parseObject(res, new TypeReference<Map<String, Object>>(){
//        });
//        String downloadUrl = resMap.get("download_url").toString();
//        return downloadUrl;
//    }
//    /** * * @param billDate 格式yyyy-MM-dd 仅支持三个月内的账单下载申请，如果传入日期未为当天则会出错 * @param accountType 分为：BASIC、OPERATION、FEES * BASIC：基本账户 * OPERATION：运营账户 * FEES：手续费账户 * @return */
//    @ApiOperation(value = "申请单个子商户资金账单-统一接口", notes = "申请单个子商户资金账单-统一接口")
//
//    @GetMapping("/subFundFlowBill")
//    public String subMerchantFundFlowBill(@RequestParam("billDate") String billDate, @RequestParam("accountType") String accountType) {
//
//        log.info("申请单个子商户资金账单，billDate：{}，accountType：{}", billDate,accountType);
//        String url = wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.FUND_FLOW_BILLS.getType())
//                .concat("?bill_date=").concat(billDate).concat("&account_type=").concat(accountType)
//                .concat("&sub_mchid=").concat(billDate).concat("&algorithm=").concat("AEAD_AES_256_GCM");
//        String res = wechatPayRequest.wechatHttpGet(url);
//        log.info("查询退款订单结果：{}",res);
//        Map<String, Object> resMap = JSONObject.parseObject(res, new TypeReference<Map<String, Object>>(){
//        });
//        String downloadBillCount = resMap.get("download_bill_count").toString();
//        String downloadBillList = resMap.get("download_bill_list").toString();
//        List<Map<String, Object>> billListMap = JSONObject.parseObject(downloadBillList, new TypeReference<List<Map<String, Object>>>(){
//        });
//        String downloadUrl = billListMap.get(0).get("download_url").toString();
//        log.info("downloadBillCount="+downloadBillCount);
//        log.info("downloadUrl="+downloadUrl);
//        return downloadUrl;
//    }
//    @ApiOperation(value = "下载账单-统一接口", notes = "下载账单-统一接口")
//
//    @GetMapping("/downloadBill")
//    public void downloadBill(String downloadUrl) {
//
//        log.info("下载账单，下载地址：{}",downloadUrl);
//        HttpGet httpGet = new HttpGet(downloadUrl);
//        httpGet.addHeader("Accept", "application/json");
//        CloseableHttpResponse response =null;
//        try {
//
////使用wxPayClient发送请求得到响应
//            response = wxPayNoSignClient.execute(httpGet);
//            String body = EntityUtils.toString(response.getEntity());
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode == 200 || statusCode == 204) {
//
//                log.info("下载账单，返回结果 = " + body);
//            } else {
//
//                throw new RuntimeException("下载账单异常, 响应码 = " + statusCode+ ", 下载账单返回结果 = " + body);
//            }
//// TODO 将body内容转为excel存入本地或者输出到浏览器，演示存入本地
//            writeStringToFile(body);
//        }catch (Exception e){
//
//            throw new RuntimeException(e.getMessage());
//        }
//        finally {
//
//            if(response!=null) {
//
//                try {
//
//                    response.close();
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//    private void writeStringToFile(String body) {
//
//        FileWriter fw = null;
//        try {
//
//            String filePath = "C:\\Users\\lhz12\\Desktop\\wxPay.txt";
//            fw = new FileWriter(filePath, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(body);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }finally {
//
//            try {
//
//                if(fw!=null) {
//
//                    fw.close();
//                }
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//    }
//}
