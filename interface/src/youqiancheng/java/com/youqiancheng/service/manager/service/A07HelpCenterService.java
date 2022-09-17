package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A07HelpCenterDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A07HelpCenterService {

     A07HelpCenterDO get(Long id);

     List<A07HelpCenterDO> listHDPage(Map<String, Object> map);

     List<A07HelpCenterDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A07HelpCenterDO a07HelpCenter);

     int insertBatch(List<A07HelpCenterDO> a07HelpCenters);

     int update(A07HelpCenterDO a07HelpCenter);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
