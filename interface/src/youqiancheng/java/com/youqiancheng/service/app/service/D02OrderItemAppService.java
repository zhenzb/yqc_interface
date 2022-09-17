package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D02OrderItemVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface D02OrderItemAppService {

     D02OrderItemDO get(Long id);

     List<D02OrderItemVO> listHDPage(Map<String, Object> map);
     List<D02OrderItemVO> list5HDPage(Map<String, Object> map);
     List<D01OrderStatusVo> getAppShopStatusOrderHDPage(Map<String, Object> map);
     List<D02OrderItemDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(D02OrderItemDO d02OrderItem);

     int insertBatch(List<D02OrderItemDO> d02OrderItems);

     int update(D02OrderItemDO d02OrderItem);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     D02OrderItemVO getOrderItmeAndOrder(Long id);

}
