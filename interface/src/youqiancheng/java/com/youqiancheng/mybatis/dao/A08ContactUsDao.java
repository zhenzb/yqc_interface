package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A08ContactUsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A08ContactUsDao  extends BaseMapper<A08ContactUsDO> {

     A08ContactUsDO get(Long id);


     List<A08ContactUsDO> listHDPage(Map<String, Object> map);

     List<A08ContactUsDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A08ContactUsDO> a08ContactUss);



     int updateList(List<A08ContactUsDO> a08ContactUss);

     int updateStatus(Map<String, Object> map);
}
