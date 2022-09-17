package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C03CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C03CategoryDao  extends BaseMapper<C03CategoryDO>{

    // C03CategoryDO get(Long id);

     List<C03CategoryDO> list(Map<String, Object> map);
     List<C03CategoryDO> listFirst();

     int count(Map<String, Object> map);



     int insertBatch(List<C03CategoryDO> c03Categorys);



     int updateList(List<C03CategoryDO> c03Categorys);

     int updateStatus(Map<String, Object> map);

    C03CategoryDO findC03CategoryDOByName(String name);

     List<C03CategoryDO> listHDPage(Map<String, Object> map);
}
