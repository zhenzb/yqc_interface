package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F03MainProjectDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface F03MainProjectDao  extends BaseMapper<F03MainProjectDO>{

     F03MainProjectDO get(Long id);

     List<F03MainProjectDO> list(Map<String, Object> map);
     List<F03MainProjectDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F03MainProjectDO> f03MainProjects);



     int updateList(List<F03MainProjectDO> f03MainProjects);

     int updateStatus(Map<String, Object> map);

    List<F03MainProjectDO> listMainProjectHDPage(Map<String, Object> map);
}
