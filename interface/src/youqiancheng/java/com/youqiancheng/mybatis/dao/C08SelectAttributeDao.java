package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C08SelectAttributeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface C08SelectAttributeDao extends BaseMapper<C08SelectAttributeDO> {
     List<C08SelectAttributeDO> listSelectAttributeHDPage(Map<String, Object> map);

     C08SelectAttributeDO get(Long id);


     List<C08SelectAttributeDO> listHDPage(Map<String, Object> map);

     List<C08SelectAttributeDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C08SelectAttributeDO> c08SelectAttributes);



     int updateList(List<C08SelectAttributeDO> c08SelectAttributes);

     int updateStatus(Map<String, Object> map);
}
