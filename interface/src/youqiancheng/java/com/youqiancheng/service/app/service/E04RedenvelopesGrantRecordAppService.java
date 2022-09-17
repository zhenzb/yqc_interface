package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.E04GrantRecordSearchForm;
import com.youqiancheng.mybatis.model.E04RedenvelopesGrantRecordDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.mybatis.model.F01ShopDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface E04RedenvelopesGrantRecordAppService {

     E04RedenvelopesGrantRecordDO get(Long id);

     List<E04RedenvelopesGrantRecordDO> listHDPage(Map<String, Object> map);
     List<F01ShopDO> getShopListByRedEnvelopes(Map<String, Object> map);

     List<E04RedenvelopesGrantRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord);

     int insertBatch(List<E04RedenvelopesGrantRecordDO> e04RedenvelopesGrantRecords);

     int update(E04RedenvelopesGrantRecordDO e04RedenvelopesGrantRecord);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     List<E05RedenvelopesReceiveRecordDO> getShopRedPacket(Map<String, Object> map);

    Long insert (E04GrantRecordSearchForm form);

}
