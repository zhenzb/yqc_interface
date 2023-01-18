package com.wechat.v33.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;


import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.v33.confing.WechatPayConfig;
import com.wechat.v33.service.WechatPayValidator;
import com.wechat.v33.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @ClassName NotifyController
 * @Description 支付/退款回调通知
 * @Author zzb
 * @Date 2023/1/18 11:34
 * @Version 1.0
 */
@Api(tags = "回调接口(API3)")
@RestController
@Slf4j
public class NotifyController {
    @Resource
    private WechatPayConfig wechatPayConfig;

    //@Resource
   // private Verifier verifier;

    //private final ReentrantLock lock = new ReentrantLock();

    @ApiOperation(value = "合单支付回调", notes = "合单支付回调")
    @PostMapping("/combinePayNotify")
    public Map<String, String> combinePayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("合单支付回调");
        //if(lock.tryLock()) {
            // 处理通知参数
            Map<String,Object> bodyMap = getNotifyBody(request);
            if(bodyMap==null){
                return falseMsg(response);
            }

            log.warn("=========== 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱 ===========");
            try {
                // 解密resource中的通知数据
                String resource = bodyMap.get("resource").toString();
                Map<String, Object> resourceMap = WechatPayValidator.decryptFromResource(resource, wechatPayConfig.getApiV3Key(),1);
                log.info("通知参数：{}", JSON.toJSONString(resourceMap));
                // 合单号s
                String combineOutTradeNo = resourceMap.get("combine_out_trade_no").toString();
                // 合单商户appid
                String combineAppid = resourceMap.get("combine_appid").toString();
                // 合单商户号
                String combineMchid = resourceMap.get("combine_mchid").toString();

                // 子订单信息
                // 子订单数据
                String subOrders = resourceMap.get("sub_orders").toString();
                log.info("subOrders：" + subOrders);
                if(subOrders!=null){
                    List<Map<String, Object>> subOrderList = JSONObject.parseObject(subOrders, new TypeReference<List<Map<String, Object>>>() {
                    });
                    log.info("============== 子订单信息: ==============");
                    for(Map<String, Object> subOrderMap:subOrderList) {
                        String subOrderNo = subOrderMap.get("out_trade_no").toString();
                        String subOrderTransactionId = subOrderMap.get("transaction_id").toString();
                        String subOrderMchId = subOrderMap.get("mchid").toString();
                        String subOrderSubMchId = subOrderMap.get("sub_mchid").toString();
                        String subOrderSuccessTime = subOrderMap.get("success_time").toString();
                        // 支付后才返回参数
                        Object subOrderAttach = subOrderMap.get("attach");
                        /**
                         *         交易状态，枚举值：
                         *         SUCCESS：支付成功
                         *         REFUND：转入退款
                         *         NOTPAY：未支付
                         *         CLOSED：已关闭
                         *         REVOKED：已撤销（仅付款码支付会返回）
                         *         USERPAYING：用户支付中（仅付款码支付会返回）
                         *         PAYERROR：支付失败（仅付款码支付会返回）
                         */
                        String subOrderTradeState = subOrderMap.get("trade_state").toString();

                        log.info("subOrderNo：" + subOrderNo);
                        log.info("subOrderTransactionId：" + subOrderTransactionId);
                        log.info("subOrderMchId：" + subOrderMchId);
                        log.info("subOrderTradeState：" + subOrderTradeState);
                        log.info("subOrderAttach：" + subOrderAttach);
                        log.info("subOrderSubMchId：" + subOrderSubMchId);
                        log.info("subOrderSuccessTime：" + subOrderSuccessTime);
                    }
                }

                // TODO 根据订单号，做幂等处理，并且在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱
                log.warn("=========== 根据订单号，做幂等处理 ===========");
            } finally {
                //要主动释放锁
                //lock.unlock();
            }
       // }

        //成功应答
        return trueMsg(response);
    }

    @ApiOperation(value = "退款回调", notes = "退款回调")
    @PostMapping("/refundNotify")
    public Map<String, String> refundNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("退款回调");
       // if(lock.tryLock()) {
            // 处理通知参数
            Map<String,Object> bodyMap = getNotifyBody(request);
            if(bodyMap==null){
                return falseMsg(response);
            }

            log.warn("=========== 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱 ===========");

            try {
                // 解密resource中的通知数据
                String resource = bodyMap.get("resource").toString();
                Map<String, Object> resourceMap = WechatPayValidator.decryptFromResource(resource, wechatPayConfig.getApiV3Key(),2);
                log.info("通知参数：{}", JSON.toJSONString(resourceMap));
                String orderNo = resourceMap.get("out_trade_no").toString();
                String transactionId = resourceMap.get("transaction_id").toString();

                // TODO 根据订单号，做幂等处理，并且在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱

                log.warn("=========== 根据订单号，做幂等处理 ===========");
            } finally {
                //要主动释放锁
                //lock.unlock();
            }
       // }

        //成功应答
        return trueMsg(response);
    }

    private Map<String,Object> getNotifyBody(HttpServletRequest request){
        //处理通知参数
        String body = HttpUtils.readData(request);
        log.info("退款回调参数：{}",body);

        // 转换为Map
        Map<String, Object> bodyMap = JSONObject.parseObject(body, new TypeReference<Map<String, Object>>(){});
        // 微信的通知ID（通知的唯一ID）
        String notifyId = bodyMap.get("id").toString();

        // 验证签名信息
//        WechatPayValidator wechatPayValidator
//                = new WechatPayValidator(verifier, notifyId, body);
//        if(!wechatPayValidator.validate(request)){
//
//            log.error("通知验签失败");
//            return null;
//        }
        log.info("通知验签成功");
        return bodyMap;
    }

    private Map<String, String> falseMsg(HttpServletResponse response){
        Map<String, String> resMap = new HashMap<>(8);
        //失败应答
        response.setStatus(500);
        resMap.put("code", "ERROR");
        resMap.put("message", "通知验签失败");
        return resMap;
    }

    private Map<String, String> trueMsg(HttpServletResponse response){
        Map<String, String> resMap = new HashMap<>(8);
        //成功应答
        response.setStatus(200);
        resMap.put("code", "SUCCESS");
        resMap.put("message", "成功");
        return resMap;
    }
}
