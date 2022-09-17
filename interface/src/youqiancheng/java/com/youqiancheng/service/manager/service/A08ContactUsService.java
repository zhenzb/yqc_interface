package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A08ContactUsDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A08ContactUsService {

     A08ContactUsDO get(Long id);

     List<A08ContactUsDO> listHDPage(Map<String, Object> map);

     List<A08ContactUsDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A08ContactUsDO a08ContactUs);

     int insertBatch(List<A08ContactUsDO> a08ContactUss);

     int update(A08ContactUsDO a08ContactUs);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
