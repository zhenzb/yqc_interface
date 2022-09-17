package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.B06UserAddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Mapper
public interface B06UserAddressDao extends BaseMapper<B06UserAddressDO> {

     B06UserAddressDO get(Long id);


     List<B06UserAddressDO> listHDPage(Map<String, Object> map);

     List<B06UserAddressDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<B06UserAddressDO> b06UserAddresss);



     int updateList(List<B06UserAddressDO> b06UserAddresss);

     int updateStatus(Map<String, Object> map);
}
