package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A12ServiceAgreementDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A12ServiceAgreementDao  extends BaseMapper<A12ServiceAgreementDO> {

     A12ServiceAgreementDO get(Long id);


     List<A12ServiceAgreementDO> listHDPage(Map<String, Object> map);

     List<A12ServiceAgreementDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A12ServiceAgreementDO> a12ServiceAgreements);



     int updateList(List<A12ServiceAgreementDO> a12ServiceAgreements);

     int updateStatus(Map<String, Object> map);
}
