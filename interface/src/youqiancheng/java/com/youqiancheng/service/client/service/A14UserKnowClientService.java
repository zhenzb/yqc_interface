package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.A14UserKnowDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface A14UserKnowClientService {

     List<A14UserKnowDO> list(Map<String, Object> map);

}
