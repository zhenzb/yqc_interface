package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.youqiancheng.mybatis.model.F18LinksDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2021-11-20
 */
@Mapper
public interface F18LinksDao extends BaseMapper<F18LinksDO> {

     F18LinksDO get(Long id);


     List<F18LinksDO> listHDPage(Map<String, Object> map);

     List<F18LinksDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F18LinksDO> f18Linkss);



     int updateList(List<F18LinksDO> f18Linkss);

     int updateStatus(Map<String, Object> map);
}
