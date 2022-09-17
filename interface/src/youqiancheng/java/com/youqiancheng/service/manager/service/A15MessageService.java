package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A15MessageDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
public interface A15MessageService {

     A15MessageDO get(Long id);

     List<A15MessageDO> listHDPage(Map<String, Object> map);

     List<A15MessageDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A15MessageDO a15Message);
     int release(List<Long> ids);

     int insertBatch(List<A15MessageDO> a15Messages);

     int update(A15MessageDO a15Message);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
