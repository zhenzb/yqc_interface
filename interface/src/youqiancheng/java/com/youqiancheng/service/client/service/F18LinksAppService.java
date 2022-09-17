package com.youqiancheng.service.client.service;



import com.youqiancheng.mybatis.model.F18LinksDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-11-20
 */
public interface F18LinksAppService {

     F18LinksDO get(Long id);

     List<F18LinksDO> listHDPage(Map<String, Object> map);

     List<F18LinksDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(F18LinksDO f18Links);

     int insertBatch(List<F18LinksDO> f18Linkss);

     int update(F18LinksDO f18Links);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     int delete(Long id);

}
