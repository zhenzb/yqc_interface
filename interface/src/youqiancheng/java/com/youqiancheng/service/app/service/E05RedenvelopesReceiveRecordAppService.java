package com.youqiancheng.service.app.service;

import com.youqiancheng.form.app.E05ReceiveRecordUpdateForm;
import com.youqiancheng.form.app.ReceiveRecordSearchForm;
import com.youqiancheng.mybatis.model.E03RedenvelopesRuleDO;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import com.youqiancheng.vo.app.ReceiveRecordNumVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-26
 */
public interface E05RedenvelopesReceiveRecordAppService {

     E05RedenvelopesReceiveRecordDO get(Long id);

     List<E05RedenvelopesReceiveRecordDO> listHDPage(Map<String, Object> map);

     List<E05RedenvelopesReceiveRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord);

     int insertBatch(List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecords);

     int update(E05RedenvelopesReceiveRecordDO e05RedenvelopesReceiveRecord);
     int receiveRedPacket(E05ReceiveRecordUpdateForm form);

     int stop(List<Long> ids);

     int start(List<Long> ids);
     //ReceiveRecordNumVO getNum( ReceiveRecordSearchForm form);

     E03RedenvelopesRuleDO getRedPacketUrl();

}
