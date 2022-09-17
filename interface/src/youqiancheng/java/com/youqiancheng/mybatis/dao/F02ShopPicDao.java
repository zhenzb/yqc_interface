package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F02ShopPicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface F02ShopPicDao  extends BaseMapper<F02ShopPicDO>{

     F02ShopPicDO get(Long id);


     List<F02ShopPicDO> listHDPage(Map<String, Object> map);

     List<F02ShopPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F02ShopPicDO> f02ShopPics);



     int updateList(List<F02ShopPicDO> f02ShopPics);

     int updateStatus(Map<String, Object> map);
}
