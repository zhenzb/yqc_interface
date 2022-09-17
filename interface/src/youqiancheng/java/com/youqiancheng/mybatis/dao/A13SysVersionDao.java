package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.A13SysVersionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface A13SysVersionDao extends BaseMapper<A13SysVersionDO> {

     A13SysVersionDO get(Long id);


     List<A13SysVersionDO> listInfoHDPage(Map<String, Object> map);

     List<A13SysVersionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<A13SysVersionDO> a13SysVersions);



     int updateList(List<A13SysVersionDO> a13SysVersions);

     int updateStatus(Map<String, Object> map);
}
