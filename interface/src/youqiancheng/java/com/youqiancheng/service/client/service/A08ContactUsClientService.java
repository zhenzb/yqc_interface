package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.A08ContactUsDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface A08ContactUsClientService {

      List<A08ContactUsDO> list(Map<String, Object> map);
      int  addBrowseVolume();
      int  getBrowseVolume();



}
