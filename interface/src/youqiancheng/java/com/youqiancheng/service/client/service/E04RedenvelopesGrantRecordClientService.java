package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.E04RedenvelopesGrantRecordSaveForm;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface E04RedenvelopesGrantRecordClientService {

     E04RedenvelopesGrantRecordDO get(Long id);

     BigDecimal TotalAmtHDPage(Map<String, Object> map);

     List<E04RedenvelopesGrantRecordDO> listHDPage(Map<String, Object> map);
     List<F01ShopDO> getShopListByRedEnvelopes(Map<String, Object> map);

     List<E04RedenvelopesGrantRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     long insert(E04RedenvelopesGrantRecordSaveForm form );

     int insertBatch(List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecords);

     int update(E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     List<E05RedenvelopesReceiveRecordDO> getShopRedPacket(Map<String, Object> map);

     int updateStatus(Long id,String no,int type);
}
