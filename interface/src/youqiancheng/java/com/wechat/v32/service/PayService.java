//package com.wechat.v32.service;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//
//import com.handongkeji.config.exception.JsonException;
//import com.handongkeji.constants.TypeConstant;
//import com.wechat.v32.config.WechatPayConfig;
//import com.wechat.v32.wxenum.WechatPayUrlEnum;
//import com.youqiancheng.controller.wechatpay.PayInfo;
//import com.youqiancheng.controller.wechatpay.WeiXinPayController;
//import com.youqiancheng.mybatis.dao.A18SysMessageDao;
//import com.youqiancheng.mybatis.model.C12RewardRecordDO;
//import com.youqiancheng.mybatis.model.D06PayOrderDO;
//import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
//import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
//import com.youqiancheng.service.client.service.C12RewardRecordClientService;
//import com.youqiancheng.service.client.service.D06PayOrderClientService;
//import com.youqiancheng.service.client.service.E01RedenvelopesStreetClientService;
//import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
//import com.youqiancheng.service.maile.service.MailService;
//import com.youqiancheng.vo.result.ResultEnum;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.math.BigDecimal;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
///**
// * @ClassName PayService
// * @Description 统一下单处理接口
// * @Author zzb
// * @Date 2022/12/31 13:37
// * @Version 1.0
// */
//@Api(tags = "支付接口(API3)")
//@RestController
//@RequestMapping("/test")
//@Slf4j
//public class PayService {
//    @Resource
//    private D06PayOrderClientService d06PayOrderClientService;
//    @Resource
//    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
//    @Resource
//    private E01RedenvelopesStreetClientService e01RedenvelopesStreetClientService;
//
//    @Autowired
//    private C12RewardRecordClientService c12RewardRecordService;
//
//
//    @Resource
//    private WechatPayConfig wechatPayConfig;
//    @Resource
//    private WechatPayRequest wechatPayRequest;
//    /** * 无需应答签名 */
//    @Resource
//    private CloseableHttpClient wxPayNoSignClient;
//    // 公众号APPID
//    private static final String WX_JSAPI_SUB_APPID = "wxeb5de11b49702a70";
//    // 小程序APPID
//    private static final String WX_JSAPI_MINI_SUB_APPID = "wxeb5de11b49702a70";
//    // 模拟特约商户子商户号，实际情况：应该写在表中，与商家表进行映射，根据订单动态获取
//    private static final String subMchId ="1606268735";
//    /** * type：h5、jsapi、app、native、sub_jsapi * H5支付时，需要在商户平台--"产品中心"--"开发配置"自行配置支付提交的域名 * @param type * @return */
//    @ApiOperation(value = "统一下单-统一接口", notes = "统一下单-统一接口")
//    @GetMapping("/transactions")
//    public Map<String,Object> transactions(String type,String orderId,String openId) {
//        //根据订单号查询要付款的商品信息
//        /**********业务流程调用参数************/
//        PayInfo payInfo = getPayInfo(Long.valueOf(orderId), 1);
//        log.info("统一下单API，支付方式：{}",type);
//// 统一参数封装
//        Map<String, Object> params = new HashMap<>(8);
//        params.put("sp_appid", wechatPayConfig.getAppId()); //注意这里是服务号的appid，不是小程序的
//        params.put("sp_mchid", wechatPayConfig.getMchId());// //这里是用服务商的id 在我的账号一栏可以找到
//// 子商户号
//        params.put("sub_mchid",subMchId);  //这里对应特约商户号id 付款到对应商户的凭证就是这个 在注册特约商户的时候邮件里可以找到 这里建议配置到数据库动态传递
//        params.put("description", "有钱城商品");
//        int outTradeNo = new Random().nextInt(999999999);
//        params.put("out_trade_no", outTradeNo + "");
//// 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
//        params.put("attach", "orderId="+orderId);
//        params.put("notify_url", wechatPayConfig.getNotifyUrl());
//// 金额单位为分
//        Map<String, Object> amountMap = new HashMap<>(4);
//        BigDecimal multiply = payInfo.getMoney().multiply(new BigDecimal("100"));
//        int money = multiply.intValue();
//        amountMap.put("total", money);
//        amountMap.put("currency", "CNY");
//        params.put("amount", amountMap);
//// 场景信息
//        Map<String, Object> sceneInfoMap = new HashMap<>(4);
//// 客户端IP
//        sceneInfoMap.put("payer_client_ip", "127.0.0.1");
//// 商户端设备号（门店号或收银设备ID）
//        sceneInfoMap.put("device_id", "127.0.0.1");
//// 除H5与JSAPI有特殊参数外，其他的支付方式都一样
//        if (type.equals(WechatPayUrlEnum.H5.getType())) {
//
//            Map<String, Object> h5InfoMap = new HashMap<>(4);
//// 场景类型:iOS, Android, Wap
//            h5InfoMap.put("type", "IOS");
//            sceneInfoMap.put("h5_info", h5InfoMap);
//        } else if (type.equals(WechatPayUrlEnum.JSAPI.getType()) || type.equals(WechatPayUrlEnum.SUB_JSAPI.getType())) {
//
//// TODO 小程序或者公众号支付时，需要传入子应用的appId，并且在服务商平台的特约商户管理中进行授权
//// 判断是否小程序还是服务号支付
//            String subAppId = WX_JSAPI_SUB_APPID;
//            if (type.equals(WechatPayUrlEnum.SUB_JSAPI.getType())) {
//
//// 发起JSAPI的小程序
//                subAppId = WX_JSAPI_MINI_SUB_APPID;
//            }
//// 子商户申请的应用ID，若sub_openid有传的情况下，sub_appid必填
//            params.put("sub_appid", subAppId);
//            Map<String, Object> payerMap = new HashMap<>(4);
//// openId需要对应小程序或者公众号授权的openId
//            payerMap.put("sub_openid", openId);
//            params.put("payer", payerMap);
//        }
//        params.put("scene_info", sceneInfoMap);
//        String paramsStr = JSON.toJSONString(params);
//        log.info("请求参数 ===> {}", paramsStr);
//// 重写type值，因为小程序会多一个下划线(sub_type)
//        String[] split = type.split("_");
//        String newType = split[split.length - 1];
//        String resStr = wechatPayRequest.wechatHttpPost(wechatPayConfig.getBaseUrl().concat(WechatPayUrlEnum.PAY_TRANSACTIONS.getType().concat(newType)), paramsStr);
//        Map<String, Object> resMap = JSONObject.parseObject(resStr, new TypeReference<Map<String, Object>>(){
//        });
//        Map<String, Object> signMap = paySignMsg(resMap, type);
//        resMap.put("type",type);
//        resMap.put("signMap",signMap);
//        log.info("返回参数 ===> {}" + resMap);
//        return resMap;
//    }
//    private Map<String, Object> paySignMsg(Map<String, Object> map,String type){
//
//// 设置签名信息,Native与H5不需要
//        if(type.equals(WechatPayUrlEnum.H5.getType()) || type.equals(WechatPayUrlEnum.NATIVE.getType()) ){
//
//            return null;
//        }
//        long timeMillis = System.currentTimeMillis();
//        String appId = wechatPayConfig.getAppId();
//        String timeStamp = timeMillis/1000+"";
//        String nonceStr = timeMillis+"";
//        String prepayId = map.get("prepay_id").toString();
//        String packageStr = "prepay_id="+prepayId;
//// 公共参数
//        Map<String, Object> resMap = new HashMap<>();
//        resMap.put("nonceStr",nonceStr);
//        resMap.put("timeStamp",timeStamp);
//// JSAPI、SUB_JSAPI(小程序)
//        if(type.equals(WechatPayUrlEnum.JSAPI.getType()) || type.equals(WechatPayUrlEnum.SUB_JSAPI.getType()) ) {
//
//            resMap.put("appId",appId);
//            resMap.put("package", packageStr);
//// 使用字段appId、timeStamp、nonceStr、package进行签名
//            String paySign = createSign(resMap);
//            resMap.put("paySign", paySign);
//            resMap.put("signType", "HMAC-SHA256");
//        }
//// APP
//        if(type.equals(WechatPayUrlEnum.APP.getType())) {
//
//            resMap.put("appid",appId);
//            resMap.put("prepayid", prepayId);
//// 使用字段appId、timeStamp、nonceStr、prepayId进行签名
//            String sign = createSign(resMap);
//            resMap.put("package", "Sign=WXPay");
//            resMap.put("partnerid", wechatPayConfig.getMchId());
//            resMap.put("sign", sign);
//            resMap.put("signType", "HMAC-SHA256");
//        }
//        return resMap;
//    }
//    /** * 获取加密数据 */
//    private String createSign(Map<String, Object> params){
//
//        try {
//
//            Map<String, Object> treeMap = new TreeMap<>(params);
//            List<String> signList = new ArrayList<>(5);
//            for (Map.Entry<String, Object> entry : treeMap.entrySet())
//            {
//
//                signList.add(entry.getKey() + "=" + entry.getValue());
//            }
//            String signStr = String.join("&", signList);
//            signStr = signStr+"&key="+wechatPayConfig.getApiV3Key();
//            System.out.println(signStr);
//            Mac sha = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(wechatPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            sha.init(secretKey);
//            byte[] array = sha.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for (byte item : array) {
//
//                sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
//            }
//            signStr = sb.toString().toUpperCase();
//            System.out.println(signStr);
//            return signStr;
//        }catch (Exception e){
//
//            throw new RuntimeException("加密失败！");
//        }
//    }
//
//
//
//    public PayInfo getPayInfo(Long id,Integer type){
//        PayInfo payInfo=new PayInfo();
//        if(id==null||id==0){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
//        }
//        if(type==null||type==0){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"type不能为空");
//        }
//        //商家订单号
//        String orderNo="";
//        //支付金额
//        BigDecimal money=new BigDecimal(0);
//        //如果是用户订单则查询支付订单信息
//        if(type== TypeConstant.PayObjectType.user.getCode()){
//            D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.deductInventory(id);
//            orderNo=d06PayOrderDO.getOrderNo();
//            money=d06PayOrderDO.getOrderPrice();
//
//        }//如果商家发红包则创建红包订单号:HB+时间流水+id，查询金额
//        else if(type==TypeConstant.PayObjectType.shop.getCode()){
//            orderNo="HB";
//            orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//            orderNo+=id;
//            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
//            if(e04RedenvelopesGrantRecordDO==null){
//                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放信息不存在");
//            }
//            E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetClientService.get(e04RedenvelopesGrantRecordDO.getStreetId());
//            money=e01RedenvelopesStreetDO.getMoney();
//        }//用户打赏，创建订单号DS+时间流水+id，查询金额
//        else{
//            orderNo="DS";
//            orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//            orderNo+=id;
//            C12RewardRecordDO c12RewardRecordDO = c12RewardRecordService.get(id);
//            if(c12RewardRecordDO==null){
//                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"打赏记录不存在");
//            }
//            money=c12RewardRecordDO.getMoney();
//        }
//        payInfo.setMoney(money);
//        payInfo.setOrderNo(orderNo);
//        return payInfo;
//    }
//}
