package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import com.youqiancheng.vo.client.D06OrderStatusNumVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-05-12
 */
@Mapper
public interface D06PayOrderDao extends BaseMapper<D06PayOrderDO> {

     D06PayOrderDO get(Long id);
     List<D06PayOrderDO> listHDPage(Map<String, Object> map);
     List<D06PayOrderDO> list(Map<String, Object> map);
     int count(Map<String, Object> map);

     int insertBatch(List<D06PayOrderDO> d06PayOrders);

     int updateList(List<D06PayOrderDO> d06PayOrders);
     int updateStatus(Map<String, Object> map);

     List<D06OrderStatusNumVO> getOrderNumberByUserId(Long userId);


}
