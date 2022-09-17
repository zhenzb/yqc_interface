package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.D06PayOrderSearchForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.vo.client.*;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface D01OrderClientService {

     D01OrderClientVO get(Long id);
     D06PayOrderDO getPayOrderById(Long id);
     int updatePayOrderById(D06PayOrderDO dto);
     int deleteByOrderId(Long id);


     List<D01OrderDO> listHDPage(Map<String, Object> map);
     List<NewOrderVO> orderList(Map<String, Object> map);
     List<D01OrderVO> list(Map<String, Object> map);

     List<D06PayOrderDO> listPayOrder(Map<String, Object> map);

     int count(Map<String, Object> map);

     D06PayOrderDO save(D06PayOrderSearchForm form);

     D06PayOrderDO saveFacePay(D06PayOrderSearchForm form);

     int insertBatch(List<D01OrderDO> d01Orders);

     int update(D01OrderDO d01Order);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     int send(List<Long> ids);
     D06PayOrderDO getPayOrderWithItemById(Long id);
     int cancelOrderByTime(Long id);
     int remindShipping(Long id);

     //删除商家订单
     int deleteShopOrderByShopIdAndOrderId(Long id);
     // 获取商家余额,收益信息
     Map<String, Object> getShopIncome(Long shopid);
     List<D01OrderDOVo>  getOrderByShopId(Map<String,Object> map);

     List<D06OrderStatusNumVO> getOrderNumberByUserId(Long userId);
}
