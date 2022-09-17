package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.B06UserAddressDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
public interface B06UserAddressClientService {

     B06UserAddressDO get(Long id);

     List<B06UserAddressDO> listHDPage(Map<String, Object> map);

     List<B06UserAddressDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B06UserAddressDO b06UserAddress);

     int insertBatch(List<B06UserAddressDO> b06UserAddresss);

     int update(B06UserAddressDO b06UserAddress);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
