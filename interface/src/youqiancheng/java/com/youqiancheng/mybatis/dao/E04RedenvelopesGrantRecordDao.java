package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Mapper
public interface E04RedenvelopesGrantRecordDao extends BaseMapper<E04RedenvelopesGrantRecordDO> {

     E04RedenvelopesGrantRecordDO get(Long id);

     BigDecimal getGrantMoney(Map<String, Object> map);
     List<E04RedenvelopesGrantRecordDO> listHDPage(Map<String, Object> map);

     BigDecimal TotalAmtHDPage(Map<String, Object> map);

     List<F01ShopDO> getShopListByRedEnvelopesHDPage(Map<String, Object> map);

     List<E04RedenvelopesGrantRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecords);



     int updateList(List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecords);

     int updateStatus(Map<String, Object> map);
     List <E05RedenvelopesReceiveRecordDO> getShopRedPacket(Map<String, Object> map);
}
