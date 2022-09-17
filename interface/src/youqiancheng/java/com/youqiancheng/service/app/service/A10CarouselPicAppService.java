package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.A10CarouselPicDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface A10CarouselPicAppService {

     List<A10CarouselPicDO> list(Map<String, Object> map);
}
