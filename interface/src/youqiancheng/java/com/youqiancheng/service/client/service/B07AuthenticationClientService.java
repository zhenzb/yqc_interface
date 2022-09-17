package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.B07AuthenticationDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
public interface B07AuthenticationClientService {

     B07AuthenticationDO get(Long id);

     List<B07AuthenticationDO> listHDPage(Map<String, Object> map);

     List<B07AuthenticationDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B07AuthenticationDO b07Authentication);

     int insertBatch(List<B07AuthenticationDO> b07Authentications);

     int update(B07AuthenticationDO b07Authentication);

     int stop(List<Long> ids);

     int start(List<Long> ids);


}
