package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.E05ReceiveRecordUpdateForm;
import com.youqiancheng.form.client.ReceiveRecordSearchForm;
import com.youqiancheng.mybatis.model.E03RedenvelopesRuleDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.vo.client.ReceiveRecordNumVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-26
 */
public interface E05RedenvelopesReceiveRecordClientService {

     E05RedenvelopesReceiveRecordDO get(Long id);

     List<E05RedenvelopesReceiveRecordDO> listHDPage(Map<String, Object> map);

     List<E05RedenvelopesReceiveRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord);

     int insertBatch(List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecords);

     int update(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord);
     BigDecimal receiveRedPacket(E05ReceiveRecordUpdateForm form);
     E03RedenvelopesRuleDO getRedPacketUrl();

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
