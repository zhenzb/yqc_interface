/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: RefundUtil
 * Author:   ytf
 * Date:     2020/6/11 17:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.controller.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.service.client.service.D02OrderItemClientService;
import com.youqiancheng.service.client.service.D06PayOrderClientService;
import com.youqiancheng.vo.client.D01OrderClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/11
 * @since 1.0.0
 */
@Component
public class RefundUtil {
    protected static final Log logger = LogFactory.getLog(PayController.class);

    @Resource
    private D06PayOrderClientService d06PayOrderClientService;
    @Resource
    private F06WithdrawalApplicationDao withdrawalApplicationDao;
    @Resource
    private AliPayConfig aliPayConfig;
    @Autowired
    private D01OrderClientService d01OrderClientService;
    @Resource
    private F01ShopDao f01ShopDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private A15MessageDao a15MessageDao;
    @Resource
    private A17MessageUserDao a17MessageUserDao;
    @Autowired
    private D02OrderItemClientService d02OrderItemAppService;

    public ResultVo payController(Long id){
       /*** 业务流程获取退款数据******/
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        //单据号
        String orderNo="";
        //金额
        BigDecimal money=new BigDecimal(0);
        D03CustomerServiceDO customService = d06PayOrderClientService.getCustomService(id);
        orderNo=customService.getTradeNo();
        money=customService.getMoney();

        /*************退款流程***************/
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = aliPayConfig.alipayClientCert();
            //设置请求参数
            AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
            //下单时传递的请求参数, 详情参照（https://docs.open.alipay.com/api_1/alipay.trade.page.pay/）请求参数
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            model.setTradeNo(orderNo);
            model.setRefundAmount(money.toString());
            model.setRefundReason("商品退款");
            alipayRequest.setBizModel(model);
            //填充业务参数
            AlipayTradeRefundResponse execute = alipayClient.certificateExecute(alipayRequest);
            if(execute.isSuccess()){
                System.out.println("退款调用成功");
                /***保存退款信息*****/
                customService.setRefundType(StatusConstant.payType.AliPay.getCode());
                customService.setRefundNo(execute.getTradeNo());
                customService.setStatus(StatusConstant.CustomServiceStatus.refund.getCode());
                d06PayOrderClientService.updateCustomService(customService);
              //  D01OrderClientVO d01OrderClientVO = d01OrderClientService.get(customService.getOrderId());
//                D01OrderDO d01OrderDO = new D01OrderDO();
//                d01OrderDO.setId(d01OrderClientVO.getId());
//                d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
//                d01OrderClientService.update(d01OrderDO);
                D02OrderItemDO d02OrderItemDO = d02OrderItemAppService.get(customService.getOrderItemId());
                d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
                d02OrderItemAppService.update(d02OrderItemDO);
                return ResultVOUtils.success("退款成功");
            } else {
                System.out.println("调用失败");
                return ResultVOUtils.success("退款失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, e.getErrMsg());
        }
    }
    public ResultVo transferAccounts(Long id,String name,F05ShopAccountDO f05ShopAccountDO){
       /*** 业务流程获取转账数据******/
        if(id==null||id==0){
            throw new JsonException(ResultEnum.PARAM_VERIFY_FAIL,"id不能为空");
        }
        //单据号
        String orderNo="";
        //金额
        BigDecimal money=new BigDecimal(0);
        F06WithdrawalApplicationDO transferAccountsInfo = d06PayOrderClientService.getTransferAccountsInfo(id);
        orderNo=transferAccountsInfo.getOrderNo();
        money=transferAccountsInfo.getActualWithdrawalMoney();

        /*************转账***************/
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = aliPayConfig.alipayClientCert();
            //设置请求参数
            AlipayFundTransUniTransferRequest alipayRequest = new AlipayFundTransUniTransferRequest();
            //下单时传递的请求参数, 详情参照（https://docs.open.alipay.com/api_1/alipay.trade.page.pay/）请求参数
            AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
            //model.setOutBizNo(orderNo);
            model.setOutBizNo("tx2012919313131"+System.currentTimeMillis());
            model.setBizScene("DIRECT_TRANSFER");
            model.setTransAmount(money.toString());//"0.01"
            model.setProductCode("TRANS_ACCOUNT_NO_PWD");//单笔无密转账到支付宝账户固定为TRANS_ACCOUNT_NO_PWD
            model.setOrderTitle("提现转账");
            Participant participant=new Participant();
            if(transferAccountsInfo.getAccount().length()<5){
                return ResultVOUtils.error(ResultEnum.NOT_NETWORK,"账户错误");
            }
            String substring = transferAccountsInfo.getAccount().substring(0, 4);

            if("2088".equals(substring)){
                participant.setIdentity(transferAccountsInfo.getAccount()); //2088902380159885
                participant.setIdentityType("ALIPAY_USER_ID"); //ALIPAY_LOGON_ID     ALIPAY_USER_ID
            }else{
                participant.setIdentity(transferAccountsInfo.getAccount());
                participant.setName(name);
                participant.setIdentityType("ALIPAY_LOGON_ID");
            }
            model.setPayeeInfo(participant);
            alipayRequest.setBizModel(model);
            String result="";
            //填充业务参数
            AlipayFundTransUniTransferResponse execute = alipayClient.certificateExecute(alipayRequest);
            if(execute.isSuccess()){
                System.out.println("转账调用成功");
                /***保存转账信息*****/
                transferAccountsInfo.setTransferNo(execute.getOrderId());
                transferAccountsInfo.setStatus(StatusConstant.TransferStatus.transfer.getCode());
                d06PayOrderClientService.updateTransfer(transferAccountsInfo);
                result="转账成功";
                sendUserMessage(f05ShopAccountDO,result,execute.getSubMsg());
                return ResultVOUtils.success("转账成功");
            } else {
                System.out.println("调用失败");
                F06WithdrawalApplicationDO f06WithdrawalApplicationDO = withdrawalApplicationDao.selectById(id);
                f06WithdrawalApplicationDO.setExamineStatus(1);
                withdrawalApplicationDao.updateById(f06WithdrawalApplicationDO);
                result="转账失败";
                sendUserMessage(f05ShopAccountDO,result,execute.getSubMsg());
                return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FAIL,"转账失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultVOUtils.error(ResultEnum.DATA_NOT_EXIST, e.getErrMsg());
        }
    }


    //发送提现审核消息给用户
        public void sendUserMessage(F05ShopAccountDO f05ShopAccountDO,String result,String reason){
            //根据商家订单创建消息体
            F01ShopDO f01ShopDO = f01ShopDao.get(f05ShopAccountDO.getShopId());
            A15MessageDO messageDo=new A15MessageDO();
            messageDo.setContent("您的提现申请"+result+", 原因: "+reason);
            messageDo.setUpdateTime(LocalDateTime.now());
            messageDo.setCreateTime(LocalDateTime.now());
//                messageDo.setUpdatePerson(d01OrderDO.getShopName());
//                messageDo.setSendId(d01OrderDO.getShopId());
//                messageDo.setCreatePerson(d01OrderDO.getShopName());
            messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
            messageDo.setTitle("提现结果通知");
            //messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
            // messageDo.setShopOrderId(d01OrderDO.getId());
            Integer insert = a15MessageDao.insert(messageDo);
            if(insert<=0){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
            }
            //查找消息接收人
            // QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
            //发送消息
            A17MessageUserDO dto=new A17MessageUserDO();
            dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
            dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
            dto.setType(TypeConstant.MessageReadType.buyer.getCode());
            dto.setMessageId(messageDo.getId());
            dto.setUserId(f01ShopDO.getUserId());
            Integer insert1 = a17MessageUserDao.insert(dto);
            if(insert1<=0){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送失败");
            }
        }
}
