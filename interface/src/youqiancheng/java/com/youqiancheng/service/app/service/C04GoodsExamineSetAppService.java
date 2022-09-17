package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.C04GoodsExamineSetDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface C04GoodsExamineSetAppService {

     C04GoodsExamineSetDO get(Long id);


     List<C04GoodsExamineSetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C04GoodsExamineSetDO c04GoodsExamineSet);

     int insertBatch(List<C04GoodsExamineSetDO> c04GoodsExamineSets);

     int update(C04GoodsExamineSetDO c04GoodsExamineSet);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
