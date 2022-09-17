package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C02GoodsPicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface C02GoodsPicDao  extends BaseMapper<C02GoodsPicDO>{
     List<C02GoodsPicDO> listGoodsPicHDPage(Map<String, Object> map);

     C02GoodsPicDO get(Long id);

     List<C02GoodsPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C02GoodsPicDO> c02GoodsPics);



     int updateList(List<C02GoodsPicDO> c02GoodsPics);

     int updateStatus(Map<String, Object> map);
}
