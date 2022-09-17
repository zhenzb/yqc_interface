package com.youqiancheng.service.shop.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.handongkeji.config.exception.JsonException;
import com.handongkeji.config.mybatis.SecurityUtils;
import com.handongkeji.constants.StatusConstant;
import com.handongkeji.constants.TypeConstant;
import com.handongkeji.push.UmengPush;
import com.handongkeji.util.Constants;
import com.youqiancheng.form.shop.ShopOrderSendForm;
import com.youqiancheng.form.shop.otther.ShopOrderStatisticsForm;
import com.youqiancheng.mybatis.dao.*;
import com.youqiancheng.mybatis.model.*;
import com.youqiancheng.service.shop.ShopOrderService;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.result.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Mr.Deng
 * @Date: 2020/4/13 13:29
 * @Version: V1.0
 */
@Service
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private D01OrderDao orderDao;
    @Resource
    private D02OrderItemDao orderItemDao;
    @Resource
    private F15ShopProfitDao f15ShopProfitDao;
    @Autowired
    private A19ExpressDao a19ExpressDao;
    @Autowired
    private A15MessageDao a15MessageDao;
    @Resource
    private A17MessageUserDao a17MessageUserDao;
    @Resource
    private B01UserDao b01UserDao;
    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/13 13:32
     * @Param:
     * @return:
     * @Description: 订单统计
     */
    @Override
    public D01OrderDO orderStatistic(ShopOrderStatisticsForm shopOrderStatisticsForm) {
        if (shopOrderStatisticsForm == null){
            return null;
        }
        F08ShopUserDO shopUser = SecurityUtils.getShopUser();
        if (shopUser == null){
            throw new JsonException(Constants.$Null, "登录超时");
        }
        EntityWrapper<D01OrderDO> ew = new EntityWrapper<D01OrderDO>();
        ew.setSqlSelect("sum(order_price) as total_sales,count(*) as total_orders");
        ew.eq("shop_id",shopUser.getShopId());
        if (shopOrderStatisticsForm.getPayType() != 0){
            ew.eq("pay_type",shopOrderStatisticsForm.getPayType());
        }
        if (shopOrderStatisticsForm.getOrderStatus() != 0){
            ew.eq("order_status",shopOrderStatisticsForm.getOrderStatus());
        }
        if (shopOrderStatisticsForm.getDeliverystatus() != 0){
            ew.eq("delivery_status",shopOrderStatisticsForm.getDeliverystatus());
        }
        if (StringUtils.isNotBlank(shopOrderStatisticsForm.getStartTime())){
            ew.ge("pay_time",shopOrderStatisticsForm.getStartTime());
        }
        if (StringUtils.isNotBlank(shopOrderStatisticsForm.getEndTime())){
            ew.le("pay_time",shopOrderStatisticsForm.getEndTime());
        }
        List<D01OrderDO> list = orderDao.selectList(ew);
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
     * @Author: Mr.Deng
     * @Date: 2020/4/14 9:23
     * @Param:
     * @return:
     * @Description: 商家管理--订单管理--订单列表
     */
    @Override
    public List<D01OrderDO> listOrderHDPage(Map<String, Object> map) {
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        return orderDao.listOrderListHDPage(map);
    }

    @Override
    public Integer saveOrUpdateOrder(D01OrderDO order) {
        if (order == null){
            return 0;
        }
        if (order.getId() == null){//添加
            order.setCreateTime(LocalDateTime.now());
            return orderDao.insert(order);
        }
        //编辑
        return orderDao.updateById(order);
    }

    @Override
    public Integer batchUpdateOrder(List<D01OrderDO> orders) {
        if (CollectionUtils.isEmpty(orders)){
            return 0;
        }
         orderDao.updateList(orders);
        //级联修改订单明细为已发货
        List<D02OrderItemDO> list=new ArrayList<>();
        for (D01OrderDO order : orders) {
            //2:通过支付订单的id去查商家订单表.是一个集合
            //3:查询订单明细
            List<D02OrderItemDO> orderItmeByOrderId = orderItemDao.selectList(
                    new EntityWrapper<D02OrderItemDO>()
                            .eq("order_id",order.getId())
            );
            list.addAll(orderItmeByOrderId);
        }
        //修改订单明细
        for (D02OrderItemDO aDoi : list) {
            aDoi.setOrderStatus(StatusConstant.OrderStatus.send.getCode());
        }
        orderItemDao.updateList(list);
        return 1;
    }

    @Override
    public Integer batchRefundOrder( Long[] ids) {
        List<D02OrderItemDO> list=new ArrayList<>();
        BigDecimal money=new BigDecimal("0");
        Long shopId=0L;
        for (Long id : ids) {
            D02OrderItemDO d02OrderItemDO = orderItemDao.get(id);
            if(d02OrderItemDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合条件的订单");
            }
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refund.getCode());
            list.add(d02OrderItemDO);
            money=money.add(d02OrderItemDO.getTotalPrice());
            shopId=d02OrderItemDO.getShopId();
        }
        orderItemDao.updateList(list);
        //减少流水数据
        //计入收益
        List<F15ShopProfitDO> f15ShopProfitDOS = f15ShopProfitDao.selectList(
                new EntityWrapper<F15ShopProfitDO>()
                        .eq("shop_id",shopId)

        );
        //判断是否存在商家台账，不存在增加，存在修改
        if(CollectionUtils.isEmpty(f15ShopProfitDOS)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合条件的台账");
        }else{
            F15ShopProfitDO f15ShopProfitDO = f15ShopProfitDOS.get(0);
            f15ShopProfitDO.setDayIncome(f15ShopProfitDO.getDayIncome().subtract(money));
            f15ShopProfitDO.setTotalIncome(f15ShopProfitDO.getTotalIncome().subtract(money));
            f15ShopProfitDao.updateById(f15ShopProfitDO);
        }
        return 1;
    }

    @Override
    public Integer examineOrderPass(Long[] ids) {
        List<D02OrderItemDO> list=new ArrayList<>();
        for (Long id : ids) {
            D02OrderItemDO d02OrderItemDO = orderItemDao.get(id);
            if(d02OrderItemDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合条件的订单");
            }
            if(StatusConstant.OrderStatus.apply_refund.getCode()==d02OrderItemDO.getOrderStatus()){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合状态的订单");
            }

            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.pass.getCode());
            list.add(d02OrderItemDO);
        }
        orderItemDao.updateList(list);
        return 1;
    }

    @Override
    public Integer examineOrderRefuse(Long[] ids) {
        List<D02OrderItemDO> list=new ArrayList<>();
        for (Long id : ids) {
            D02OrderItemDO d02OrderItemDO = orderItemDao.get(id);
            if(d02OrderItemDO==null){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合条件的订单");
            }
            if(StatusConstant.OrderStatus.apply_refund.getCode()==d02OrderItemDO.getOrderStatus()){
                throw new JsonException(ResultEnum.DATA_NOT_EXIST,"未查询到符合状态的订单");
            }
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.refuse.getCode());
            list.add(d02OrderItemDO);
        }
        orderItemDao.updateList(list);
        return 1;
    }

    @Override
    public D01OrderDO getOrderById(long id) {
        return orderDao.selectById(id);
    }

    @Override
    public List<D02OrderItemDO> listOrderItemBy(EntityWrapper<D02OrderItemDO> ew) {
        return orderItemDao.selectList(ew);
    }

    @Override
    public int send(ShopOrderSendForm form) {
        D01OrderDO d01OrderDO = orderDao.get(form.getOrderId());
        if(d01OrderDO==null){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单信息不存在");
        }
        if(StatusConstant.OrderStatus.pay.getCode()!=d01OrderDO.getOrderStatus()){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单状态不符合");
        }
        d01OrderDO.setExpressNumber(form.getExpressNumber());
        d01OrderDO.setExpressName(form.getExpressName());
        d01OrderDO.setExpressCode(form.getExpressCode());
        d01OrderDO.setOrderStatus(StatusConstant.OrderStatus.send.getCode());
        d01OrderDO.setSendTime(LocalDateTime.now());
        //假如是标记是2,就是面对面付款拿货,所以就不存在物流信息
        d01OrderDO.setFlag(form.getFlag());

        orderDao.updateById(d01OrderDO);
        //修改订单明细状态
        QueryMap map1 =new QueryMap(StatusConstant.CreatFlag.delete.getCode());
        map1.put("orderId",d01OrderDO.getId());
        List<D02OrderItemDO> list = orderItemDao.list(map1);
        if(CollectionUtils.isEmpty(list)){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"订单明细不存在");
        }
        for (D02OrderItemDO d02OrderItemDO : list) {
            d02OrderItemDO.setOrderStatus(StatusConstant.OrderStatus.send.getCode());
        }
        orderItemDao.updateList(list);
        //根据商家订单创建消息体
        A15MessageDO messageDo=new A15MessageDO();
        messageDo.setContent("您的订单已经发货，订单："+d01OrderDO.getOrderNo());
        messageDo.setUpdateTime(LocalDateTime.now());
        messageDo.setCreateTime(LocalDateTime.now());
        messageDo.setUpdatePerson(d01OrderDO.getShopName());
        messageDo.setSendId(d01OrderDO.getShopId());
        messageDo.setCreatePerson(d01OrderDO.getShopName());
        messageDo.setType(StatusConstant.MessageType.service_msg.getCode());
        messageDo.setTitle("发货提醒");
        //messageDo.setContent("请尽快发货，订单："+d01OrderDO.getOrderNo());
        messageDo.setShopOrderId(d01OrderDO.getId());
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
        dto.setUserId(d01OrderDO.getUserId());
        Integer insert1 = a17MessageUserDao.insert(dto);
        if(insert1<=0){
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息发送失败");
        }
        try {
            String alias = "yqc_" + d01OrderDO.getId();
            String aliastype = "yqc_type";
            String content = messageDo.getContent();
            String title = messageDo.getTitle();
            UmengPush.sendIOSCustomizedcast(aliastype, alias, title,title, content, "1","1",null,null,null);  // IOS 单推
            UmengPush.sendAndroidCustomizedcast(aliastype, alias, title, title, content, "1","1",null,null,null); // android 单推
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new JsonException(ResultEnum.DATA_NOT_EXIST,"消息推送失败"+e.getMessage());
        }

        return 1;
    }
    //搜索快递公司名称以及编号
    @Override
    public List<A19ExpressDO> getCourierServicesCompanylist(Map<String, Object> map) {
        return a19ExpressDao.getCourierServicesCompanylist( map);
    }



}


