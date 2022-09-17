package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A17MessageUserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Mapper
public interface A17MessageUserDao extends BaseMapper<A17MessageUserDO> {

     A17MessageUserDO get(Long id);


     List<A17MessageUserDO> listHDPage(Map<String, Object> map);

     List<A17MessageUserDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A17MessageUserDO> a17MessageUsers);



     int updateList(List<A17MessageUserDO> a17MessageUsers);

     int updateStatus(Map<String, Object> map);
}
