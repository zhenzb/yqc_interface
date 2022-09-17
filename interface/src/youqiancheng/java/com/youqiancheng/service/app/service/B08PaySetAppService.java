package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.B08PaySetDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface B08PaySetAppService {

     B08PaySetDO get(Long id);

     List<B08PaySetDO> listHDPage(Map<String, Object> map);

     List<B08PaySetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B08PaySetDO b08PaySet);

     int insertBatch(List<B08PaySetDO> b08PaySets);

     int update(B08PaySetDO b08PaySet);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
