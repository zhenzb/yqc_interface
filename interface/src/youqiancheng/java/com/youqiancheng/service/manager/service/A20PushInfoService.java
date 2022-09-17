package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A20PushInfoDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-06-17
 */
public interface A20PushInfoService {

     A20PushInfoDO get(Long id);

     List<A20PushInfoDO> listHDPage(Map<String, Object> map);

     List<A20PushInfoDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A20PushInfoDO a20PushInfo);

     int insertBatch(List<A20PushInfoDO> a20PushInfos);

     int update(A20PushInfoDO a20PushInfo);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
