package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E03RedenvelopesRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface E03RedenvelopesRuleDao  extends BaseMapper<E03RedenvelopesRuleDO>{

     E03RedenvelopesRuleDO get(Long id);

     List<E03RedenvelopesRuleDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E03RedenvelopesRuleDO> e03RedenvelopesRules);



     int updateList(List<E03RedenvelopesRuleDO> e03RedenvelopesRules);

     int updateStatus(Map<String, Object> map);

}
