package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A14UserKnowDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A14UserKnowDao  extends BaseMapper<A14UserKnowDO> {

     A14UserKnowDO get(Long id);


     List<A14UserKnowDO> listHDPage(Map<String, Object> map);

     List<A14UserKnowDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A14UserKnowDO> a14UserKnows);



     int updateList(List<A14UserKnowDO> a14UserKnows);

     int updateStatus(Map<String, Object> map);
}
