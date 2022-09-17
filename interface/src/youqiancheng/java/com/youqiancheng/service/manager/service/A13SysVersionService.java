package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A13SysVersionDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A13SysVersionService {

     A13SysVersionDO get(Long id);

     List<A13SysVersionDO> listHDPage(Map<String, Object> map);

     List<A13SysVersionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A13SysVersionDO a13SysVersion);

     int insertBatch(List<A13SysVersionDO> a13SysVersions);

     int update(A13SysVersionDO a13SysVersion);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
