package com.youqiancheng.service.manager.service;

import com.youqiancheng.mybatis.model.A10CarouselPicDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
public interface A10CarouselPicService {

     A10CarouselPicDO get(Long id);

     List<A10CarouselPicDO> listHDPage(Map<String, Object> map);

     List<A10CarouselPicDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);

     int insert(A10CarouselPicDO a10CarouselPic);

     int insertBatch(List<A10CarouselPicDO> a10CarouselPics);

     int update(A10CarouselPicDO a10CarouselPic);

     int stop(List<Long> ids);
     int delete(List<Long> ids);

     int start(List<Long> ids);

}
