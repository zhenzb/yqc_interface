package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface F04ShopExamineSetClientService {

     F04ShopExamineSetDO get(Long id);

     List<F04ShopExamineSetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(F04ShopExamineSetDO f04ShopExamineSet);

     int insertBatch(List<F04ShopExamineSetDO> f04ShopExamineSets);

     int update(F04ShopExamineSetDO f04ShopExamineSet);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
