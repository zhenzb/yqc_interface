package com.youqiancheng.service.client.service;

import com.youqiancheng.mybatis.model.A15MessageDO;
import com.youqiancheng.vo.client.A15MessageClientVO;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
public interface A15MessageClientService {

     A15MessageDO get(Long id);

     List<A15MessageClientVO> listHDPage(Map<String, Object> map);

     int read(Long id);

}
