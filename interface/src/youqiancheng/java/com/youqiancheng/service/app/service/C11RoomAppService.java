package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.C11RoomDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
public interface C11RoomAppService {

     C11RoomDO get(Long id);

     List<C11RoomDO> listHDPage(Map<String, Object> map);

     List<C11RoomDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(C11RoomDO c11Room);

     int insertBatch(List<C11RoomDO> c11Rooms);

     int update(C11RoomDO c11Room);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
