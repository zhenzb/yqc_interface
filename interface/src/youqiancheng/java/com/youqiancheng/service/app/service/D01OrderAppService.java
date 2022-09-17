package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.D01OrderStatusrForm;
import com.youqiancheng.form.app.D06PayOrderSearchForm;
import com.youqiancheng.form.app.D06PayOrderUpdateInfoForm;
import com.youqiancheng.form.app.PayOrderPayForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D01OrderVO;
import com.youqiancheng.vo.app.OrderVO;
import com.youqiancheng.vo.client.D01OrderDOVo;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface D01OrderAppService {

     D01OrderVO get(Long id);
     D06PayOrderDO getPayOrderById(Long id);
     D06PayOrderDO getPayOrderWithItemById(Long id);
     int updatePayOrderById(D06PayOrderDO dto);

     List<D01OrderDO> listHDPage(Map<String, Object> map);
     List<OrderVO> orderList(Map<String, Object> map);

     List<D01OrderVO> list(Map<String, Object> map);

     List<D06PayOrderDO> listPayOrder(Map<String, Object> map);

     int count(Map<String, Object> map);

     D06PayOrderDO save(D06PayOrderSearchForm form);

     int payOrder(PayOrderPayForm form);
     int remindShipping(Long id);

     int insertBatch(List<D01OrderDO> d01Orders);

     int update(D01OrderDO d01Order);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     int updateInfoByOrderId( D06PayOrderUpdateInfoForm form);

     int cancelOrderByTime(Long id);

     Map<String, Object>  getAppShopIncome(Long id);
     //通过商家id获取订单的信息===>信息有订单编号,发货状态,图片,商品名称,规格,价格,数量,总价
     List<D01OrderDOVo> getAppOrderByShopId(Map<String,Object> map);
     //查今天商家的订单个数
     int  getAppTodayShopOrderCountByShopId(Long ShopId);
     //查商家的订单各个状态的个数
     Map<String, Object> getOrderStatusCountById(Long ShopId);
     //查商家的各个支付状态下的订单信息
     List<D01OrderStatusVo> getAppShopStatusOrder1(D01OrderStatusrForm d01OrderStatusrForm);
     List<D01OrderStatusVo> getAppShopStatusOrder(D01OrderStatusrForm d01OrderStatusrForm);
     //查用户的订单各个状态的个数
     Map<String, Object> getUserOrderStatusCountByUserId(Map<String,Object> map);




}
