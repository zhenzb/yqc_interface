package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F09WithdrawalSetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface F09WithdrawalSetDao  extends BaseMapper<F09WithdrawalSetDO>{

     F09WithdrawalSetDO get(Long id);

     List<F09WithdrawalSetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F09WithdrawalSetDO> f09WithdrawalSets);



     int updateList(List<F09WithdrawalSetDO> f09WithdrawalSets);

     int updateStatus(Map<String, Object> map);
}
