package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A16SysDictDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A16SysDictService {

     A16SysDictDO get(Long id);

     List<A16SysDictDO> listHDPage(Map<String, Object> map);

     List<A16SysDictDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A16SysDictDO a16SysDict);

     int insertBatch(List<A16SysDictDO> a16SysDicts);

     int update(A16SysDictDO a16SysDict);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
