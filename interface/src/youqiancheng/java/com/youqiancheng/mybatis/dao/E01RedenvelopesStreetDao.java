package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E01RedenvelopesStreetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-09
 */
@Mapper
public interface E01RedenvelopesStreetDao extends BaseMapper<E01RedenvelopesStreetDO> {

     E01RedenvelopesStreetDO get(Long id);


     List<E01RedenvelopesStreetDO> listHDPage(Map<String, Object> map);

     List<E01RedenvelopesStreetDO> listE01Redenve(Map<String, Object> map);

     List<E01RedenvelopesStreetDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E01RedenvelopesStreetDO> e01RedenvelopesStreets);



     int updateList(List<E01RedenvelopesStreetDO> e01RedenvelopesStreets);

     int updateStatus(Map<String, Object> map);
}
