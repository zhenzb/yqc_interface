package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A16SysDictDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A16SysDictDao extends BaseMapper<A16SysDictDO> {

     A16SysDictDO get(Long id);


     List<A16SysDictDO> listHDPage(Map<String, Object> map);

     List<A16SysDictDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A16SysDictDO> a16SysDicts);



     int updateList(List<A16SysDictDO> a16SysDicts);

     int updateStatus(Map<String, Object> map);
}
