package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C06AttributeProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C06AttributeProjectDao  extends BaseMapper<C06AttributeProjectDO>{
     List<C06AttributeProjectDO> listAttributeProjectHDPage(Map<String, Object> map);

     C06AttributeProjectDO get(Long id);

     List<C06AttributeProjectDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C06AttributeProjectDO> c06AttributeProjects);



     int updateList(List<C06AttributeProjectDO> c06AttributeProjects);

     int updateStatus(Map<String, Object> map);
}
