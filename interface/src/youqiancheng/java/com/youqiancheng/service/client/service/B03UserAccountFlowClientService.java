package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.B03UserAccountFlowDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface B03UserAccountFlowClientService {

     B03UserAccountFlowDO get(Long id);

     List<B03UserAccountFlowDO> listHDPage(Map<String, Object> map);

     List<B03UserAccountFlowDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(B03UserAccountFlowDO b03UserAccountFlow);

     int insertBatch(List<B03UserAccountFlowDO> b03UserAccountFlows);

     int update(B03UserAccountFlowDO b03UserAccountFlow);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
