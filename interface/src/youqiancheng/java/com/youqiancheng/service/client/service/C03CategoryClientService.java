package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.C03CategoryDO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-13
 */
public interface C03CategoryClientService {


     List<C03CategoryDO> getByParentId(Map<String, Object> map);
     List<C03CategoryDO> listFirst();


}
