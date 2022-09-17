package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.form.app.D01OrderStatusrForm;
import com.youqiancheng.mybatis.model.D01OrderDO;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.util.QueryMap;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.client.D01OrderDOVo;
import com.youqiancheng.vo.client.ShopOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface D01OrderDao  extends BaseMapper<D01OrderDO>{

    List<D01OrderDO> listOrderHDPage(Map<String, Object> map);

     D01OrderDO get(Long id);


     List<D01OrderDO> list(Map<String, Object> map);
     List<D01OrderDO> listHDPage(Map<String, Object> map);
     List<D01OrderDOVo> listByShopIdHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<D01OrderDO> d01Orders);



     int updateList(List<D01OrderDO> d01Orders);

     int updateStatus(Map<String, Object> map);

    Long selectForDay();

     Long selectForWeek();

    Long selectForMonth();

    Long selectSaleForDay();

    Long selectSaleForWeek();

    Long selectSaleForMonth();

    List<D01OrderDO> getOrderHDPage(QueryMap map);

    List<D01OrderDO> listOrderListHDPage(Map<String, Object> map);


    //根据订单id先去查询一下订单
    D01OrderDO getOrderByShopIdAndOrderId( @Param("id") Long id);
    int deleteOrede(Long id);
    //根据条件查询,余额,店铺今日收益,昨日收益
    BigDecimal getTodayShopincome(Map<String, Object> map);
    //根据商家的id查询订单,因为商家有多个订单,所以在这用集合
     List<D01OrderDO> getOrderByShopId(Long id);
    int getAppTodayShopOrderCountByShopId(Long shopId);
    //查订单状态的各个个数
    List<D01OrderDO> getOrderStatusCountById( Long shopId);
    //查商家的各个支付状态下的订单信息
    List<D01OrderStatusVo> getAppShopStatusOrder(@Param("d01OrderStatusrForm") D01OrderStatusrForm d01OrderStatusrForm);
    //通过支付订单的id来获取订单表里的订单
    List<D01OrderDO> getOrderByPayOrderId(Long id);




}
