//package com.youqiancheng.controller.client.alipay;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.domain.AlipayTradeAppPayModel;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.alipay.api.request.AlipayTradePagePayRequest;
//import com.alipay.api.request.AlipayTradePageRefundRequest;
//import com.alipay.api.response.AlipayTradeAppPayResponse;
//import com.handongkeji.config.exception.JsonException;
//import com.handongkeji.constants.TypeConstant;
//import com.handongkeji.util.Constants;
//import com.handongkeji.util.JsonBean;
//import com.handongkeji.util.manager.ResultVOUtils;
//import com.youqiancheng.mybatis.dao.A18SysMessageDao;
//import com.youqiancheng.mybatis.model.A18SysMessageDO;
//import com.youqiancheng.mybatis.model.D06PayOrderDO;
//import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
//import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
//import com.youqiancheng.service.client.service.D06PayOrderClientService;
//import com.youqiancheng.service.client.service.E01RedenvelopesStreetClientService;
//import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
//import com.youqiancheng.vo.result.ResultEnum;
//import com.youqiancheng.vo.result.ResultVo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.*;
//
//@RestController
//@Api(tags = {"阿里支付"})
//@RequestMapping(value = "/alipay")
//public class PayController {
//    protected static final Log logger = LogFactory.getLog(PayController.class);
//
//    @Resource
//    private D06PayOrderClientService  d06PayOrderClientService;
//    @Resource
//    private A18SysMessageDao a18SysMessageDao;
//    @Resource
//    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
//    @Resource
//    private E01RedenvelopesStreetClientService e01RedenvelopesStreetClientService;
//
//
//    @PostMapping("pc/pay")
//    @ApiOperation(value = "支付接口:type:1、用户支付——下订单；2、买家支付——发红包；id: type=1时——订单id ，type =2时——红包发放记录ID")
//    public ResultVo payController(Long id,Integer type, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        if(id==null||id==0){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
//        }
//        if(type==null||type==0){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"type不能为空");
//        }
//        //单据号
//        String orderNo="";
//        //金额
//        BigDecimal money=new BigDecimal(0);
//        if(type==TypeConstant.PayObjectType.user.getCode()){
//            D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.deductInventory(id);
//            orderNo=d06PayOrderDO.getOrderNo();
//            money=d06PayOrderDO.getOrderPrice();
//        }else{
//            orderNo="HB";
//            orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//            orderNo+=id;
//            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
//            if(e04RedenvelopesGrantRecordDO==null){
//                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放信息不存在");
//            }
//            E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetClientService.get(e04RedenvelopesGrantRecordDO.getStreetId());
//            money=e01RedenvelopesStreetDO.getMoney();
//        }
//
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID,
//                AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
//
//        //设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        //alipayRequest.setReturnUrl(AlipayConfig.return_url);
//        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
//        //下单时传递的请求参数, 详情参照（https://docs.open.alipay.com/api_1/alipay.trade.page.pay/）请求参数
//        Map<String, Object> bizContentMap = new TreeMap<String, Object>();
//        //商户网站唯一订单号(必填，类型为String), 最大长度64
//
//        bizContentMap.put("out_trade_no", orderNo);
//        //商品的标题/交易标题/订单标题/订单关键字等(必填，类型为String), 最大长度256。
//        bizContentMap.put("subject", "有钱城订单");
//        //订单总金额，单位为元，精确到小数点后两位(必填，类型为String), 最大长度9。
//        bizContentMap.put("total_amount", "0.01");
//      //  bizContentMap.put("total_amount",money.toString());
//        //销售产品码，与支付宝签约的产品码名称(必填，类型为String), 目前仅支持FAST_INSTANT_TRADE_PAY
//        bizContentMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
//        //对一笔交易的具体描述信息。 (非必填，类型为String), 最大长度128。
//        bizContentMap.put("body", "有钱城订单");
//        bizContentMap.put("timeout_express", "5m");
//
//        //填充业务参数
//        alipayRequest.setBizContent(JSONObject.toJSONString(bizContentMap));
//        String form="";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, e.getErrMsg());
//        }
////        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
////        response.getWriter().write(form);//直接将完整的表单html输出到页面
////        response.getWriter().flush();
////        response.getWriter().close();
//        return ResultVOUtils.success(form);
//    }
//
//   @PostMapping("pc/refund")
//    @ApiOperation(value = "退款接口：售后ID")
//    public ResultVo payController(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        if(id==null||id==0){
//            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
//        }
//
//        //单据号
//        String orderNo="";
//        //金额
//        BigDecimal money=new BigDecimal(0);
//        D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.deductInventory(id);
//        orderNo=d06PayOrderDO.getOrderNo();
//        money=d06PayOrderDO.getOrderPrice();
//
//
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID,
//                AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
//
//        //设置请求参数
//       AlipayTradePageRefundRequest alipayRequest = new AlipayTradePageRefundRequest();
//        //alipayRequest.setReturnUrl(AlipayConfig.return_url);
//        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
//        //下单时传递的请求参数, 详情参照（https://docs.open.alipay.com/api_1/alipay.trade.page.pay/）请求参数
//        Map<String, Object> bizContentMap = new TreeMap<String, Object>();
//        //商户网站唯一订单号(必填，类型为String), 最大长度64
//
//        bizContentMap.put("out_trade_no", orderNo);
//        //商品的标题/交易标题/订单标题/订单关键字等(必填，类型为String), 最大长度256。
//        bizContentMap.put("subject", "有钱城订单");
//        //订单总金额，单位为元，精确到小数点后两位(必填，类型为String), 最大长度9。
//        bizContentMap.put("total_amount", "0.01");
//      //  bizContentMap.put("total_amount",money.toString());
//        //销售产品码，与支付宝签约的产品码名称(必填，类型为String), 目前仅支持FAST_INSTANT_TRADE_PAY
//        bizContentMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
//        //对一笔交易的具体描述信息。 (非必填，类型为String), 最大长度128。
//        bizContentMap.put("body", "有钱城订单");
//        bizContentMap.put("timeout_express", "5m");
//
//        //填充业务参数
//        alipayRequest.setBizContent(JSONObject.toJSONString(bizContentMap));
//        String form="";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, e.getErrMsg());
//        }
////        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
////        response.getWriter().write(form);//直接将完整的表单html输出到页面
////        response.getWriter().flush();
////        response.getWriter().close();
//        return ResultVOUtils.success(form);
//    }
//
//
//    @ApiOperation(value = "APP获取支付宝签名接口:type:1、用户支付——下订单；2、买家支付——发红包；id: type=1时——订单id ，type =2时——红包发放记录ID")
//    @PostMapping("app/aliPaySign")
//    public JsonBean aliPaySign(Long id, Integer type) {
//        JsonBean JsonBean = new JsonBean();
//        logger.info("支付宝支付获取签名请求参数【{ orderId = " + id + "}】");
//        try {
//
//            if(id==null||id==0){
//                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
//            }
//            if(type==null||type==0){
//                throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"type不能为空");
//            }
//            //单据号
//            String orderNo="";
//            //金额
//            BigDecimal money=new BigDecimal(0);
//            if(type==TypeConstant.PayObjectType.user.getCode()){
//                D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.deductInventory(id);
//                orderNo=d06PayOrderDO.getOrderNo();
//                money=d06PayOrderDO.getOrderPrice();
//            }else{
//                orderNo="HB";
//                orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//                orderNo+=id;
//                E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
//                if(e04RedenvelopesGrantRecordDO==null){
//                    throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放信息不存在");
//                }
//                E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetClientService.get(e04RedenvelopesGrantRecordDO.getStreetId());
//                money=e01RedenvelopesStreetDO.getMoney();
//            }
//            //获得初始化的AlipayClient
//            //获得初始化的AlipayClient
//            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID,
//                    AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
//
//            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
//            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
//            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//            model.setBody("商品详情");
//            model.setSubject("商品名称");
//            model.setOutTradeNo(orderNo);
////            model.setTotalAmount(money.toString());
//            model.setTotalAmount("0.01");
//            model.setProductCode("QUICK_MSECURITY_PAY");
//            request.setBizModel(model);
//            request.setNotifyUrl(AlipayConfig.notify_url);
//            //这里和普通的接口调用不同，使用的是sdkExecute
//            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            logger.warn("返给前端data:"+response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
//            JsonBean.setData(response.getBody());
//            JsonBean.setMessage("支付签名成功！");
//            JsonBean.setStatus(Constants.$Success);
//        } catch (Exception e) {
//            logger.error("支付签名失败！", e);
//            JsonBean.setMessage("支付签名失败！");
//            JsonBean.setStatus(Constants.$Failure);
//        }
//        return JsonBean;
//    }
//
//
//
//    /**
//     * 支付宝回调方法
//     *
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/pay/notifyalipay")
//    @ResponseBody
//    public String payNotifyalipay(HttpServletRequest request){
//        logger.info("进入支付宝回调函数notifyalipay...");
//        //获取支付宝POST过来反馈信息
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            params.put(name, valueStr);
//        }
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//        //商户订单号
//        String out_trade_no = params.get("out_trade_no");
//        logger.info("order " + out_trade_no + " alipay callback begin");
//
//        //支付宝交易号
//        String trade_no = params.get("trade_no");
//        logger.info("order " + trade_no );
//
//        //交易状态
//        String trade_status = params.get("trade_status");
//        logger.info(" :trade_status = " + trade_status);
//
//        //交易金额
//        String total_amount = params.get("total_amount");
//        logger.info(" :total_amount = " + total_amount);
//
//        //异步通知ID
//        String notify_id=params.get("notify_id");
//        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//        if(notify_id!=""&&notify_id!=null){////判断接受的post通知中有无notify_id，如果有则是异步通知。
////            if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
////            {
//            if(checkSign(requestParams))//使用支付宝公钥验签
//            {
//                if(trade_status.equals("TRADE_FINISHED")){
//                    //判断该笔订单是否在商户网站中已经做过处理
//                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                    //如果有做过处理，不执行商户的业务程序
//                    //注意：
//                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
//
//                    //交易关闭——限时未完成则返还库存
//                    d06PayOrderClientService.addInventory(out_trade_no);
//
//                } else if (trade_status.equals("TRADE_SUCCESS")){
//                    //判断该笔订单是否在商户网站中已经做过处理
//                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
//                    //如果有做过处理，不执行商户的业务程序
//                    //注意：
//                    //付款完成后，支付宝系统发送该交易状态通知
//                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
//                    //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
//                    //订单操作逻辑
//                    //更新订单支付状态
//                    // 业务处理
//                    synchronized (this) {
//                        logger.info("系统订单号：" + out_trade_no);
//                        try {
//                            String substring = out_trade_no.substring(0,2);
//                            if(substring.equals("DD")){
//                                d06PayOrderClientService.updatePayStatus(out_trade_no,trade_no);
//                            }else{
//                                String str = out_trade_no.substring(16);
//                                Long id=Long.valueOf(str);
//                                e04RedenvelopesGrantRecordClientService.updateStatus(id);
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            A18SysMessageDO dto=new A18SysMessageDO();
//                            dto.setContent(e.getMessage());
//                            dto.setType(TypeConstant.NotifyType.ali.getCode());
//                            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
//                            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
//                            dto.setOrderNo( out_trade_no);
//                            dto.setCreateTime( LocalDateTime.now());
//                            dto.setUpdateTime( LocalDateTime.now());
//                            a18SysMessageDao.insert(dto);
//                            logger.error("订单 " + out_trade_no + " callback field");
//                        }
//                    }
//                }
//                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
//                logger.error("订单 " + out_trade_no + " callback success");
//
//                return "success";//请不要修改或删除
//            }
//            else//验证签名失败——则返还库存
//            {
//                A18SysMessageDO dto=new A18SysMessageDO();
//                dto.setContent("验证签名失败");
//                dto.setType(TypeConstant.NotifyType.ali.getCode());
//                dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
//                dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
//                dto.setOrderNo( out_trade_no);
//                dto.setCreateTime( LocalDateTime.now());
//                dto.setUpdateTime( LocalDateTime.now());
//                a18SysMessageDao.insert(dto);
//                logger.error("order " + out_trade_no + " : sign fail");
//                return "fail";
//            }
//        }
//        else
//        { //则返还库存
//            A18SysMessageDO dto=new A18SysMessageDO();
//            dto.setContent("no notify message");
//            dto.setType(TypeConstant.NotifyType.ali.getCode());
//            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
//            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
//            dto.setOrderNo( out_trade_no);
//            dto.setCreateTime( LocalDateTime.now());
//            dto.setUpdateTime( LocalDateTime.now());
//            a18SysMessageDao.insert(dto);
//            logger.error("order " + out_trade_no + " : no notify message");
//            return "fail";
//        }
//
//    }
//
////    /**
////     * 支付宝回调方法
////     *
////     * @param request
////     * @return
////     * @throws Exception
////     */
////    @PostMapping("/refund/notifyalipay")
////    @ResponseBody
////    public String refundNotifyalipay(HttpServletRequest request){
////        logger.info("进入支付宝回调函数notifyalipay...");
////        //获取支付宝POST过来反馈信息
////        Map<String,String> params = new HashMap<String,String>();
////        Map requestParams = request.getParameterMap();
////        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
////            String name = (String) iter.next();
////            String[] values = (String[]) requestParams.get(name);
////            String valueStr = "";
////            for (int i = 0; i < values.length; i++) {
////                valueStr = (i == values.length - 1) ? valueStr + values[i]
////                        : valueStr + values[i] + ",";
////            }
////            params.put(name, valueStr);
////        }
////        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
////        //商户订单号
////        String out_trade_no = params.get("out_trade_no");
////        logger.info("order " + out_trade_no + " alipay callback begin");
////
////        //支付宝交易号
////        String trade_no = params.get("trade_no");
////        logger.info("order " + trade_no );
////
////        //退款请求号
////        String out_request_no = params.get("out_request_no");
////        logger.info(" :out_request_no = " + out_request_no);
////
////        //本次退款请求金额
////        String refund_amount = params.get("refund_amount");
////        logger.info(" :refund_amount = " + refund_amount);
////
////        //异步通知ID
////        String notify_id=params.get("notify_id");
////        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
////        if(notify_id!=""&&notify_id!=null){////判断接受的post通知中有无notify_id，如果有则是异步通知。
//////            if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
//////            {
////            if(checkSign(requestParams))//使用支付宝公钥验签
////            {
////                if(trade_status.equals("TRADE_FINISHED")){
////                    //判断该笔订单是否在商户网站中已经做过处理
////                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
////                    //如果有做过处理，不执行商户的业务程序
////                    //注意：
////                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
////                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
////
////                    //交易关闭——限时未完成则返还库存
////                    d06PayOrderClientService.addInventory(out_trade_no);
////
////                } else if (trade_status.equals("TRADE_SUCCESS")){
////                    //判断该笔订单是否在商户网站中已经做过处理
////                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
////                    //如果有做过处理，不执行商户的业务程序
////                    //注意：
////                    //付款完成后，支付宝系统发送该交易状态通知
////                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
////                    //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
////                    //订单操作逻辑
////                    //更新订单支付状态
////                    // 业务处理
////                    synchronized (this) {
////                        logger.info("系统订单号：" + out_trade_no);
////                        try {
////                            String substring = out_trade_no.substring(0,2);
////                            if(substring.equals("DD")){
////                                d06PayOrderClientService.updatePayStatus(out_trade_no);
////                            }else{
////                                String str = out_trade_no.substring(16);
////                                Long id=Long.valueOf(str);
////                                e04RedenvelopesGrantRecordClientService.updateStatus(id);
////                            }
////
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                            A18SysMessageDO dto=new A18SysMessageDO();
////                            dto.setContent(e.getMessage());
////                            dto.setType(TypeConstant.NotifyType.ali.getCode());
////                            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
////                            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
////                            dto.setOrderNo( out_trade_no);
////                            dto.setCreateTime( LocalDateTime.now());
////                            dto.setUpdateTime( LocalDateTime.now());
////                            a18SysMessageDao.insert(dto);
////                            logger.error("订单 " + out_trade_no + " callback field");
////                        }
////                    }
////                }
////                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
////                logger.error("订单 " + out_trade_no + " callback success");
////
////                return "success";//请不要修改或删除
////            }
////            else//验证签名失败——则返还库存
////            {
////                A18SysMessageDO dto=new A18SysMessageDO();
////                dto.setContent("验证签名失败");
////                dto.setType(TypeConstant.NotifyType.ali.getCode());
////                dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
////                dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
////                dto.setOrderNo( out_trade_no);
////                dto.setCreateTime( LocalDateTime.now());
////                dto.setUpdateTime( LocalDateTime.now());
////                a18SysMessageDao.insert(dto);
////                logger.error("order " + out_trade_no + " : sign fail");
////                return "fail";
////            }
////        }
////        else
////        { //则返还库存
////            A18SysMessageDO dto=new A18SysMessageDO();
////            dto.setContent("no notify message");
////            dto.setType(TypeConstant.NotifyType.ali.getCode());
////            dto.setErrorCode(TypeConstant.ErrorType.add_inventory.getCode());
////            dto.setErrorDes(TypeConstant.ErrorType.add_inventory.getMsg());
////            dto.setOrderNo( out_trade_no);
////            dto.setCreateTime( LocalDateTime.now());
////            dto.setUpdateTime( LocalDateTime.now());
////            a18SysMessageDao.insert(dto);
////            logger.error("order " + out_trade_no + " : no notify message");
////            return "fail";
////        }
////
////    }
//
//
//    public Boolean checkSign(Map<String,String[]> requestParams){
//        //获取支付宝POST过来反馈信息
//        Map<String,String> params = new HashMap<String,String>();
//        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。
//            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            params.put(name, valueStr);
//        }
//        boolean flag = false;
//        try {
//            flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.sign_type);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    public static void main(String[] args) {
//        String orderNo="HB2020060203291912";
//        System.out.println(orderNo.substring(15));
//    }
//
//}
