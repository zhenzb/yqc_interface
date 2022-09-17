package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C13PublicityAutioDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-07-23
 */
@Mapper
public interface C13PublicityAutioDao extends BaseMapper<C13PublicityAutioDO> {

     C13PublicityAutioDO get(Long id);


     List<C13PublicityAutioDO> listHDPage(Map<String, Object> map);

     List<C13PublicityAutioDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C13PublicityAutioDO> c13PublicityAutios);



     int updateList(List<C13PublicityAutioDO> c13PublicityAutios);

     int updateStatus(Map<String, Object> map);
}
