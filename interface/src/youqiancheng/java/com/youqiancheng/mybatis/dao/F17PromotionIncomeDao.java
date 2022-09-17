package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F17PromotionIncomeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-03-06
 */
@Mapper
public interface F17PromotionIncomeDao extends BaseMapper<F17PromotionIncomeDO> {

     F17PromotionIncomeDO get(Long id);


     List<F17PromotionIncomeDO> listHDPage(Map<String, Object> map);
     List<F17PromotionIncomeDO> listHDPageV2(Map<String, Object> map);

     List<F17PromotionIncomeDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F17PromotionIncomeDO> f17PromotionIncomes);



     int updateList(List<F17PromotionIncomeDO> f17PromotionIncomes);

     int updateStatus(Map<String, Object> map);
}
