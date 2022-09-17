package com.youqiancheng.service.app.service;


import com.youqiancheng.mybatis.model.F17PromotionIncomeDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-03-06
 */
public interface F17PromotionIncomeAppService {

     F17PromotionIncomeDO get(Long id);

     List<F17PromotionIncomeDO> listHDPage(Map<String, Object> map);

     List<F17PromotionIncomeDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(F17PromotionIncomeDO f17PromotionIncome);

     int insertBatch(List<F17PromotionIncomeDO> f17PromotionIncomes);

     int update(F17PromotionIncomeDO f17PromotionIncome);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
