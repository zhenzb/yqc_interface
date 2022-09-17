package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B10ShareDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B10ShareDao  extends BaseMapper<B10ShareDO>{

     B10ShareDO get(Long id);

     List<B10ShareDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B10ShareDO> b10Shares);



     int updateList(List<B10ShareDO> b10Shares);

     int updateStatus(Map<String, Object> map);
}
