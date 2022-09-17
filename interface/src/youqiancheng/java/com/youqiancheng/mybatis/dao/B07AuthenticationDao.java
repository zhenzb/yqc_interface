package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B07AuthenticationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface B07AuthenticationDao  extends BaseMapper<B07AuthenticationDO>{

     B07AuthenticationDO get(Long id);

     List<B07AuthenticationDO> list(Map<String, Object> map);
     List<B07AuthenticationDO> listHDPage(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B07AuthenticationDO> b07Authentications);



     int updateList(List<B07AuthenticationDO> b07Authentications);

     int updateStatus(Map<String, Object> map);

     List<B07AuthenticationDO> listUserAuthHDPage(Map<String, Object> map);



}
