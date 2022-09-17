package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F10WithdrawalRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface F10WithdrawalRuleDao  extends BaseMapper<F10WithdrawalRuleDO>{

     F10WithdrawalRuleDO get(Long id);
     BigDecimal getServiceRatio(BigDecimal money);

     List<F10WithdrawalRuleDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F10WithdrawalRuleDO> f10WithdrawalRules);



     int updateList(List<F10WithdrawalRuleDO> f10WithdrawalRules);

     int updateStatus(Map<String, Object> map);
}
