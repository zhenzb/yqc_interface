//package com.wechat.v32.controller;
//
//import com.wechat.v32.service.PayService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
///**
// * @ClassName payController
// * @Description 微信预下单组装小程序唤醒支付接口
// * @Author zzb
// * @Date 2022/12/31 14:49
// * @Version 1.0
// */
//@RestController
//@RequestMapping("/payController")
//@Slf4j
//public class payController {
//
//    @Autowired
//    private PayService payService;
//
//    @RequestMapping("/payOrder")
//    public Map<String,Object> payOrder(String openid,String orderId) {
//        return payService.transactions("sub_jsapi",orderId,openid);
//    }
//}
