package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A11AdvertisementPicDao  extends BaseMapper<A11AdvertisementPicDO> {

     A11AdvertisementPicDO get(Long id);


     List<A11AdvertisementPicDO> listHDPage(Map<String, Object> map);

     List<A11AdvertisementPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A11AdvertisementPicDO> a11AdvertisementPics);



     int updateList(List<A11AdvertisementPicDO> a11AdvertisementPics);

     int updateStatus(Map<String, Object> map);
}
