package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A06BaseInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Mapper
public interface A06BaseInfoDao  extends BaseMapper<A06BaseInfoDO> {

     A06BaseInfoDO get(Long id);


     List<A06BaseInfoDO> listHDPage(Map<String, Object> map);

     List<A06BaseInfoDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A06BaseInfoDO> a06BaseInfos);



     int updateList(List<A06BaseInfoDO> a06BaseInfos);

     int updateStatus(Map<String, Object> map);
}
