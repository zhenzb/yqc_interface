package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.E02RedenvelopesAdvertisementDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Mapper
public interface E02RedenvelopesAdvertisementDao  extends BaseMapper<E02RedenvelopesAdvertisementDO>{

     E02RedenvelopesAdvertisementDO get(Long id);

     List<E02RedenvelopesAdvertisementDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<E02RedenvelopesAdvertisementDO> e02RedenvelopesAdvertisements);



     int updateList(List<E02RedenvelopesAdvertisementDO> e02RedenvelopesAdvertisements);

     int updateStatus(Map<String, Object> map);
}
