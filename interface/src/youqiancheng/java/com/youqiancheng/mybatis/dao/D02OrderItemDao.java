package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.vo.app.D01OrderStatusVo;
import com.youqiancheng.vo.app.D02OrderItemVO;
import com.youqiancheng.vo.client.D02OrderItemClientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface D02OrderItemDao  extends BaseMapper<D02OrderItemDO> {

     D02OrderItemDO get(Long id);

     List<D02OrderItemDO> list(Map<String, Object> map);

     List<D02OrderItemDO> listHDPage(Map<String, Object> map);

     List<D02OrderItemVO> listAppHDPage(Map<String, Object> map);

     List<D02OrderItemVO> listApp5HDPage(Map<String, Object> map);

     List<D01OrderStatusVo> getAppShopStatusOrderHDPage(Map<String, Object> map);

     List<D02OrderItemClientVO> listClientHDPage(Map<String, Object> map);

     List<D02OrderItemClientVO> listClient5tHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insertBatch(List<D02OrderItemDO> d02OrderItems);

     int updateList(List<D02OrderItemDO> d02OrderItems);

     int updateStatus(Map<String, Object> map);

     List<D02OrderItemDO> getOrderItmeByOrderId(Long id);

     List<D02OrderItemDO> getD02OrderItemDaoById(Long ShopId);

     D02OrderItemVO getOrderItmeAndOrder(Long id);


}





