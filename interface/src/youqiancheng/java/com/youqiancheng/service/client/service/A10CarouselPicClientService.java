package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.A10CarouselPicDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface A10CarouselPicClientService {

     List<A10CarouselPicDO> list(Map<String, Object> map);
}
