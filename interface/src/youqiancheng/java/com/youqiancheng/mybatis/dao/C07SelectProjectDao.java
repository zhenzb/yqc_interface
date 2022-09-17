package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C07SelectProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C07SelectProjectDao  extends BaseMapper<C07SelectProjectDO>{
     List<C07SelectProjectDO> listSelectProjectHDPage(Map<String, Object> map);

     C07SelectProjectDO get(Long id);

     List<C07SelectProjectDO> list(Map<String, Object> map);
     List<C07SelectProjectDO> listWithContent(Map<String, Object> map);
     List<C07SelectProjectDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C07SelectProjectDO> c07SelectProjects);



     int updateList(List<C07SelectProjectDO> c07SelectProjects);

     int updateStatus(Map<String, Object> map);
}
