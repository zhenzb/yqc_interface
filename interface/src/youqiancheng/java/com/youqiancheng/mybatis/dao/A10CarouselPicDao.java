package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A10CarouselPicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A10CarouselPicDao  extends BaseMapper<A10CarouselPicDO> {

     A10CarouselPicDO get(Long id);


     List<A10CarouselPicDO> listHDPage(Map<String, Object> map);

     List<A10CarouselPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A10CarouselPicDO> a10CarouselPics);



     int updateList(List<A10CarouselPicDO> a10CarouselPics);

     int updateStatus(Map<String, Object> map);
}
