package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.C12RewardRecordDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface C12RewardRecordClientService {

     C12RewardRecordDO get(Long id);

     List<C12RewardRecordDO> listHDPage(Map<String, Object> map);

     List<C12RewardRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C12RewardRecordDO c12RewardRecord);

     int insertBatch(List<C12RewardRecordDO> c12RewardRecords);

     int update(C12RewardRecordDO c12RewardRecord);
     int updateStatus(Long id,String no,int type);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
