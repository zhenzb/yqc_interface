package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A07HelpCenterDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A07HelpCenterDao  extends BaseMapper<A07HelpCenterDO> {

     A07HelpCenterDO get(Long id);


     List<A07HelpCenterDO> listHDPage(Map<String, Object> map);

     List<A07HelpCenterDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A07HelpCenterDO> a07HelpCenters);



     int updateList(List<A07HelpCenterDO> a07HelpCenters);

     int updateStatus(Map<String, Object> map);
}
