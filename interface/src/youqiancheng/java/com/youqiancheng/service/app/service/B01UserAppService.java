package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.B01UserDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
public interface B01UserAppService {

     B01UserDO get(Long id);

     List<B01UserDO> listHDPage(Map<String, Object> map);

     List<B01UserDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B01UserDO b01User);

     int insertBatch(List<B01UserDO> b01Users);

     int update(B01UserDO b01User);

     int stop(List<Long> ids);

     int start(List<Long> ids);


}
