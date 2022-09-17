package com.youqiancheng.mybatis.dao.shop.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.youqiancheng.mybatis.model.F12PermissionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Mapper
public interface F12PermissionDao extends BaseMapper<F12PermissionDO> {
     //根据用户id 获取用户所有的权限
     List<F12PermissionDO> findByUserId(Long id);

     F12PermissionDO findShopPermissionByName(String name);

     F12PermissionDO get(Long id);


     List<F12PermissionDO> listHDPage(Map<String, Object> map);

     List<F12PermissionDO> list(Map<String, Object> map);

     int count(Map<String, Object> map);



     int insertBatch(List<F12PermissionDO> f12Permissions);



     int updateList(List<F12PermissionDO> f12Permissions);

     int updateStatus(Map<String, Object> map);
}
