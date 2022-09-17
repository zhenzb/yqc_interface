package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A06BaseInfoDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A06BaseInfoService {

     A06BaseInfoDO get(Long id);

     List<A06BaseInfoDO> listHDPage(Map<String, Object> map);

     List<A06BaseInfoDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A06BaseInfoDO a06BaseInfo);

     int insertBatch(List<A06BaseInfoDO> a06BaseInfos);

     int update(A06BaseInfoDO a06BaseInfo);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
