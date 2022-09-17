package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.DecimalUtil;
import com.handongkeji.util.manager.ResultVOUtils;
import com.youqiancheng.controller.alipay.RefundUtil;
import com.youqiancheng.controller.wechatpay.WeiXinRefundUtil;
import com.youqiancheng.form.shop.CustomerServiceUpdateForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.client.service.D01OrderClientService;
import com.youqiancheng.service.shop.CustomerServiceService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.client.D01OrderClientVO;
import com.youqiancheng.vo.result.ResultEnum;
import com.youqiancheng.vo.result.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/21 13:24
 * @Version: V1.0
 */
@Service
@Transactional
public class CustomerServiceServiceImpl implements CustomerServiceService {
    @Resource
    private D03CustomerServiceDao d03CustomerServiceDao;
    @Resource
    private D02OrderItemDao  d02OrderItemDao;
    @Resource
    private D06PayOrderDao d06PayOrderDao;
    @Resource
    private RefundUtil aliRefund;
    @Resource
    private WeiXinRefundUtil weiXinRefundUtil;
    @Resource
    private B02UserAccountDao b02UserAccountDao;
    @Resource
    private B03UserAccountFlowDao b03UserAccountFlowDao;
    @Resource
    private C09GoodsSkuDao c09GoodsSkuDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Autowired
    private D01OrderClientService d01OrderClientService;

    @Autowired
    private A15MessageDao a15MessageDao;
    @Resource
    private A17MessageUserDao a17MessageUserDao;

    @Override
    public D03CustomerServiceDO get(Long id) {
        return d03CustomerServiceDao.get(id);
    }

    @Override
    public List<D03CustomerServiceDO> list(Map<String, Object> map) {
        return d03CustomerServiceDao.list(map);
    }

    @Override
    public List<D03CustomerServiceDO> listHDPage(Map<String, Object> map) {
        return d03CustomerServiceDao.listHDPage(map);
    }
    @Override
    public List<D03CustomerServiceDO> listHDPageByManage(Map<String, Object> map) {
        return d03CustomerServiceDao.listByManageHDPage(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return d03CustomerServiceDao.count(map);
    }

    @Override
    public int insertBatch(List<D03CustomerServiceDO> d03CustomerServices) {
        return d03CustomerServiceDao.insertBatch(d03CustomerServices);
    }

    @Override
    public int updateList(List<D03CustomerServiceDO> d03CustomerServices) {
        return d03CustomerServiceDao.updateList(d03CustomerServices);
    }

    @Override
    public int examineOrderPass(CustomerServiceUpdateForm form) {
        D03CustomerServiceDO d03CustomerServiceDO = d03CustomerServiceDao.get(form.getId());
        if(d03CustomerServiceDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后信息不存在");
        }
        d03CustomerServiceDO.setExamineStatus(StatusConstant.ExamineStatus.adopt.getCode());
        d03CustomerServiceDO.setStatus(StatusConstant.CustomServiceStatus.pass.getCode());
        d03CustomerServiceDO.setExamineTime(LocalDateTime.now());
        d03CustomerServiceDao.updateById(d03CustomerServiceDO);

        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(d03CustomerServiceDO.getOrderItemId());
        if(d02OrderItemDO!=null){
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.pass.getCode());
            d02OrderItemDao.updateById(d02OrderItemDO);
        }
        return 1;
    }
    @Override
    public ResultVo examineOrderPassApp(CustomerServiceUpdateForm form) {
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(form.getId());
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("orderItemId",d02OrderItemDO.getId());
        List<D03CustomerServiceDO> list = d03CustomerServiceDao.list(map);
        if(CollectionUtils.isEmpty(list)){
           throw new JsonException(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        D03CustomerServiceDO d03CustomerServiceDO = list.get(0);
        if(d03CustomerServiceDO==null){
            throw  new JsonException(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        d03CustomerServiceDO.setExamineStatus(StatusConstant.ExamineStatus.adopt.getCode());
        d03CustomerServiceDO.setStatus(StatusConstant.CustomServiceStatus.pass.getCode());
        d03CustomerServiceDO.setExamineTime(LocalDateTime.now());
        d03CustomerServiceDao.updateById(d03CustomerServiceDO);
        if(d02OrderItemDO!=null){
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.pass.getCode());
            d02OrderItemDao.updateById(d02OrderItemDO);
        }

        //退款
        return refundOrder(d03CustomerServiceDO.getId());
    }
    @Override
    public int examineOrderRefuse(CustomerServiceUpdateForm form) {
        D03CustomerServiceDO d03CustomerServiceDO = d03CustomerServiceDao.get(form.getId());
        if(d03CustomerServiceDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后信息不存在");
        }
        d03CustomerServiceDO.setExamineStatus(StatusConstant.ExamineStatus.refuse.getCode());
        d03CustomerServiceDO.setStatus(StatusConstant.CustomServiceStatus.refuse.getCode());
        d03CustomerServiceDO.setRefuseReason(form.getRefuseReason());
        d03CustomerServiceDO.setExamineTime(LocalDateTime.now());
         d03CustomerServiceDao.updateById(d03CustomerServiceDO);
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(d03CustomerServiceDO.getOrderItemId());
        if(d02OrderItemDO!=null) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refuse.getCode());
            d02OrderItemDao.updateById(d02OrderItemDO);
        }
        //根据商家订单创建消息体
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("您的退款申请被拒绝，订单："+d03CustomerServiceDO.getOrderNo());
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        //messageDo.setUpdatePerson(d01OrderDO.getShopName());
        //messageDo.setSendId(d01OrderDO.getShopId());
        //messageDo.setCreatePerson(d01OrderDO.getShopName());
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("退款被拒绝");
        //messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
        //messageDo.setShopOrderId(d01OrderDO.getId());
        Integer insert = a15MessageDao.insert(messageDo);
        if(insert<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息插入失败");
        }
        //查找消息接收人
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        //发送消息
        A17MessageUserDO dto=new A17MessageUserDO();
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        dto.setIsRead(StatusConstant.IsRead.un_read.getCode());
        dto.setType(TypeConstant.MessageReadType.buyer.getCode());
        dto.setMessageId(messageDo.getId());
        dto.setUserId(d03CustomerServiceDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送失败");
        }
        try {
            String alias = "yqc_" + d03CustomerServiceDO.getUserId();
            String aliastype = "yqc_type";
            String content = form.getRefuseReason();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, "退款申请被拒绝","", content, "1","1",null,null,null);  // IOS 单推
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, "退款申请被拒绝", "", content, "1","1",null,null,null); // android 单推
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败"+e.getMessage());
        }
        return 1;

    }

    @Override
    public int examineOrderRefuseApp(CustomerServiceUpdateForm form) {
        //查询订单明细
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(form.getId());
        //查询售后信息
        QueryMap map=new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map.put("orderItemId",d02OrderItemDO.getId());
        List<D03CustomerServiceDO> list = d03CustomerServiceDao.list(map);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"数据不存在");
        }
        //修改售后状态
        D03CustomerServiceDO d03CustomerServiceDO = list.get(0);
        d03CustomerServiceDO.setExamineStatus(StatusConstant.ExamineStatus.refuse.getCode());
        d03CustomerServiceDO.setStatus(StatusConstant.CustomServiceStatus.refuse.getCode());
        d03CustomerServiceDO.setRefuseReason(form.getRefuseReason());
        d03CustomerServiceDO.setExamineTime(LocalDateTime.now());
         d03CustomerServiceDao.updateById(d03CustomerServiceDO);
         //
        if(d02OrderItemDO!=null) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refuse.getCode());
            d02OrderItemDao.updateById(d02OrderItemDO);
        }
        return 1;
    }

    @Override
    public ResultVo refundOrder(Long id) {
        //查询售后信息
        D03CustomerServiceDO d03CustomerServiceDO = d03CustomerServiceDao.get(id);
        if(d03CustomerServiceDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后服务信息不存在");
        }
        //查询订单信息
        D02OrderItemDO d02OrderItemDO = d02OrderItemDao.get(d03CustomerServiceDO.getOrderItemId());
        if(d02OrderItemDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"售后服务信息关联订单明细不存在");
        }
        //退库存
        C09GoodsSkuDO c09GoodsSkuDO = c09GoodsSkuDao.get(d02OrderItemDO.getSkuId());
        if(c09GoodsSkuDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"库存信息不存在");
        }

        c09GoodsSkuDO.setNum(c09GoodsSkuDO.getNum()+d02OrderItemDO.getNum());
        c09GoodsSkuDao.updateById(c09GoodsSkuDO);

        //退商家账户余额,收益
        F05ShopAccountDO shopAccountByIds = f05ShopAccountDao.getShopAccountById(d02OrderItemDO.getShopId());
        BigDecimal accountBalance = shopAccountByIds.getAccountBalance();//商户账户总金额
        BigDecimal bigDecimal =accountBalance.subtract(d02OrderItemDO.getTotalPrice());
        if(bigDecimal.intValue()>=0){
            shopAccountByIds.setAccountBalance(bigDecimal);
            shopAccountByIds.setAvailableBalance(bigDecimal);
            f05ShopAccountDao.updateById(shopAccountByIds);
        }

        //退今日收益
//        f15ShopProfitDao.delete(new EntityWrapper<F15ShopProfitDO>()
//                .eq("shop_id",d02OrderItemDO.getShopId()).eq("day_income",d02OrderItemDO.getTotalPrice()));
        //支付宝退款
        D06PayOrderDO d06PayOrderDO = d06PayOrderDao.get(d02OrderItemDO.getPayOrderId());
        if(StatusConstant.payType.AliPay.getCode()==d06PayOrderDO.getPayType()){
            return  aliRefund.payController(id);
        }//微信退款
        else if(StatusConstant.payType.Wechat.getCode()==d06PayOrderDO.getPayType()){
            return weiXinRefundUtil.weixinRefund(id);
        }else{//余额退款
            ResultVo resultVo = refundAccount(d03CustomerServiceDO);
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
            d02OrderItemDao.updateById(d02OrderItemDO);
            return resultVo;
        }

    }

    public ResultVo refundAccount(D03CustomerServiceDO form){
        //修改用户余额
        Map<String,Object> map=new HashMap<>();
        map.put("userId",form.getUserId());
        map.put("countryId", TypeConstant.country.china.getCode());
        List<B02UserAccountDO> list = b02UserAccountDao.list(map);
        if(list==null||list.isEmpty()){
            throw new  JsonException(ResultEnum.UPDATE_FAIL,"用户账户不存在");
        }
        B02UserAccountDO b02UserAccountDO = list.get(0);

        //新增用户账户流水
        B03UserAccountFlowDO entity=new B03UserAccountFlowDO();
        entity.setAccountId(b02UserAccountDO.getId());
        entity.setType(TypeConstant.UserAccountType.refund_incone.getCode());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setCreateTime(LocalDateTime.now());
        entity.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        //原账户金额=b02里账户余额
        entity.setOriginalMoney(b02UserAccountDO.getAccountBalance());
        //流水金额=退款金额
        entity.setMoney(form.getMoney());

        BigDecimal money = b02UserAccountDO.getAccountBalance()==null? new BigDecimal("0"):b02UserAccountDO.getAccountBalance();
        BigDecimal finalMoney=money.add(form.getMoney()==null? new BigDecimal("0"):form.getMoney());//加上退款的钱
        entity.setFinalMoney(finalMoney);
        b03UserAccountFlowDao.insert(entity);

        //修改用户余额
        b02UserAccountDO.setAccountBalance(finalMoney);
        b02UserAccountDao.updateById(b02UserAccountDO);

        //修改售后信息
        form.setStatus(StatusConstant.CustomServiceStatus.refund.getCode());
        form.setRefundType(StatusConstant.payType.Balance.getCode());
        d03CustomerServiceDao.updateById(form);
        //修改订单状态
//        D01OrderClientVO d01OrderClientVO = d01OrderClientService.get(form.getOrderId());
//        D01OrderDO d01OrderDO = new D01OrderDO();
//        d01OrderDO.setId(d01OrderClientVO.getId());
//        d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
//        d01OrderClientService.update(d01OrderDO);
        return ResultVOUtils.success("退款成功");
    }

}


