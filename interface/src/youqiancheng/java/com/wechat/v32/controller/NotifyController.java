//package com.wechat.v32.controller;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
//import com.wechat.v32.config.WechatPayConfig;
//import com.wechat.v32.service.WechatPayValidator;
//import com.wechat.v32.utils.HttpUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.locks.ReentrantLock;
///**
// * @ClassName NotifyController
// * @Description 支付/退款回调通知
// * @Author zzb
// * @Date 2022/12/31 13:44
// * @Version 1.0
// */
//@Api(tags = "回调接口(API3)")
//@RestController
//@Slf4j
//public class NotifyController {
//    //@Resource
//    //private WechatPayConfig wechatPayConfig;
//    //@Resource
//    //private Verifier verifier;
//    private final ReentrantLock lock = new ReentrantLock();
//    @ApiOperation(value = "支付回调", notes = "支付回调")
//    @PostMapping("/payNotify")
//    public Map<String, String> payNotify(HttpServletRequest request, HttpServletResponse response) {
//
//        log.info("支付回调");
//        if(lock.tryLock()) {
//
//// 处理通知参数
//            Map<String,Object> bodyMap = getNotifyBody(request);
//            if(bodyMap==null){
//
//                return falseMsg(response);
//            }
//            log.warn("=========== 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱 ===========");
//            try {
//
//// 解密resource中的通知数据
//                String resource = bodyMap.get("resource").toString();
//                Map<String, Object> resourceMap = WechatPayValidator.decryptFromResource(resource, "1111111",1);
//                log.info("通知参数：{}", JSON.toJSONString(resourceMap));
//                String orderNo = resourceMap.get("out_trade_no").toString();
//                String transactionId = resourceMap.get("transaction_id").toString();
//// TODO 根据订单号，做幂等处理，并且在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱
//                log.warn("=========== 根据订单号，做幂等处理 ===========");
//            } finally {
//
////要主动释放锁
//                lock.unlock();
//            }
//        }
////成功应答
//        return trueMsg(response);
//    }
//    @ApiOperation(value = "退款回调", notes = "退款回调")
//    @PostMapping("/refundNotify")
//    public Map<String, String> refundNotify(HttpServletRequest request, HttpServletResponse response) {
//
//        log.info("退款回调");
//        if(lock.tryLock()) {
//
//// 处理通知参数
//            Map<String,Object> bodyMap = getNotifyBody(request);
//            if(bodyMap==null){
//
//                return falseMsg(response);
//            }
//            log.warn("=========== 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱 ===========");
//            try {
//
//// 解密resource中的通知数据
//                String resource = bodyMap.get("resource").toString();
//                Map<String, Object> resourceMap = WechatPayValidator.decryptFromResource(resource, "1111111",2);
//                log.info("通知参数：{}", JSON.toJSONString(resourceMap));
//                String orderNo = resourceMap.get("out_trade_no").toString();
//                String transactionId = resourceMap.get("transaction_id").toString();
//// TODO 根据订单号，做幂等处理，并且在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱
//                log.warn("=========== 根据订单号，做幂等处理 ===========");
//            } finally {
//
////要主动释放锁
//                lock.unlock();
//            }
//        }
////成功应答
//        return trueMsg(response);
//    }
//    private Map<String,Object> getNotifyBody(HttpServletRequest request){
//
////处理通知参数
//        String body = HttpUtils.readData(request);
//        log.info("退款回调参数：{}",body);
//// 转换为Map
//        Map<String, Object> bodyMap = JSONObject.parseObject(body, new TypeReference<Map<String, Object>>(){
//        });
//// 微信的通知ID（通知的唯一ID）
//        String notifyId = bodyMap.get("id").toString();
//// 验证签名信息
////        WechatPayValidator wechatPayValidator
////                = new WechatPayValidator(verifier, notifyId, body);
////        if(!wechatPayValidator.validate(request)){
////
////            log.error("通知验签失败");
////            return null;
////        }
//        log.info("通知验签成功");
//        return bodyMap;
//    }
//    private Map<String, String> falseMsg(HttpServletResponse response){
//
//        Map<String, String> resMap = new HashMap<>(8);
////失败应答
//        response.setStatus(500);
//        resMap.put("code", "ERROR");
//        resMap.put("message", "通知验签失败");
//        return resMap;
//    }
//    private Map<String, String> trueMsg(HttpServletResponse response){
//
//        Map<String, String> resMap = new HashMap<>(8);
////成功应答
//        response.setStatus(200);
//        resMap.put("code", "SUCCESS");
//        resMap.put("message", "成功");
//        return resMap;
//    }
//}
