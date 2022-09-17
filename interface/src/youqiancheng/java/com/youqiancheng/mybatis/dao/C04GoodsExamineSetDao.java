package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C04GoodsExamineSetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C04GoodsExamineSetDao  extends BaseMapper<C04GoodsExamineSetDO>{

     C04GoodsExamineSetDO get(Long id);

     List<C04GoodsExamineSetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C04GoodsExamineSetDO> c04GoodsExamineSets);



     int updateList(List<C04GoodsExamineSetDO> c04GoodsExamineSets);

     int updateStatus(Map<String, Object> map);
}
