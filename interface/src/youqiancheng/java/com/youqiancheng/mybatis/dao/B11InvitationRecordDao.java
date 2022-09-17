package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B11InvitationRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-12-08
 */
@Mapper
public interface B11InvitationRecordDao extends BaseMapper<B11InvitationRecordDO> {

     B11InvitationRecordDO get(Long id);


     List<B11InvitationRecordDO> listHDPage(Map<String, Object> map);

     List<B11InvitationRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B11InvitationRecordDO> b11InvitationRecords);



     int updateList(List<B11InvitationRecordDO> b11InvitationRecords);

     int updateStatus(Map<String, Object> map);
}
