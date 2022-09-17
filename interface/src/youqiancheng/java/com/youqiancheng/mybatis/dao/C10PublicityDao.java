package com.youqiancheng.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.C10PublicityDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface C10PublicityDao extends BaseMapper<C10PublicityDO> {

     C10PublicityDO get(Long id);
     List<C10PublicityDO> listHDPage(Map<String, Object> map);
     List<C10PublicityDO> listByShopManageHDPage(Map<String, Object> map);

     List<C10PublicityDO> listPublicityHDPage(Map<String, Object> map);

     List<C10PublicityDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<C10PublicityDO> c10Publicitys);



     int updateList(List<C10PublicityDO> c10Publicitys);

     int updateStatus(Map<String, Object> map);

}
