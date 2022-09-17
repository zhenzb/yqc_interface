package com.youqiancheng.ability;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.util.DecimalUtil;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.util.sendMessage.SendSmsUtil;
import com.youqiancheng.vo.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class ConfirmReceiveAbility {

    @Autowired
    private D01OrderDao d01OrderDao;
    @Autowired
    private D02OrderItemDao d02OrderItemDao;
    @Autowired
    private F05ShopAccountDao f05ShopAccountDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Autowired
    private F07ShopAccountFlowDao f07ShopAccountFlowDao;
    @Autowired
    private SendSmsUtil sendSmsUtil;

    public int confirmReceive(Long id) {
        //查询商家订单
        D01OrderDO d01OrderDO = d01OrderDao.get(id);
        if(d01OrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到商家订单");
        }
        //修改状态为已完成
        d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
        d01OrderDO.setEndTime(LocalDateTime.now());
        d01OrderDO.setTakeTime(LocalDateTime.now());

        Integer integer = d01OrderDao.updateById(d01OrderDO);


        //3:查询订单明细
        List<D02OrderItemDO> orderItmeByOrderId = d02OrderItemDao.selectList(
                new EntityWrapper<D02OrderItemDO>()
                        .eq("order_id",id)
        );
        //修改订单明细
        for (D02OrderItemDO aDoi : orderItmeByOrderId) {
            aDoi.setOrderStatus(StatusConstant.OrderStatus.end.getCode());
        }
        d02OrderItemDao.updateList(orderItmeByOrderId);

        //计入收益
//        List<F15ShopProfitDO> f15ShopProfitDOS = f15ShopProfitDao.selectList(
//                new EntityWrapper<F15ShopProfitDO>()
//                        .eq("shop_id",d01OrderDO.getShopId())
//
//        );
        //判断是否存在商家台账，不存在增加，存在修改
//        if(CollectionUtils.isEmpty(f15ShopProfitDOS)){
        F15ShopProfitDO dto=new F15ShopProfitDO();
        dto.setDayIncome(d01OrderDO.getOrderPrice());
        dto.setTotalIncome(d01OrderDO.getOrderPrice());
        dto.setShopId(d01OrderDO.getShopId());
        dto.setUpdateTime(LocalDateTime.now());
        dto.setUpdatePerson(d01OrderDO.getUserPhone());
        dto.setCreatePerson(d01OrderDO.getUserPhone());
        dto.setCreateTime(LocalDateTime.now());
        dto.setDeleteFlag(StatusConstant.DeleteFlag.un_delete.getCode());
        f15ShopProfitDao.insert(dto);
//        }else{
//            F15ShopProfitDO f15ShopProfitDO = f15ShopProfitDOS.get(0);
//            f15ShopProfitDO.setDayIncome(f15ShopProfitDO.getDayIncome().add(d01OrderDO.getOrderPrice()));
//            f15ShopProfitDO.setTotalIncome(f15ShopProfitDO.getTotalIncome().add(d01OrderDO.getOrderPrice()));
//            f15ShopProfitDao.updateById(f15ShopProfitDO);
//        }
        //增加商户账户金额
        F05ShopAccountDO shopAccountById = f05ShopAccountDao.getShopAccountById(d01OrderDO.getShopId());
        BigDecimal accountBalance = shopAccountById.getAccountBalance();//商户账户总金额
        BigDecimal bigDecimal =accountBalance.add(d01OrderDO.getOrderPrice());
        //BigDecimal bigDecimal = DecimalUtil.addBigMal(String.valueOf(accountBalance), String.valueOf(d01OrderDO.getOrderPrice()), 0, 0);//最新总金额
        if(d01OrderDO.getFlag() != 2) {
            shopAccountById.setAccountBalance(bigDecimal);
            shopAccountById.setAvailableBalance(bigDecimal);
        }
        if(d01OrderDO.getFlag() == 2){
            //面对面交易可直接提现
            BigDecimal money = DecimalUtil.addBigMal(String.valueOf(shopAccountById.getAvailableWithdrawMoney()), String.valueOf(bigDecimal), 0, 0);
            shopAccountById.setAvailableWithdrawMoney(money);
        }
        f05ShopAccountDao.updateById(shopAccountById);
        //增加商户账户流水
        F07ShopAccountFlowDO f07ShopAccountFlowDo = new F07ShopAccountFlowDO();
        f07ShopAccountFlowDo.setAccountId(shopAccountById.getId());
        f07ShopAccountFlowDo.setOriginalMoney(accountBalance);
        f07ShopAccountFlowDo.setMoney(d01OrderDO.getOrderPrice());
        f07ShopAccountFlowDo.setFinalMoney(bigDecimal);
        f07ShopAccountFlowDo.setSourceId(d01OrderDO.getId());
        f07ShopAccountFlowDo.setType(1);
        if(d01OrderDO.getFlag() == 2){
            f07ShopAccountFlowDo.setStatus(9);
        }else{
            f07ShopAccountFlowDo.setStatus(1);
        }
        f07ShopAccountFlowDo.setCreatePerson(String.valueOf(d01OrderDO.getUserId()));
        f07ShopAccountFlowDo.setUpdatePerson(d01OrderDO.getNick());
        f07ShopAccountFlowDo.setCreateTime(LocalDateTime.now());
        f07ShopAccountFlowDo.setDeleteFlag(1);
        if(d01OrderDO.getFlag() == 2){
            f07ShopAccountFlowDo.setIsFace(2);
        }
        f07ShopAccountFlowDao.insert(f07ShopAccountFlowDo);

        if(integer>0){
            //发送短信通知 尊敬的${name},您的${shop}审核未通过,请检查后重新提交申请!
           // 尊敬的用户${usserName}，您已获得权限，权限从现在开始到晚上零时结束，请您上街进店领取，祝您快乐！
            JSONObject json = new JSONObject();
            json.put("usserName",d01OrderDO.getNick());
            String information = sendSmsUtil.aliSendSmsTwo(d01OrderDO.getUserPhone(), json.toJSONString(), TypeConstant.ShortMessageType.confirmReceiveUnCode.getCode());
            if(information.equals("ok")){
                log.info(d01OrderDO.getUserPhone()+"：确认收货 短信已发送，请注意查收");
            }else{
                log.info(d01OrderDO.getUserPhone()+"：确认收货 短信发送失败");
            }
        }
        return integer;
    }
}
