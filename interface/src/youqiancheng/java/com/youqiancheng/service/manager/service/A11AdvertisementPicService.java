package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A11AdvertisementPicDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A11AdvertisementPicService {

     A11AdvertisementPicDO get(Long id);

     List<A11AdvertisementPicDO> listHDPage(Map<String, Object> map);

     List<A11AdvertisementPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A11AdvertisementPicDO a11AdvertisementPic);

     int insertBatch(List<A11AdvertisementPicDO> a11AdvertisementPics);

     int update(A11AdvertisementPicDO a11AdvertisementPic);

     int stop(List<Long> ids);
     int delete(List<Long> ids);

     int start(List<Long> ids);

}
