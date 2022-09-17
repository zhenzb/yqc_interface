package com.youqiancheng.controller.wechatpay;

import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.wechatpay.weixinpay.UniCallbackReqData;
import com.youqiancheng.controller.wechatpay.weixinpay.UnifiedOrderApp;
import com.youqiancheng.controller.wechatpay.weixinpay.config.WexinPayConfig;
import com.youqiancheng.controller.wechatpay.weixinpay.sign.SignUtil;
import com.youqiancheng.mybatis.dao.A18SysMessageDao;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.C12RewardRecordClientService;
import com.youqiancheng.service.client.service.D06PayOrderClientService;
import com.youqiancheng.service.client.service.E01RedenvelopesStreetClientService;
import com.youqiancheng.service.client.service.E04RedenvelopesGrantRecordClientService;
import com.youqiancheng.service.maile.service.MailService;
import com.youqiancheng.util.QrCodeUtil;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Api(tags  = "微信支付")
@RestController
@RequestMapping("/wxpay")
public class WeiXinPayController {
    protected static final Log logger = LogFactory.getLog(WeiXinPayController.class);

    @Resource
    private D06PayOrderClientService d06PayOrderClientService;
    @Resource
    private A18SysMessageDao a18SysMessageDao;
    @Resource
    private E04RedenvelopesGrantRecordClientService e04RedenvelopesGrantRecordClientService;
    @Resource
    private E01RedenvelopesStreetClientService e01RedenvelopesStreetClientService;

    @Autowired
    private C12RewardRecordClientService c12RewardRecordService;

    @Autowired
    private MailService mailService;

    @ApiOperation(value = "APP获取微信签名接口:type:1、用户支付——下订单，2、买家支付——发红包，3——打赏" +
            "id:type=1时——订单id ，type =2时——红包发放记录ID,type=3时——红包打赏记录")
    @PostMapping("/weixinPaySign")
    public ResultVo weixinPaySign(Long id, Integer type) {
        logger.info("微信支付获取签名请求参数【{orderId = " + id + "}】");
        /**********业务流程调用参数************/
        PayInfo payInfo = getPayInfo(id, type);
        /********支付流程*******/
        try {
            // 获取请求参数
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attrs.getRequest();
            UnifiedOrderApp app = null;
            String attach = "";
            app = SignUtil.getUnifiedOrderResultForApp(request, WexinPayConfig.wx_notify, attach,
                    "[商品]订单" + payInfo.getOrderNo(), payInfo.getOrderNo(), payInfo.getMoney().toString());

            if (app != null) {
                return ResultVOUtils.success(app);
            } else {
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"支付签名失败！");
            }
        } catch (Exception e) {
            logger.error("支付签名失败！", e);
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"支付签名失败！");
        }
    }


    @ApiOperation(value = "PC获取微信二维码接口:type:1、用户支付——下订单，2、买家支付——发红包，3——打赏" +
            "id:type=1时——订单id ，type =2时——红包发放记录ID,type=3时——红包打赏记录")
    @PostMapping("/weixinPayCode")
    public ResultVo weixinPayCode(Long id, Integer type, HttpServletResponse response) {
        logger.info("微信支付获取签名请求参数【{orderId = " + id + "}】");
        /**********业务流程调用参数************/
        PayInfo payInfo = getPayInfo(id, type);
        /********支付流程*******/
        try {
            // 获取请求参数
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attrs.getRequest();
            String imgUrl = null;
            String attach = "";
            imgUrl = SignUtil.getUnifiedOrderResultForPC(request, WexinPayConfig.wx_notify, attach,
                    "[商品]订单" + payInfo.getOrderNo(), payInfo.getOrderNo(), payInfo.getMoney().toString());
            if (imgUrl != null) {
                ServletOutputStream stream = null;
                try {
                    stream = response.getOutputStream();
                    //使用工具类生成二维码
                    QrCodeUtil.encode(imgUrl, stream);
                } finally {
                    if (stream != null) {
                        stream.flush();
                        stream.close();
                    }
                }
            } else {
                return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！");
            }
        } catch (Exception e) {
            logger.error("获取二维码失败！", e);
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！");
        }
        return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"获取二维码失败！");
    }

    @ApiOperation(value = "回调接口")
    @PostMapping("/notifyWxpay")
    public void notifyWxpay(HttpServletRequest request, HttpServletResponse response) {
        logger.info("============微信支付回调通知notifyWxpay===============");
        response.setContentType("text/html;charset=utf-8");
        //回调返回值
        String responseXML = "";
        PrintWriter out = null;
        try {
            out = response.getWriter();
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            UniCallbackReqData reqData = (UniCallbackReqData) SignUtil.getObjectFromXML(result, UniCallbackReqData.class);
            if (reqData != null) {
                String return_code = reqData.getReturn_code();
                // 商户订单号
                String out_trade_no = reqData.getOut_trade_no();
                // 微信交易订单号
                String transaction_id = reqData.getTransaction_id();

                //交易金额
                Integer totalFee1 = reqData.getTotal_fee();

                // 商家附加参数数据，微信会原样返回
                String userid = reqData.getAttach();  //用户ID
                // 金额
                String total_fee = "0";
                if ("SUCCESS".equals(return_code) && SignUtil.isTenpaySign(SignUtil.obj2Map(reqData), reqData.getSign())) {
                    logger.info("=========微信支付成功===========");
                    //回调业务逻辑处理
                    // 业务处理
                    synchronized (this) {
                        logger.info("系统订单号：" + out_trade_no);
                        // 参数校验
                        String mixstr = transaction_id;
                        Long orderid = null;
                        //String[] mixarr = null;
                        try {
                            if (mixstr == null) {
                                throw new Exception();
                            }
                            //mixarr = mixstr.split("@");
                            //order_id = Long.parseLong(mixarr[0]);
                        } catch (Exception e1) {
                            // 支付失败 ,直接返回
                            e1.printStackTrace();
                        }
                        try {
                            updateSuccessStatus(out_trade_no,transaction_id,totalFee1);

                        } catch (Exception e) {
                            e.printStackTrace();
                            A18SysMessageDO dto=new A18SysMessageDO();
                            dto.setContent(e.getMessage());
                            dto.setType(TypeConstant.NotifyType.wechat.getCode());
                            dto.setOrderNo( out_trade_no);
                            dto.setCreateTime( LocalDateTime.now());
                            dto.setUpdateTime( LocalDateTime.now());
                            a18SysMessageDao.insert(dto);
                        }
                    }
                    //回写回调成功标识
                    responseXML = SignUtil.setXML("SUCCESS", "");
                }
                else {
                    updateFailStatus(out_trade_no);
                    responseXML = SignUtil.setXML("FAIL", "");
                    A18SysMessageDO dto=new A18SysMessageDO();
                    dto.setContent("微信支付通知失败====回调验签失败######");
                    dto.setType(TypeConstant.NotifyType.wechat.getCode());
                    dto.setOrderNo( out_trade_no);
                    dto.setCreateTime( LocalDateTime.now());
                    dto.setUpdateTime( LocalDateTime.now());
                    a18SysMessageDao.insert(dto);
                    logger.info("微信支付通知失败====回调验签失败######");
                }
            } else {
                responseXML = SignUtil.setXML("FAIL", "");
                logger.info("微信支付通知失败====无返回数据######");
                A18SysMessageDO dto=new A18SysMessageDO();
                dto.setContent("微信支付通知失败====无返回数据######");
                dto.setType(TypeConstant.NotifyType.wechat.getCode());
                dto.setOrderNo("");
                dto.setCreateTime( LocalDateTime.now());
                dto.setUpdateTime( LocalDateTime.now());
                a18SysMessageDao.insert(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("微信支付回调失败：" + e.getMessage());
        }
        if (out != null) {
            out.print(responseXML);
            out.flush();
            out.close();
            out = null;
        }
    }

    @ApiOperation(value = "PC轮询查询支付是否成功；参数：type——1、订单，2、发红包，3、打赏；" +
            "id——type=1时 订单id，type =2时  红包发放记录ID，type=3时 红包打赏记录；" +
            "结果——1、支付成功，0未支付")
    @GetMapping("/PC/getPayStatus")
    public ResultVo getPayStatus(int type,Long id){
        if(id==null||id==0||type==0){
            ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST,"ID或者类型不能为空");
        }
        if(type==1){
            D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.get(id);
            if(d06PayOrderDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单不存在");
            }
            if(StatusConstant.OrderStatus.pay.getCode()==d06PayOrderDO.getOrderStatus()){
                return ResultVOUtils.success(1);
            }
        }else if(type==2){
            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
            if(e04RedenvelopesGrantRecordDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放信息不存在");
            }
            if(2==e04RedenvelopesGrantRecordDO.getEndFlag()){//1——未支付，2——已支付，3——结束
                return ResultVOUtils.success(1);
            }
        }else{
            C12RewardRecordDO c12RewardRecordDO = c12RewardRecordService.get(id);
            if(c12RewardRecordDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"打赏记录不存在");
            }
            if(c12RewardRecordDO.getStatus()==2){//1——未支付；2支付
                return ResultVOUtils.success(1);
            }
        }
        return ResultVOUtils.success(0);
    }




    PayInfo getPayInfo(Long id,Integer type){
        PayInfo payInfo=new PayInfo();
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        if(type==null||type==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"type不能为空");
        }
        //商家订单号
        String orderNo="";
        //支付金额
        BigDecimal money=new BigDecimal(0);
        //如果是用户订单则查询支付订单信息
        if(type== TypeConstant.PayObjectType.user.getCode()){
            D06PayOrderDO d06PayOrderDO = d06PayOrderClientService.deductInventory(id);
            orderNo=d06PayOrderDO.getOrderNo();
            money=d06PayOrderDO.getOrderPrice();
        }//如果商家发红包则创建红包订单号:HB+时间流水+id，查询金额
        else if(type==TypeConstant.PayObjectType.shop.getCode()){
            orderNo="HB";
            orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            orderNo+=id;
            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
            if(e04RedenvelopesGrantRecordDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"红包发放信息不存在");
            }
            E01RedenvelopesStreetDO e01RedenvelopesStreetDO = e01RedenvelopesStreetClientService.get(e04RedenvelopesGrantRecordDO.getStreetId());
            money=e01RedenvelopesStreetDO.getMoney();
        }//用户打赏，创建订单号DS+时间流水+id，查询金额
        else{
            orderNo="DS";
            orderNo+= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            orderNo+=id;
            C12RewardRecordDO c12RewardRecordDO = c12RewardRecordService.get(id);
            if(c12RewardRecordDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"打赏记录不存在");
            }
            money=c12RewardRecordDO.getMoney();
        }
        payInfo.setMoney(money);
        payInfo.setOrderNo(orderNo);
        return payInfo;
    }

    /**
     * 功能描述: <br>
     * 〈失败状态修改〉

     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date:
     */
    void updateFailStatus(String out_trade_no){
        String substring = out_trade_no.substring(0,2);
        if(substring.equals("DD")){
            d06PayOrderClientService.addInventory(out_trade_no);
        }else if(substring.equals("HB")){
            String str = out_trade_no.substring(16);
            Long id=Long.valueOf(str);
            E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecordDO = e04RedenvelopesGrantRecordClientService.get(id);
            e04RedenvelopesGrantRecordDO.setEndFlag(StatusConstant.EndFlag.end.getCode());
            e04RedenvelopesGrantRecordClientService.update(e04RedenvelopesGrantRecordDO);
        }else{
            String str = out_trade_no.substring(16);
            Long id=Long.valueOf(str);
            C12RewardRecordDO c12RewardRecordDO = c12RewardRecordService.get(id);
            c12RewardRecordDO.setStatus(StatusConstant.PayStatus.fail.getCode());
            c12RewardRecordService.update(c12RewardRecordDO);
        }
    }


    void updateSuccessStatus(String out_trade_no,String transaction_id,Integer totalFee1){
        //业务处理
        String substring = out_trade_no.substring(0,2);
        if(substring.equals("DD")){
            long id = d06PayOrderClientService.updatePayStatus(out_trade_no, transaction_id, StatusConstant.payType.Wechat.getCode());
            try{
                mailService.sendMail("微信商品订单交易","流水号："+out_trade_no+",交易金额："+totalFee1);
            }catch (Exception e){}
        }else if(substring.equals("HB")){
            String str = out_trade_no.substring(16);
            Long id=Long.valueOf(str);
            e04RedenvelopesGrantRecordClientService.updateStatus(id,transaction_id, StatusConstant.payType.Wechat.getCode());
            try{
                mailService.sendMail("微信上街红包交易","流水号："+out_trade_no+",交易金额："+totalFee1);
            }catch (Exception e){}
        }else{
            String str = out_trade_no.substring(16);
            Long id=Long.valueOf(str);
            c12RewardRecordService.updateStatus(id,transaction_id, StatusConstant.payType.Wechat.getCode());
            try{
                mailService.sendMail("微信打赏商家交易","流水号："+out_trade_no+",交易金额："+totalFee1);
            }catch (Exception e){}
        }
    }

}
