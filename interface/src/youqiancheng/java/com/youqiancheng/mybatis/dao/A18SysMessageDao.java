package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A18SysMessageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-06-01
 */
@Mapper
public interface A18SysMessageDao extends BaseMapper<A18SysMessageDO> {

     A18SysMessageDO get(Long id);


     List<A18SysMessageDO> listHDPage(Map<String, Object> map);

     List<A18SysMessageDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A18SysMessageDO> a18SysMessages);



     int updateList(List<A18SysMessageDO> a18SysMessages);

     int updateStatus(Map<String, Object> map);
}
