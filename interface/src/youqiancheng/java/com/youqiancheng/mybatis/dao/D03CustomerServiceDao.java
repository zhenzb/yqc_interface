package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.vo.client.D03CustomerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface D03CustomerServiceDao  extends BaseMapper<D03CustomerServiceDO>{

     D03CustomerServiceDO get(Long id);

     List<D03CustomerServiceDO> list(Map<String, Object> map);
     List<D03CustomerServiceDO> listHDPage(Map<String, Object> map);
     List<D03CustomerServiceDO> listByManageHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<D03CustomerServiceDO> d03CustomerServices);



     int updateList(List<D03CustomerServiceDO> d03CustomerServices);

     int updateStatus(Map<String, Object> map);

     List<D03CustomerVo> getCustomerGroupByTime(Map<String, Object> map);
}
