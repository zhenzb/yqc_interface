package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C12RewardRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Mapper
public interface C12RewardRecordDao  extends BaseMapper<C12RewardRecordDO> {

     C12RewardRecordDO get(Long id);


     List<C12RewardRecordDO> listHDPage(Map<String, Object> map);

     List<C12RewardRecordDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C12RewardRecordDO> c12RewardRecords);



     int updateList(List<C12RewardRecordDO> c12RewardRecords);

     int updateStatus(Map<String, Object> map);
}
