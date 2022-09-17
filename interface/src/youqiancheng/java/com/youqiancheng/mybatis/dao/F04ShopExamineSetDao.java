package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F04ShopExamineSetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface F04ShopExamineSetDao  extends BaseMapper<F04ShopExamineSetDO>{

     F04ShopExamineSetDO get(Long id);

     List<F04ShopExamineSetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F04ShopExamineSetDO> f04ShopExamineSets);



     int updateList(List<F04ShopExamineSetDO> f04ShopExamineSets);

     int updateStatus(Map<String, Object> map);
}
