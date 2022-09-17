package com.youqiancheng.service.app.service;

import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.vo.app.A15MessageAppVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
public interface A15MessageAppService {

     A15MessageDO get(Long id);

     List<A15MessageAppVO> listHDPage(Map<String, Object> map);
     Integer count(Map<String, Object> map);

     int read(Long id);

    A06BaseInfoDO getA06BaseInfo();

    A06BaseInfoDO addA06BaseInfo();
}
