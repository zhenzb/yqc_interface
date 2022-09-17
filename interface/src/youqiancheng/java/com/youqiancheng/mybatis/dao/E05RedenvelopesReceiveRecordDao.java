package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E05RedenvelopesReceiveRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface E05RedenvelopesReceiveRecordDao  extends BaseMapper<E05RedenvelopesReceiveRecordDO>{

     E05RedenvelopesReceiveRecordDO get(Long id);

     List<E05RedenvelopesReceiveRecordDO> list(Map<String, Object> map);
     List<E05RedenvelopesReceiveRecordDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecords);



     int updateList(List<E05RedenvelopesReceiveRecordDO> e05RedenvelopesReceiveRecords);

     int updateStatus(Map<String, Object> map);
}
