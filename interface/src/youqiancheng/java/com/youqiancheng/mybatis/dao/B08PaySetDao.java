package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B08PaySetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B08PaySetDao  extends BaseMapper<B08PaySetDO>{

     B08PaySetDO get(Long id);

     List<B08PaySetDO> list(Map<String, Object> map);
     List<B08PaySetDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B08PaySetDO> b08PaySets);



     int updateList(List<B08PaySetDO> b08PaySets);

     int updateStatus(Map<String, Object> map);
}
