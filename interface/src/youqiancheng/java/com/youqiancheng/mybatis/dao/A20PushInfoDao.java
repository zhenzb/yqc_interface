package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A20PushInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-06-17
 */
@Mapper
public interface A20PushInfoDao extends BaseMapper<A20PushInfoDO> {

     A20PushInfoDO get(Long id);


     List<A20PushInfoDO> listHDPage(Map<String, Object> map);

     List<A20PushInfoDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A20PushInfoDO> a20PushInfos);



     int updateList(List<A20PushInfoDO> a20PushInfos);

     int updateStatus(Map<String, Object> map);
}
