package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.B11InvitationRecordDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-12-08
 */
public interface B11InvitationRecordAppService {

     B11InvitationRecordDO get(Long id);

     List<B11InvitationRecordDO> listHDPage(Map<String, Object> map);

     List<B11InvitationRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B11InvitationRecordDO b11InvitationRecord);

     int insertBatch(List<B11InvitationRecordDO> b11InvitationRecords);

     int update(B11InvitationRecordDO b11InvitationRecord);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
