package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A14UserKnowDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A14UserKnowService {

     A14UserKnowDO get(Long id);

     List<A14UserKnowDO> listHDPage(Map<String, Object> map);

     List<A14UserKnowDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A14UserKnowDO a14UserKnow);

     int insertBatch(List<A14UserKnowDO> a14UserKnows);

     int update(A14UserKnowDO a14UserKnow);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
