package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A12ServiceAgreementService {

     A12ServiceAgreementDO get(Long id);

     List<A12ServiceAgreementDO> listHDPage(Map<String, Object> map);

     List<A12ServiceAgreementDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A12ServiceAgreementDO a12ServiceAgreement);

     int insertBatch(List<A12ServiceAgreementDO> a12ServiceAgreements);

     int update(A12ServiceAgreementDO a12ServiceAgreement);

     int stop(List<Long> ids);

     int start(List<Long> ids);

}
