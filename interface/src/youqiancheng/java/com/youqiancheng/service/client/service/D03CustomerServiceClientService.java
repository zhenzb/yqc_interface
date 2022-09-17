package com.youqiancheng.service.client.service;

import com.youqiancheng.form.client.D03CustomerServiceSaveForm;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.vo.client.D03CustomerVo;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
public interface D03CustomerServiceClientService {

     D03CustomerServiceDO get(Long id);
     D03CustomerServiceDO getCustomerServiceByOrderId(Long id);
     List<D03CustomerServiceDO> listHDPage(Map<String, Object> map);

     List<D03CustomerServiceDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int save(D03CustomerServiceSaveForm d03CustomerService);

     int insertBatch(List<D03CustomerServiceDO> d03CustomerServices);

     int update(D03CustomerServiceDO d03CustomerService);

     int stop(List<Long> ids);

     int start(List<Long> ids);

     List<D03CustomerVo> getCustomerGroupByTime(Map<String, Object> map);
}
